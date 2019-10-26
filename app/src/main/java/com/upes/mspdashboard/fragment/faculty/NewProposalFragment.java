package com.upes.mspdashboard.fragment.faculty;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.upes.mspdashboard.R;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.SessionManager;
import com.upes.mspdashboard.util.Utility;
import com.upes.mspdashboard.util.retrofit.RetrofitApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewProposalFragment extends Fragment implements
    SwipeRefreshLayout.OnRefreshListener {
    public static String TAG = "NewPropFragment";
    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout swrLayout;
    private RecyclerView rv;
    private RVAdapter rvAdapter;

    public NewProposalFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        Fragment fragment = new NewProposalFragment();
        Bundle args = new Bundle();
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty_proposal, container, false);
        swrLayout = view.findViewById(R.id.swr_layout_fac_new_prop);
        rv = view.findViewById(R.id.rv_fac_new_prop);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvAdapter = new RVAdapter();
        rv.setAdapter(rvAdapter);
        swrLayout.setOnRefreshListener(this);
        fetchData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rv.setAdapter(null);
        swrLayout.setOnRefreshListener(null);
    }

    private void fetchData() {
        String username = SessionManager.getInstance(this.getContext())
                .getUser().getUsername();
        Log.i(TAG,"username : "+username);
        RetrofitApiClient.getInstance().getDataClient()
                .getNewProposals(Utility.authHeader(this.getContext()),username)
                .enqueue(new Callback<List<Proposal>>() {
                    @Override
                    public void onResponse(Call<List<Proposal>> call, Response<List<Proposal>> response) {
                        List<Proposal> proposals = response.body();
                        if(proposals!=null) {
                            Log.i(TAG,"no of proposals "+proposals.size());
                            rvAdapter.setProposalList(proposals);
                        } else {
                            Log.i(TAG,"proposals are null : "+response.code());
                        }
                        stopRefreshAnimation();
                    }

                    @Override
                    public void onFailure(Call<List<Proposal>> call, Throwable t) {
                        t.printStackTrace();
                        Log.i(TAG,"failed to fetch new proposals:network error");
                        stopRefreshAnimation();
                    }
                });
    }

    private void stopRefreshAnimation() {
        if(swrLayout!=null)
            swrLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        fetchData();
    }

    public interface OnFragmentInteractionListener {
        void onClickViewNewProposal(Proposal proposal);
    }
    private class ProposalVH extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        private TextView tvTitle;
        private Proposal proposal;
        public ProposalVH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.txtv_fac_new_prop_title);
            itemView.setOnClickListener(this);
        }

        public void bind(Proposal proposal) {
            this.proposal = proposal;
            tvTitle.setText(proposal.getTitle());
        }

        @Override
        public void onClick(View view) {
            mListener.onClickViewNewProposal(proposal);
        }
    }
    private class RVAdapter extends RecyclerView.Adapter<ProposalVH> {
        private List<Proposal> proposalList = new ArrayList<>();
        @NonNull
        @Override
        public ProposalVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_fac_new_prop,parent,false);
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
            this.proposalList = proposalList;
            notifyDataSetChanged();
        }
    }
}
