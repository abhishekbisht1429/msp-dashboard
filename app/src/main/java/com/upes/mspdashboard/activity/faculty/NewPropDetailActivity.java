package com.upes.mspdashboard.activity.faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.fragment.faculty.FacProposalDetailsFragment;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.GlobalConstants;

import static com.upes.mspdashboard.util.GlobalConstants.FACULTY_PARCEL_KEY;

public class NewPropDetailActivity extends AppCompatActivity implements
    FacProposalDetailsFragment.OnFragmentInteractionListener {
    FrameLayout frameLayout;
    private Proposal proposal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_prop_detail);
        frameLayout = findViewById(R.id.frame_layout_act_fac_new_prop);
        retrieveSavedState();
        setCurrentFragment(FacProposalDetailsFragment.newInstance(proposal),false);
    }

    private void retrieveSavedState() {
        Bundle args = getIntent().getExtras();
        proposal = args.getParcelable(GlobalConstants.PROPOSAL_PARCEL_KEY);
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
    public void onInteraction(String msg) {
        makeToast(msg);
        onBackPressed();
    }
}
