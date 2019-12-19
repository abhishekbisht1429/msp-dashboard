package com.upes.mspdashboard.fragment.faculty;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.model.Faculty;
import com.upes.mspdashboard.util.SessionManager;
import com.upes.mspdashboard.util.Utility;
import com.upes.mspdashboard.util.retrofit.RetrofitApiClient;
import com.upes.mspdashboard.util.retrofit.model.response.LogoutResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements View.OnClickListener{
    public static final String TAG = "ProfileFragment";
    private OnFragmentInteractionListener mListener;
    private Button btnLogout;
    private TextView txtVName;
    private TextView txtVExtra;

    private Faculty faculty;
    private TextView txtVAreaofStudy;
    private TextView txtVEmail;
    private TextView txtVPhone;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        faculty = (Faculty)SessionManager.getInstance(this.getContext()).getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fac_profile, container, false);
        btnLogout = view.findViewById(R.id.btn_fac_logout);
        txtVName = view.findViewById(R.id.text_view_fac_profile_name);
        txtVExtra = view.findViewById(R.id.text_view_fac_profile_extra);
        txtVAreaofStudy = view.findViewById(R.id.txtv_fac_prof_area_of_expt);
        txtVEmail = view.findViewById(R.id.txtv_fac_prof_email);
        txtVPhone = view.findViewById(R.id.txtv_frag_fac_prof_phone);
        btnLogout.setOnClickListener(this);
        setFacultyDetails();
        return view;
    }

    private void setFacultyDetails() {
        txtVName.setText(faculty.getFirstname()+" "+faculty.getLastname());
        txtVExtra.setText(faculty.getDepartment()+" Department");
        txtVAreaofStudy.setText(faculty.getFieldOfStudy());
        txtVEmail.setText(faculty.getEmail());
        txtVPhone.setText(faculty.getPhoneNo());
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
        btnLogout.setOnClickListener(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        faculty = null;
    }

    @Override
    public void onClick(View view) {
        RetrofitApiClient.getInstance().getAuthClient()
                .logout(Utility.authHeader(this.getContext()), faculty.getUsername())
                .enqueue(new Callback<LogoutResponse>() {
                    @Override
                    public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                        Log.i(TAG,"response code : "+response.code());
                        try {
                            Log.i(TAG, "response error : " + response.errorBody().string());
                        }catch(Exception e) {
                            e.printStackTrace();
                        }
                        mListener.onFacLogout(true,null);
                    }

                    @Override
                    public void onFailure(Call<LogoutResponse> call, Throwable t) {
                        Log.i(TAG,"failed to logout");
                        t.printStackTrace();
                        mListener.onFacLogout(false,"failed to logout");
                    }
                });
    }

    public interface OnFragmentInteractionListener {
        void onFacLogout(boolean success, String errorMsg);
    }
}
