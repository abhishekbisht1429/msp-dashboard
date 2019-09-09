package com.upes.mspdashboard.fragment.login;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.upes.mspdashboard.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginOptionFragment extends Fragment implements View.OnClickListener {


    public static final int STUDENT_LOGIN = 1;
    public static final int FACULTY_LOGIN = 2;

    private OnFragmentInteractionListener mListener;
    private Button btnFacultyLogin;
    private Button btnStudentLogin;
    public LoginOptionFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance() {
        return new LoginOptionFragment();
    }

    @Override
    public void onAttach(Context context) {
        if(context instanceof OnFragmentInteractionListener) {
            super.onAttach(context);
            mListener = (OnFragmentInteractionListener)context;
        } else {
            throw new IllegalStateException(context.toString()+"must implement "
                    +this.getClass()+".OnFragmentInteractionListener");
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
        View view = inflater.inflate(R.layout.fragment_login_option, container, false);
        btnFacultyLogin = view.findViewById(R.id.button_login_student);
        btnStudentLogin = view.findViewById(R.id.button_login_faculty);
        btnFacultyLogin.setOnClickListener(this);
        btnStudentLogin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnStudentLogin.getId()) {
            mListener.onOptionSelect(FACULTY_LOGIN);
        } else if(view.getId() == btnFacultyLogin.getId()) {
            mListener.onOptionSelect(STUDENT_LOGIN);
        }
    }

    public interface OnFragmentInteractionListener {
        void onOptionSelect(int opt);
    }
}
