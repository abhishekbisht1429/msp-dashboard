package com.upes.mspdashboard.fragment.student;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ser.impl.PropertyBasedObjectIdGenerator;
import com.upes.mspdashboard.R;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.SessionManager;
import com.upes.mspdashboard.util.Utility;
import com.upes.mspdashboard.util.WebApiConstants;
import com.upes.mspdashboard.util.retrofit.RetrofitApiClient;
import com.upes.mspdashboard.util.retrofit.model.response.ProposalResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentProposalFragment extends Fragment implements
    SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "StuPropFragment";
    OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout swrLayout;
    private RecyclerView rv;
    private RVAdapter rvAdapter;
    private TextView txtvNoData;

    public StudentProposalFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        Fragment fragment = new StudentProposalFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_proposal, container, false);
        swrLayout = view.findViewById(R.id.swrl_stu_submitted_prop);
        rv = view.findViewById(R.id.rv_stu_submitted_prop);
        txtvNoData = view.findViewById(R.id.txtv_no_data);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvAdapter = new RVAdapter();
        rv.setAdapter(rvAdapter);
        swrLayout.setOnRefreshListener(this);
        fetchData();
        return view;

    }

    private void showNoData(boolean show) {
        txtvNoData.setVisibility(show?View.VISIBLE:View.GONE);
    }
    private void fetchData() {
        int userid = SessionManager.getInstance(this.getContext())
                .getUser().getUserId();
        RetrofitApiClient.getInstance().getDataClient()
                .getSubmittedProposals(Utility.authHeader(this.getContext()),userid)
                .enqueue(new Callback<List<ProposalResponse>>() {
                    @Override
                    public void onResponse(Call<List<ProposalResponse>> call, Response<List<ProposalResponse>> response) {
                        List<ProposalResponse> proposalResps = response.body();
                        if(proposalResps!=null) {
                            List<Proposal> submittedProps = new ArrayList<>();
                            List<Proposal> rejectedProps = new ArrayList<>();
                            List<Proposal> acceptedProposal = new ArrayList<>();
                            for(ProposalResponse propResp:proposalResps) {
                                Proposal prop = new Proposal();
                                prop.setPropsalDetails(propResp);
                                if(prop.getStatus()== WebApiConstants.PROPOSAL_STATUS_ACCEPTED)
                                    acceptedProposal.add(prop);
                                else if(prop.getStatus()==WebApiConstants.PROPOSAL_STATUS_REJECTED)
                                    rejectedProps.add(prop);
                                else
                                    submittedProps.add(prop);
                            }
                            List<Proposal> proposals = new ArrayList<>();
                            proposals.addAll(acceptedProposal);
                            proposals.addAll(submittedProps);
                            proposals.addAll(rejectedProps);
                            Log.i(TAG,"no of proposals "+proposals.size());
                            rvAdapter.setProposalList(proposals);
                        } else {
                            Log.i(TAG,"proposals are null : "+response.code());
                        }
                        stopRefreshAnimation();
                    }

                    @Override
                    public void onFailure(Call<List<ProposalResponse>> call, Throwable t) {
                        t.printStackTrace();
                        Log.i(TAG,"failed to fetch new proposals:network error");
                        stopRefreshAnimation();
                    }
                });
    }

    public interface OnFragmentInteractionListener {
        void onClickSubmittedProposal(Proposal proposal);
    }

    private void stopRefreshAnimation() {
        if(swrLayout!=null)
            swrLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        fetchData();
    }

    private class ProposalVH extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        private TextView tvTitle;
        private TextView tvDescription;
        private Proposal proposal;
        private View statusView;
        public ProposalVH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.txtv_stu_submitted_prop_title);
            tvDescription = itemView.findViewById(R.id.txtv_stu_submitted_prop_desc);
            statusView = itemView.findViewById(R.id.view_frag_stu_prop_status);
            itemView.setOnClickListener(this);
        }

        public void bind(Proposal proposal) {
            this.proposal = proposal;
            tvTitle.setText(proposal.getTitle());
            tvDescription.setText(proposal.getDescription());
            if(proposal.getStatus()==WebApiConstants.PROPOSAL_STATUS_ACCEPTED)
                statusView.setBackgroundColor(ContextCompat.getColor(StudentProposalFragment.this.getContext(),R.color.prop_accepted));
            else if(proposal.getStatus()==WebApiConstants.PROPOSAL_STATUS_REJECTED)
                statusView.setBackgroundColor(ContextCompat.getColor(StudentProposalFragment.this.getContext(),R.color.prop_rejected));
            else
                statusView.setBackgroundColor(ContextCompat.getColor(StudentProposalFragment.this.getContext(),R.color.prop_submitted));
        }

        @Override
        public void onClick(View view) {
            mListener.onClickSubmittedProposal(proposal);
        }
    }
    private class RVAdapter extends RecyclerView.Adapter<ProposalVH> {
        private List<Proposal> proposalList = new ArrayList<>();
        @NonNull
        @Override
        public ProposalVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_stu_submitted_prop,parent,false);
            return new ProposalVH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProposalVH holder, int position) {
            holder.bind(proposalList.get(position));
        }

        @Override
        public int getItemCount() {
            return proposalList.size();
        }

        public void add(Proposal proposal) {
            if(proposal!=null) {
                proposalList.add(proposal);
                notifyItemChanged(proposalList.size() - 1);
            }
        }

        void setProposalList(List<Proposal> proposalList) {
            if(proposalList.size()==0)
                showNoData(true);
            else {
                showNoData(false);
                this.proposalList = proposalList;
                notifyDataSetChanged();
            }
        }
    }
}
