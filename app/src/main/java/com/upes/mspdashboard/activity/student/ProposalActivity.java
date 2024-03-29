package com.upes.mspdashboard.activity.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.fragment.faculty.FacProposalDetailsFragment;
import com.upes.mspdashboard.fragment.student.StudentProposalFragment;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.GlobalConstants;

public class ProposalActivity extends AppCompatActivity implements
    FacProposalDetailsFragment.OnFragmentInteractionListener {
    private FrameLayout frameLayout;
    private Proposal proposal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal);
        frameLayout = findViewById(R.id.frame_layout_stu_prop_activity);
        Bundle args = getIntent().getExtras();
        proposal = args.getParcelable(GlobalConstants.PROPOSAL_PARCEL_KEY);
        setCurrentFragment(FacProposalDetailsFragment.newInstance(proposal,false),false);
    }

    void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void setCurrentFragment(Fragment fragment, boolean addToBackStack) {
        Bundle args = fragment.getArguments();
        args.putBoolean(GlobalConstants.SET_TOOLBAR_AS_ACTIONBAR,addToBackStack);
        args.putParcelable(GlobalConstants.PROPOSAL_PARCEL_KEY,proposal);
        args.putBoolean(GlobalConstants.SET_TOOLBAR_AS_ACTIONBAR,true);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(frameLayout.getId(), fragment);
        if (addToBackStack)
            ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onInteraction(boolean accepted, Proposal proposal, String msg) {
        //DO Nothing here
    }
}
