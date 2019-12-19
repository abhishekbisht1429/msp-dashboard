package com.upes.mspdashboard.fragment.student;


import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.upes.mspdashboard.R;
import com.upes.mspdashboard.model.Faculty;
import com.upes.mspdashboard.util.GlobalConstants;

import static com.upes.mspdashboard.util.GlobalConstants.FACULTY_PARCEL_KEY;
import static com.upes.mspdashboard.util.GlobalConstants.SET_TOOLBAR_AS_ACTIONBAR;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacultyDetailsFragment extends Fragment {
    private Toolbar toolbar;
    private FloatingActionButton fabNewProposal;
    private OnFragmentInteractionListener mListener;
    private Faculty faculty;
    private boolean setToolbarAsActionbar;
    private TextView txtvDepartment;
    private TextView txtvAoS;
    private TextView txtvEmail;

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
        setToolbarAsActionbar = args.getBoolean(GlobalConstants.SET_TOOLBAR_AS_ACTIONBAR);
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
        txtvDepartment = view.findViewById(R.id.txtv_stu_fac_prof_dept);
        txtvAoS = view.findViewById(R.id.txtv_stu_fac_prof_area_of_expt);
        txtvEmail = view.findViewById(R.id.txtv_frag_fac_details_email);
        fabNewProposal = view.findViewById(R.id.fab_fac_details_stu);
        fabNewProposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickNewProposal(faculty);
            }
        });
        if(setToolbarAsActionbar) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setFacultyDetails();
        return view;
    }

    private void setFacultyDetails() {
        toolbar.setTitle(faculty.getFirstname()+" "+faculty.getLastname());
        txtvDepartment.setText(faculty.getDepartment());
        txtvAoS.setText(faculty.getFieldOfStudy());
        txtvEmail.setText(faculty.getEmail());
    }

    public interface OnFragmentInteractionListener {
        void onClickNewProposal(Faculty faculty);
    }
}
