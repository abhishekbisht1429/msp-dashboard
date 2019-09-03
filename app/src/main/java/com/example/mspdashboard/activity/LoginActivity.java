package com.example.mspdashboard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.mspdashboard.R;
import com.example.mspdashboard.fragments.LoginOptionFragment;

public class LoginActivity extends AppCompatActivity implements
        LoginOptionFragment.InteractionListener {
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.frame_layout_login_activity);
    }

    void makeToask(String msg) {
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


        } else if(opt==LoginOptionFragment.FACULTY_LOGIN) {

        }
    }
}
