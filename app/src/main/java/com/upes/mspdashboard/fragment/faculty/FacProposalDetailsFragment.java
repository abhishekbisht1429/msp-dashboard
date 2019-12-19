package com.upes.mspdashboard.fragment.faculty;


import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.upes.mspdashboard.R;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.model.Student;
import com.upes.mspdashboard.util.GlobalConstants;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacProposalDetailsFragment extends Fragment {
    private static String TAG = "FacProposalDetailsFrag";
    private static String OPERATION_MODE_KEY = "fragment operation mode key";
    private OnFragmentInteractionListener mListener;
    private Proposal proposal;
    private boolean setToolbarAsActionbar;
    private boolean newPropOperationMode;
    private Toolbar toolbar;
    private FloatingActionButton fabAccept;
    private FloatingActionButton fabReject;
    private TextView txtVTitle;
    private TextView txtvDesc;
    private TextView txtvMem1;
    private TextView txtvMem2;
    private TextView txtvMem3;
    private TextView txtvMem4;

    public FacProposalDetailsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Proposal proposal, boolean facnewProp) {
        Fragment fragment = new FacProposalDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(GlobalConstants.PROPOSAL_PARCEL_KEY,proposal);
        args.putBoolean(OPERATION_MODE_KEY,facnewProp);
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
        newPropOperationMode = args.getBoolean(OPERATION_MODE_KEY);
        if(proposal==null)
            Log.e(TAG,"null proposal");
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
        txtVTitle = view.findViewById(R.id.txtv_frag_fac_proposal_details_title);
        txtvDesc = view.findViewById(R.id.txtv_frag_fac_proposal_details_desc);
        txtvMem1 = view.findViewById(R.id.txtv_frag_fac_prop_details_mem1);
        txtvMem2 = view.findViewById(R.id.txtv_frag_fac_prop_details_mem2);
        txtvMem3 = view.findViewById(R.id.txtv_frag_fac_prop_details_mem3);
        txtvMem4 = view.findViewById(R.id.txtv_frag_fac_prop_details_mem4);
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
        showProposalDetails();
        return view;
    }

    private void showProposalDetails() {
        txtVTitle.setText(proposal.getTitle());
        txtvDesc.setText(proposal.getDescription());
        List<Student> teamList = proposal.getTeamList();
        List<TextView> tvList = new ArrayList<>();
        tvList.add(txtvMem1);
        tvList.add(txtvMem2);
        tvList.add(txtvMem3);
        tvList.add(txtvMem4);
        int i;
        for(i=0;i<teamList.size();++i) {
            Student stu = teamList.get(i);
            tvList.get(i).setText(stu.getFirstname()+" "+stu.getLastname());
        }
        for(;i<tvList.size();++i)
            tvList.get(i).setVisibility(GONE);

        if(!newPropOperationMode) {
            fabAccept.setVisibility(GONE);
            fabReject.setVisibility(GONE);
        }
    }
    public interface OnFragmentInteractionListener {
        void onInteraction(boolean accepted, Proposal proposal,String msg);
    }
}
