package com.upes.mspdashboard.activity.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.fragment.student.FacultyDetailsFragment;
import com.upes.mspdashboard.fragment.student.ProposalSubmissionFragment;
import com.upes.mspdashboard.model.Faculty;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.Utility;
import com.upes.mspdashboard.util.retrofit.RetrofitApiClient;
import com.upes.mspdashboard.util.retrofit.model.ProposalSubmitResp;

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

    @Override
    public void onProposalSubmit(Proposal proposal) {
        makeToast("submitting...");
        MultipartBody.Part  part = MultipartBody.Part.createFormData("title",proposal.getTitle());
        RetrofitApiClient.getInstance().getDataClient()
                .submitProposal(Utility.authHeader(this),part)
                .enqueue(new Callback<ProposalSubmitResp>() {
                    @Override
                    public void onResponse(Call<ProposalSubmitResp> call, Response<ProposalSubmitResp> response) {
                        ProposalSubmitResp resp = response.body();
                        if(resp!=null) {
                            Log.i(TAG,"resp not null "+response.code());
                        } else {
                            Log.e(TAG,"resp is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<ProposalSubmitResp> call, Throwable t) {
                        Log.e(TAG,"failed to submit proposal");
                    }
                });
    }
}
