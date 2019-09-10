package com.upes.mspdashboard.activity.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.upes.mspdashboard.R;
import com.upes.mspdashboard.fragment.faculty.CurrentProjectFragment;
import com.upes.mspdashboard.fragment.faculty.ProfileFragment;
import com.upes.mspdashboard.fragment.login.FacultyLoginFragment;

public class FacultyHomeActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemReselectedListener {
    private FrameLayout frameLayout;
    private BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);
        frameLayout = findViewById(R.id.frame_layout_faculty_home);
        bnv = findViewById(R.id.bnv_faculty_home_activity);
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
    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.action_proposals: {
                setCurrentFragment(FacultyLoginFragment.newInstance(),false);
                break;
            }
            case R.id.action_curent_projects: {
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
    }
}
