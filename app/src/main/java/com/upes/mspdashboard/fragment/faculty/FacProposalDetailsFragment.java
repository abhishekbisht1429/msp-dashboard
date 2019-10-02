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
public class FacProposalDetailsFragment extends Fragment {


    public FacProposalDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fac_proposal_details, container, false);
    }

}
