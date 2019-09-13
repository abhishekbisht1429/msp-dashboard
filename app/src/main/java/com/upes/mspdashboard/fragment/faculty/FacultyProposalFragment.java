package com.upes.mspdashboard.fragment.faculty;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import com.upes.mspdashboard.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacultyProposalFragment extends Fragment {


    public FacultyProposalFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        Fragment fragment = new FacultyProposalFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faculty_proposal, container, false);
    }

}
