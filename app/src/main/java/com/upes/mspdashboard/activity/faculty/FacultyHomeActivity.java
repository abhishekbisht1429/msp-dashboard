package com.upes.mspdashboard.activity.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.upes.mspdashboard.R;
import com.upes.mspdashboard.activity.LoginActivity;
import com.upes.mspdashboard.fragment.faculty.CurrentProjectFragment;
import com.upes.mspdashboard.fragment.faculty.NewProposalFragment;
import com.upes.mspdashboard.fragment.faculty.ProfileFragment;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.GlobalConstants;
import com.upes.mspdashboard.util.SessionManager;

public class FacultyHomeActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener,
        CurrentProjectFragment.OnFragmentInteractionListener,
        NewProposalFragment.OnFragmentInteractionListener {
    private static final String TAG = "FacultyHomeActivity";
    private FrameLayout frameLayout;
    private BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);
        frameLayout = findViewById(R.id.frame_layout_faculty_home);
        bnv = findViewById(R.id.bnv_faculty_home_activity);
        bnv.setOnNavigationItemSelectedListener(this);
        setCurrentFragment(new NewProposalFragment(),false);
    }

    void makeToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void setCurrentFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(frameLayout.getId(),fragment);
        if(addToBackStack)
            ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bnv.setOnNavigationItemSelectedListener(null);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.action_proposals: {
                setCurrentFragment(NewProposalFragment.newInstance(),false);
                break;
            }
            case R.id.action_current_projects: {
                setCurrentFragment(CurrentProjectFragment.newInstance(),false);
                break;
            }
            case R.id.action_profile: {
                setCurrentFragment(ProfileFragment.newInstance(),false);
                break;
            }
            case R.id.action_ac: {
                break;
            }
        }
        return true;
    }

    @Override
    public void onFacLogout(boolean success, String errorMsg) {
        SessionManager.getInstance(this).logout();
        if(success) {
            makeToast("Successfully Logged out");
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        } else {
            makeToast(errorMsg);
        }
    }

    @Override
    public void onClickViewNewProposal(Proposal proposal) {
        Intent intent = new Intent(this,NewPropDetailActivity.class);
        intent.putExtra(GlobalConstants.PROPOSAL_PARCEL_KEY,proposal);
        startActivity(intent);
        //finish();//TODO: remove this finish

    }

    @Override
    public void onExcelFileDownload(boolean success, String msg) {
        if(success) {
            makeToast(msg);
        } else {
            makeToast(msg);
        }
    }
}
