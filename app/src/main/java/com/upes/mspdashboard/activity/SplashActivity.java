package com.upes.mspdashboard.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.activity.faculty.FacultyHomeActivity;
import com.upes.mspdashboard.util.SessionManager;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final int sessionType = SessionManager.getInstance(this).getSessionType();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sessionType==SessionManager.SESSION_TYPE_FACULTY)
                    startActivity(new Intent(SplashActivity.this, FacultyHomeActivity.class));
                else if(sessionType==SessionManager.SESSION_TYPE_STUDENT) {
                    //TODO: startActivity for student
                }
                else
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                SplashActivity.this.finish();
            }
        },SPLASH_TIME_OUT);
    }
}
