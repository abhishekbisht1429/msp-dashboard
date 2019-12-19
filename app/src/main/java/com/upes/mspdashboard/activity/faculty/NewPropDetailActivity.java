package com.upes.mspdashboard.activity.faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.fragment.faculty.FacProposalDetailsFragment;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.GlobalConstants;
import com.upes.mspdashboard.util.Utility;
import com.upes.mspdashboard.util.retrofit.RetrofitApiClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPropDetailActivity extends AppCompatActivity implements
    FacProposalDetailsFragment.OnFragmentInteractionListener {
    private static String TAG = "NewPropDetailActivity";
    FrameLayout frameLayout;
    private Proposal proposal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_prop_detail);
        frameLayout = findViewById(R.id.frame_layout_act_fac_new_prop);
        retrieveSavedState();
        setCurrentFragment(FacProposalDetailsFragment.newInstance(proposal, true),false);
    }

    private void retrieveSavedState() {
        Bundle args = getIntent().getExtras();
        proposal = args.getParcelable(GlobalConstants.PROPOSAL_PARCEL_KEY);
        if(proposal==null)
            Log.e(TAG,"proposal is null");
    }

    void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void setCurrentFragment(Fragment fragment, boolean addToBackStack) {
        Bundle args = fragment.getArguments();
        args.putBoolean(GlobalConstants.SET_TOOLBAR_AS_ACTIONBAR,true);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(frameLayout.getId(), fragment);
        if (addToBackStack)
            ft.addToBackStack(null);
        ft.commit();
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

    @Override
    public void onInteraction(boolean accepted, Proposal proposal, String msg) {
        makeToast(msg);
        if(accepted) {
            RetrofitApiClient.getInstance().getDataClient()
                    .acceptProposal(Utility.authHeader(this),proposal.getId())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            ResponseBody resp = response.body();
                            if(resp!=null) {
                                Log.i(TAG,"Proposal Accepted");
                                makeToast("Proposal Accepted");
                            } else {
                                try {
                                    Log.e(TAG, "error : " + response.errorBody().string());
                                }catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }
                                Log.e(TAG,"response is null");
                                makeToast("Respone is null");
                                makeToast("Cannot Accept Proposal");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                            Log.e(TAG,"error while accepting");
                            makeToast("Some Error occured");
                        }
                    });
        } else {
            RetrofitApiClient.getInstance().getDataClient()
                    .rejectProposal(Utility.authHeader(this),proposal.getId())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            ResponseBody resp = response.body();
                            if(resp!=null) {
                                Log.i(TAG,"Proposal Rejected");
                                makeToast("Proposal Rejected");
                            } else {
                                try {
                                    Log.e(TAG, "error : " + response.errorBody().string());
                                }catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }
                                Log.e(TAG,"response is null");
                                makeToast("Respone is null");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                            Log.e(TAG,"error while rejecting");
                            makeToast("Some Error occured");
                        }
                    });
        }
        onBackPressed();
    }
}
