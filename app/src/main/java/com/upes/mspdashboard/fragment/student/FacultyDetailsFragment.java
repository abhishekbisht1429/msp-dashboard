package com.upes.mspdashboard.fragment.student;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.upes.mspdashboard.R;
import com.upes.mspdashboard.model.Faculty;

import static com.upes.mspdashboard.util.GlobalConstants.FACULTY_PARCEL_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacultyDetailsFragment extends Fragment {
    private Toolbar toolbar;
    private FloatingActionButton fabNewProposal;
    private OnFragmentInteractionListener mListener;
    private Faculty faculty;
    public FacultyDetailsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Faculty faculty) {
        Fragment fragment = new FacultyDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(FACULTY_PARCEL_KEY,faculty);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        faculty = args.getParcelable(FACULTY_PARCEL_KEY);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        faculty = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_faculty_details, container, false);
        toolbar = view.findViewById(R.id.toolbar_fac_details_stu);
        fabNewProposal = view.findViewById(R.id.fab_fac_details_stu);
        fabNewProposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickNewProposal(faculty);
            }
        });
        return view;
    }

    public interface OnFragmentInteractionListener {
        void onClickNewProposal(Faculty faculty);
    }
}
