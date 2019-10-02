package com.upes.mspdashboard.activity.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.fragment.student.FacultyDetailsFragment;
import com.upes.mspdashboard.fragment.student.ProposalSubmissionFragment;
import com.upes.mspdashboard.model.Faculty;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.GlobalConstants;
import com.upes.mspdashboard.util.Utility;
import com.upes.mspdashboard.util.retrofit.RetrofitApiClient;
import com.upes.mspdashboard.util.retrofit.model.ProposalSubmitResp;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    private String getRealPathFromUri(Uri uri) {
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if(cursor!=null) {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            Log.i(TAG,cursor.getString(idx));
            return cursor.getString(idx);
        }
        return null;
    }

    @Override
    public void onClickProposalSubmit(Proposal proposal) {
        makeToast("submitting..."+proposal.getTitle());
        //create the file part
        File file = new File(getRealPathFromUri(proposal.getProposalUri()));
        RequestBody fileBody = RequestBody.create(MediaType.parse("*/*"),file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file","proposal",fileBody);
        //create the username part
        MultipartBody.Part unamePart = MultipartBody.Part.createFormData("username",proposal.getStudent().getUsername());
        //create the mentor-uname part
        MultipartBody.Part mentorUnamePart = MultipartBody.Part.createFormData("mentorUsername",proposal.getMentor().getUsername());
        //create the title part
        MultipartBody.Part  titlePart = MultipartBody.Part.createFormData("title",proposal.getTitle());
        //create the description part
        MultipartBody.Part descPart = MultipartBody.Part.createFormData("description",proposal.getDescription());

        RetrofitApiClient.getInstance().getDataClient()
                .submitProposal(Utility.authHeader(this),unamePart,mentorUnamePart,titlePart,descPart,filePart)
                .enqueue(new Callback<ProposalSubmitResp>() {
                    @Override
                    public void onResponse(Call<ProposalSubmitResp> call, Response<ProposalSubmitResp> response) {
                        ProposalSubmitResp resp = response.body();
                        if(resp!=null) {
                            Log.i(TAG,"resp not null "+response.code()+" : "+resp.getErrors());
                        } else {
                            Log.e(TAG,"resp is null");
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
