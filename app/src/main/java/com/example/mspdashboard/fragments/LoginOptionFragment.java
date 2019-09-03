package com.example.mspdashboard.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mspdashboard.R;
import com.example.mspdashboard.activity.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginOptionFragment extends Fragment {


    public static final int STUDENT_LOGIN = 1;
    public static final int FACULTY_LOGIN = 2;

    public LoginOptionFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance() {
        return new LoginOptionFragment();
    }

    @Override
    public void onAttach(Context context) {
        if(context instanceof InteractionListener) {
            super.onAttach(context);
        } else {
            throw new IllegalStateException(context.toString()+"must implement "
                    +this.getClass()+".InteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_option, container, false);
    }

    public interface InteractionListener {
        void onOptionSelect(int opt);
    }
}
