package com.upes.mspdashboard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.activity.faculty.FacultyHomeActivity;
import com.upes.mspdashboard.fragment.login.FacultyLoginFragment;
import com.upes.mspdashboard.fragment.login.LoginOptionFragment;
import com.upes.mspdashboard.fragment.login.StudentLoginFragment;

public class LoginActivity extends AppCompatActivity implements
        LoginOptionFragment.OnFragmentInteractionListener,
        StudentLoginFragment.OnFragmentInteractionListener,
        FacultyLoginFragment.OnFragmentInteractionListener {
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.frame_layout_login_activity);
        setCurrentFragment(LoginOptionFragment.getInstance(),false);
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
    public void onOptionSelect(int opt) {
        if(opt==LoginOptionFragment.STUDENT_LOGIN) {
            setCurrentFragment(StudentLoginFragment.newInstance(),true);
        } else if(opt==LoginOptionFragment.FACULTY_LOGIN) {
            setCurrentFragment(FacultyLoginFragment.newInstance(),true);
        }
    }

    @Override
    public void onFacultyLogin(boolean authenticated, String errorMsg) {
        if(authenticated) {
            startActivity(new Intent(this, FacultyHomeActivity.class));
            this.finish();
        } else {
            makeToast("Authentication Failure: "+errorMsg);
        }
    }

    @Override
    public void onStudentLogin() {

    }
}
