package com.upes.mspdashboard.fragment.student;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.model.Faculty;
import com.upes.mspdashboard.model.Proposal;

import static com.upes.mspdashboard.util.GlobalConstants.FACULTY_PARCEL_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProposalSubmissionFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private Faculty faculty;
    private Button btnSubmit;
    public ProposalSubmissionFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Faculty faculty) {
        Fragment fragment = new ProposalSubmissionFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_proposal_submission, container, false);
        btnSubmit = view.findViewById(R.id.btn_proposal_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Proposal proposal = new Proposal();
                proposal.setTitle("Title");
                proposal.setDescription("Test Description");
                mListener.onProposalSubmit(proposal);
            }
        });
        return view;
    }

    public interface OnFragmentInteractionListener {
        void onProposalSubmit(Proposal proposal);
    }

}
