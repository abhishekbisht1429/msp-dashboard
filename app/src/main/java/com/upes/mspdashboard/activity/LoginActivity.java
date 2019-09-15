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
import com.upes.mspdashboard.activity.student.StudentHomeActivity;
import com.upes.mspdashboard.fragment.login.LoginFragment;
import com.upes.mspdashboard.fragment.login.LoginOptionFragment;
import com.upes.mspdashboard.model.User;
import com.upes.mspdashboard.util.WebApiConstants;

public class LoginActivity extends AppCompatActivity implements
        LoginOptionFragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener {
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
        setCurrentFragment(LoginFragment.newInstance(opt),false);
    }

    @Override
    public void onLogin(boolean authenticated, User user, String errorMsg) {
        if(authenticated && user.getType()== WebApiConstants.UserType.FACULTY) {
            startActivity(new Intent(this, FacultyHomeActivity.class));
            this.finish();
        } else if(authenticated && user.getType() == WebApiConstants.UserType.STUDENT ) {
            startActivity(new Intent(this, StudentHomeActivity.class));
            this.finish();
        }
        else {
            makeToast("Authentication Failure: "+errorMsg);
        }
    }
}
