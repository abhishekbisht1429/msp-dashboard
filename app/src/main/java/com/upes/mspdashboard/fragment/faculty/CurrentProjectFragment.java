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
public class CurrentProjectFragment extends Fragment {


    public CurrentProjectFragment() {
        // Required empty public constructor
    }

    public static CurrentProjectFragment newInstance() {
        return new CurrentProjectFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fac_current_project, container, false);
    }

}
