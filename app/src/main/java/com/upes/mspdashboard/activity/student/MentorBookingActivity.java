package com.upes.mspdashboard.activity.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.fragment.student.FacultyDetailsFragment;
import com.upes.mspdashboard.fragment.student.ProposalSubmissionFragment;
import com.upes.mspdashboard.model.Faculty;
import com.upes.mspdashboard.util.GlobalConstants;
import com.upes.mspdashboard.util.Utility;
import com.upes.mspdashboard.util.retrofit.RetrofitApiClient;
import com.upes.mspdashboard.util.retrofit.model.request.ProposalSubmissionRequest;
import com.upes.mspdashboard.util.retrofit.model.response.ProposalSubmitResp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.upes.mspdashboard.util.GlobalConstants.FACULTY_PARCEL_KEY;

public class MentorBookingActivity extends AppCompatActivity implements
        FacultyDetailsFragment.OnFragmentInteractionListener,
        ProposalSubmissionFragment.OnFragmentInteractionListener {
    private static final String TAG = "MentorBookingActivity";
    FrameLayout frameLayout;
    private Faculty faculty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_booking);
        frameLayout = findViewById(R.id.frame_layout_act_mentor_booking);
        retrieveSavedState();
        setCurrentFragment(FacultyDetailsFragment.newInstance(faculty),false);
    }

    void retrieveSavedState() {
        Bundle args = getIntent().getExtras();
        faculty = args.getParcelable(FACULTY_PARCEL_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelable(FACULTY_PARCEL_KEY,faculty);
    }

    void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void setCurrentFragment(Fragment fragment, boolean addToBackStack) {
        Bundle args = fragment.getArguments();
        args.putBoolean(GlobalConstants.SET_TOOLBAR_AS_ACTIONBAR,addToBackStack);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(frameLayout.getId(), fragment);
        if (addToBackStack)
            ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onClickNewProposal(Faculty faculty) {
        makeToast("Faculty : "+faculty.getUsername());
        //TODO: set ProposalSubmissionFragment as current fragment
        setCurrentFragment(ProposalSubmissionFragment.newInstance(faculty),true);
    }

    private String getUploadPathFromUri(Uri uri) {
        File temp = new File(this.getExternalCacheDir(),"temp");
        try(InputStream is = getContentResolver().openInputStream(uri);
            OutputStream os = new FileOutputStream(temp)) {
            int b;
            while((b=is.read())!=-1) {
                os.write(b);
            }
            return temp.getAbsolutePath();
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(IOException ioE) {
            Log.e(TAG,"IOException");
            ioE.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClickProposalSubmit(ProposalSubmissionRequest proposalReq) {
        makeToast("submitting..."+ proposalReq.getTitle());
        //create the file part
        String uploadFilePath = getUploadPathFromUri(proposalReq.getProposalUri());
        if(uploadFilePath==null) {
            makeToast("Please select a Proposal to Upload");
            return;
        }
        File file = new File(uploadFilePath);
        RequestBody fileBody = RequestBody.create(MediaType.parse("*/*"),file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("proposal","proposal",fileBody);
        Log.i(TAG,"mentor user id : "+ proposalReq.getMentor().getUserId());
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), proposalReq.getMember1().getUserId()+"");
        RequestBody mem2sap = RequestBody.create(MediaType.parse("text/plain"), proposalReq.getMember2Sap());
        RequestBody mem3sap = RequestBody.create(MediaType.parse("text/plain"), proposalReq.getMember3Sap());

        RequestBody mentor = RequestBody.create(MediaType.parse("text/plain"), proposalReq.getMentor().getUserId()+"");
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), proposalReq.getTitle());
        RequestBody desc = RequestBody.create(MediaType.parse("text/plain"), proposalReq.getDescription());

        RetrofitApiClient.getInstance().getDataClient()
                .submitProposal(Utility.authHeader(this),id,mem2sap,mem3sap,mentor,title,desc,filePart)
                .enqueue(new Callback<ProposalSubmitResp>() {
                    @Override
                    public void onResponse(Call<ProposalSubmitResp> call, Response<ProposalSubmitResp> response) {
                        ProposalSubmitResp resp = response.body();
                        if(resp!=null) {
                            Log.i(TAG,"resp not null "+response.code()+" : "+resp.getErrors());
                            makeToast("Proposal Submitted Successfully");
                            onBackPressed();
                        } else {
                            ResponseBody respb = response.errorBody();
                            try {
                                Log.e(TAG, "resp is null " + respb.string() + " : ");
                                makeToast("You cannot submit a proposal with the provided Data");
                            }catch(IOException ioe) {
                                ioe.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProposalSubmitResp> call, Throwable t) {
                        Log.e(TAG,"failed to submit proposal");
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
