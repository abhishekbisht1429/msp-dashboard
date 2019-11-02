package com.upes.mspdashboard.fragment.faculty;


import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.upes.mspdashboard.R;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.GlobalConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacProposalDetailsFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private Proposal proposal;
    private boolean setToolbarAsActionbar;
    private Toolbar toolbar;
    private FloatingActionButton fabAccept;
    private FloatingActionButton fabReject;
    private TextView txtVTitle;

    public FacProposalDetailsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Proposal proposal) {
        Fragment fragment = new FacProposalDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(GlobalConstants.PROPOSAL_PARCEL_KEY,proposal);
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
        proposal = args.getParcelable(GlobalConstants.PROPOSAL_PARCEL_KEY);
        setToolbarAsActionbar = args.getBoolean(GlobalConstants.SET_TOOLBAR_AS_ACTIONBAR);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        proposal = null;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fac_proposal_details, container, false);
        toolbar = view.findViewById(R.id.toolbar_fac_new_prop_details);
        fabAccept = view.findViewById(R.id.fab_fac_new_prop_details_accept);
        fabReject = view.findViewById(R.id.fab_fac_new_prop_details_reject);
        txtVTitle = view.findViewById(R.id.txtv_fac_new_prop_detail_title);

        txtVTitle.setText(proposal.getTitle());
        if (setToolbarAsActionbar) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        fabAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onInteraction(true, proposal,"Proposal Accepted");
            }
        });
        fabReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onInteraction(false, proposal, "Proposal Rejected");
            }
        });
        return view;
    }

    public interface OnFragmentInteractionListener {
        void onInteraction(boolean accepted, Proposal proposal,String msg);
    }
}
