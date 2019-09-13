package com.upes.mspdashboard.activity.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.upes.mspdashboard.R;
import com.upes.mspdashboard.fragment.faculty.CurrentProjectFragment;
import com.upes.mspdashboard.fragment.faculty.FacultyProposalFragment;
import com.upes.mspdashboard.fragment.faculty.ProfileFragment;
import com.upes.mspdashboard.fragment.login.FacultyLoginFragment;

public class FacultyHomeActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener {
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
                setCurrentFragment(FacultyProposalFragment.newInstance(),false);
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
}
