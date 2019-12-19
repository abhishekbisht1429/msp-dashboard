package com.upes.mspdashboard.activity.faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.fragment.faculty.FacProposalDetailsFragment;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.GlobalConstants;

public class CurrentProjectActivty extends AppCompatActivity implements
    FacProposalDetailsFragment.OnFragmentInteractionListener {
    public static String TAG = "CurrentProjectActivity";
    private FrameLayout frameLayout;
    private Proposal proposal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_project_activty);
        frameLayout = findViewById(R.id.frame_layout_act_fac_current_project);
        retrieveSavedState();
        setCurrentFragment(FacProposalDetailsFragment.newInstance(proposal, false), false);
    }

    private void retrieveSavedState() {
        Bundle args = getIntent().getExtras();
        proposal = args.getParcelable(GlobalConstants.PROPOSAL_PARCEL_KEY);
        if (proposal == null)
            Log.e(TAG, "proposal is null");
    }

    void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void setCurrentFragment(Fragment fragment, boolean addToBackStack) {
        Bundle args = fragment.getArguments();
        args.putBoolean(GlobalConstants.SET_TOOLBAR_AS_ACTIONBAR, true);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(frameLayout.getId(), fragment);
        if (addToBackStack)
            ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onInteraction(boolean accepted, Proposal proposal, String msg) {
        //Do nothing here
    }
}

