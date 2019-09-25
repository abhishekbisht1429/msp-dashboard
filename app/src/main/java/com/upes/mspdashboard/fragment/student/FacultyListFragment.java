package com.upes.mspdashboard.fragment.student;


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

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.model.Faculty;
import com.upes.mspdashboard.util.Utility;
import com.upes.mspdashboard.util.WebApiConstants;
import com.upes.mspdashboard.util.retrofit.RetrofitApiClient;
import com.upes.mspdashboard.util.retrofit.model.UserDetailsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacultyListFragment extends Fragment implements
    SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "FacultyListFragment";
    private SwipeRefreshLayout swrLayout;
    private RecyclerView rv;
    private RVAdapter rvAdapter;
    OnFragmentInteractionListener mListener;
    public FacultyListFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        Fragment fragment = new FacultyListFragment();
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
        View view = inflater.inflate(R.layout.fragment_faculty_list, container, false);
        swrLayout = view.findViewById(R.id.swr_layout_fac_list);
        rv = view.findViewById(R.id.rv_fac_list);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvAdapter = new RVAdapter();
        rv.setAdapter(rvAdapter);
        fetchData();
        return view;
    }

    void fetchData() {
        RetrofitApiClient.getInstance().getDataClient()
                .getFacultyList(Utility.authHeader(this.getContext()))
                .enqueue(new Callback<List<UserDetailsResponse>>() {
                    @Override
                    public void onResponse(Call<List<UserDetailsResponse>> call, Response<List<UserDetailsResponse>> response) {
                        List<UserDetailsResponse> facList = response.body();
                        if(facList!=null) {
                            Log.i(TAG, facList.size() + "");
                            for(UserDetailsResponse udr:facList) {
                                rvAdapter.add(new Faculty.Builder()
                                                .username(udr.getFieldOfStudy())
                                                .type(WebApiConstants.UserType.HOD)
                                                .userDetails(udr)
                                                .build()
                                );
                            }
                        } else {
                            Log.i(TAG,"facList null");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserDetailsResponse>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void onRefresh() {

    }

    public interface OnFragmentInteractionListener {
        void onSelectFaculty(Faculty faculty);
    }

    private class FacultyVH extends RecyclerView.ViewHolder implements
        View.OnClickListener {
        private TextView tvName;
        private Faculty faculty;
        public FacultyVH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txtv_fac_list_name);
            itemView.setOnClickListener(this);
        }

        public void bind(Faculty faculty) {
            tvName.setText(faculty.getUsername());
            this.faculty = faculty;
        }

        @Override
        public void onClick(View view) {
            mListener.onSelectFaculty(faculty);
        }
    }
    private class RVAdapter extends RecyclerView.Adapter<FacultyVH> {
        private List<Faculty> facultyList = new ArrayList<>();
        @NonNull
        @Override
        public FacultyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_fac_list,parent,false);
            return new FacultyVH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FacultyVH holder, int position) {
            holder.bind(facultyList.get(position));
        }

        @Override
        public int getItemCount() {
            return facultyList.size();
        }

        public void add(Faculty faculty) {
            if(faculty!=null) {
                facultyList.add(faculty);
                notifyItemChanged(facultyList.size() - 1);
            }
        }
    }
}
