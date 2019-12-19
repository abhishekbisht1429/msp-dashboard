package com.upes.mspdashboard.fragment.faculty;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.GlobalConstants;
import com.upes.mspdashboard.util.SessionManager;
import com.upes.mspdashboard.util.Utility;
import com.upes.mspdashboard.util.WebApiConstants;
import com.upes.mspdashboard.util.retrofit.RetrofitApiClient;
import com.upes.mspdashboard.util.retrofit.model.response.ProposalResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentProjectFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "CurrentProjectFragment";
    private static final int RC_EXCEL_FILE_CREATION = 1;
    private OnFragmentInteractionListener mListener;
    private Toolbar toolbar;
    private SwipeRefreshLayout swrLayout;
    private RecyclerView rv;
    private RVAdapter rvAdapter;
    private TextView tvNoData;

    public CurrentProjectFragment() {
        // Required empty public constructor
    }

    public static CurrentProjectFragment newInstance() {
        return new CurrentProjectFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fac_current_project, container, false);
        toolbar = view.findViewById(R.id.toolbar_fac_current_project);
        swrLayout = view.findViewById(R.id.swr_layout_fac_current_projects);
        rv = view.findViewById(R.id.rv_fac_current_projects);
        tvNoData = view.findViewById(R.id.txtv_fac_current_project_no_data);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvAdapter = new RVAdapter();
        rv.setAdapter(rvAdapter);
        swrLayout.setOnRefreshListener(this);
        fetchData();
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rv.setAdapter(null);
        swrLayout.setOnRefreshListener(null);
    }


    private void fetchData() {
        int userid = SessionManager.getInstance(this.getContext())
                .getUser().getUserId();
        RetrofitApiClient.getInstance().getDataClient()
                .getAcceptedProposals(Utility.authHeader(this.getContext()),userid)
                .enqueue(new Callback<List<ProposalResponse>>() {
                    @Override
                    public void onResponse(Call<List<ProposalResponse>> call, Response<List<ProposalResponse>> response) {
                        List<ProposalResponse> proposalResps = response.body();
                        if(proposalResps!=null) {
                            Log.i(TAG,"Proposal Resp is not null");
                            List<Proposal> proposals = new ArrayList<>();
                            for(ProposalResponse propResp:proposalResps) {
                                Proposal prop = new Proposal();
                                prop.setPropsalDetails(propResp);
                                proposals.add(prop);
                            }
                            rvAdapter.setProposalList(proposals);
                        } else {
                            try {
                                Log.e(TAG, "proposal is null " + response.errorBody().string());
                            }catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        }
                        stopRefreshAnimation();
                    }

                    @Override
                    public void onFailure(Call<List<ProposalResponse>> call, Throwable t) {
                        Log.e(TAG,"connection failure");
                    }
                });
    }

    private void stopRefreshAnimation() {
        if(swrLayout!=null)
            swrLayout.setRefreshing(false);
    }

    private void showNoData(boolean show) {
        tvNoData.setVisibility(show?View.VISIBLE:View.GONE);
    }

    @Override
    public void onRefresh() {
        fetchData();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.abm_frag_fac_current_project,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId()==R.id.action_download_list) {
            requestFileCreation("accepted_projects"+System.currentTimeMillis()+".csv",GlobalConstants.MIME_TYPE_CSV);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == RC_EXCEL_FILE_CREATION) {
            if(resultCode== Activity.RESULT_OK) {
                Uri excelFileuri = data.getData();
                downloadExcelFile(excelFileuri);
            } else {
                Log.e(TAG,"Document Creation Failed");
            }
        }
    }

    private void downloadExcelFile(final Uri excelFileuri) {
        int fac_id = SessionManager.getInstance(this.getContext())
                .getUser().getUserId();
        Log.i(TAG,"fac_id: "+fac_id);
        RetrofitApiClient.getInstance().getDataClient()
                .downloadFile(Utility.authHeader(this.getContext()),fac_id, WebApiConstants.PROPOSAL_STATUS_ACCEPTED)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null) {
                            writeContentToDisk(response.body(),excelFileuri);
                        } else {
                            Log.e(TAG,"Response is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        Log.e(TAG,"Failed to download file!");
                    }
                });
    }

    private void writeContentToDisk(ResponseBody body,Uri outUri) {
        try(OutputStream os = getActivity().getContentResolver().openOutputStream(outUri);
            InputStream is = body.byteStream()){
            int b;
            while((b=is.read())!=-1) {
                os.write(b);
                Log.i(TAG,"written : "+b);
            }
            Log.i(TAG,"Written bytes to disk");
            mListener.onExcelFileDownload(true,"Download successful!");
        } catch(FileNotFoundException fnfE) {
            fnfE.printStackTrace();
            Log.e(TAG,"File not found!");
            mListener.onExcelFileDownload(false,"File not found!");
        } catch (IOException ioE) {
            ioE.printStackTrace();
            mListener.onExcelFileDownload(false,"IO Exception");
        } catch (Exception e) {
            e.printStackTrace();
            mListener.onExcelFileDownload(false,"Exception'");
        }
    }

    void requestFileCreation(String fileName, String mimeType) {
        Intent fileIntent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        fileIntent.setType(mimeType);
        fileIntent.putExtra(Intent.EXTRA_TITLE,fileName);
        startActivityForResult(fileIntent, RC_EXCEL_FILE_CREATION);
    }

    public interface OnFragmentInteractionListener {
        void onExcelFileDownload(boolean success,String msg);
        void onClickViewCurrentProposal(Proposal proposal);
    }

    private class ProposalVH extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        private TextView tvTitle;
        private TextView tvDesc;
        private Proposal proposal;
        public ProposalVH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.txtv_fac_current_project_title);
            tvDesc = itemView.findViewById(R.id.txtv_fac_current_projects_desc);
            itemView.setOnClickListener(this);
        }

        public void bind(Proposal proposal) {
            this.proposal = proposal;
            tvTitle.setText(proposal.getTitle());
            tvDesc.setText(proposal.getDescription());
        }

        @Override
        public void onClick(View view) {
            mListener.onClickViewCurrentProposal(proposal);
        }
    }
    private class RVAdapter extends RecyclerView.Adapter<ProposalVH> {
        private List<Proposal> proposalList = new ArrayList<>();
        @NonNull
        @Override
        public ProposalVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_fac_current_project,parent,false);
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
            if(proposalList.size()==0) showNoData(true);
            else showNoData(false);
        }
    }
}
