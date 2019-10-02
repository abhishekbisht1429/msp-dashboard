package com.upes.mspdashboard.activity.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.fragment.student.StudentProposalFragment;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.GlobalConstants;

public class ProposalActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal);
        frameLayout = findViewById(R.id.frame_layout_stu_prop_activity);
    }

    void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void setCurrentFragment(Fragment fragment, boolean addToBackStack) {
        Bundle args = fragment.getArguments();
        args.putBoolean(GlobalConstants.SET_TOOLBAR_AS_ACTIONBAR,addToBackStack);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(frameLayout.getId(), fragment);
        if (addToBackStack)
            ft.addToBackStack(null);
        ft.commit();
    }
}
