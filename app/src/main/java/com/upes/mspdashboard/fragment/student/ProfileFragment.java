package com.upes.mspdashboard.fragment.student;

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
import com.upes.mspdashboard.model.Student;
import com.upes.mspdashboard.util.SessionManager;
import com.upes.mspdashboard.util.Utility;
import com.upes.mspdashboard.util.retrofit.RetrofitApiClient;
import com.upes.mspdashboard.util.retrofit.model.response.LogoutResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements
    View.OnClickListener {
    public static final String TAG = "ProfileFragment";
    private ProfileFragment.OnFragmentInteractionListener mListener;
    private Button btnLogout;
    private TextView txtVName;
    private TextView txtVExtra;
    private TextView txtVEnrNo;

    private Student student;
    private TextView txtVSapId;
    private TextView txtVcgpa;
    private TextView txtVsem;
    private TextView txtVProgram;

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

        student = (Student)SessionManager.getInstance(this.getContext()).getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stu_profile, container, false);
        btnLogout = view.findViewById(R.id.btn_stu_logout);
        txtVName = view.findViewById(R.id.text_view_stu_profile_name);
        txtVExtra = view.findViewById(R.id.text_view_stu_profile_extra);
        txtVEnrNo = view.findViewById(R.id.txtv_enrno);
        txtVSapId = view.findViewById(R.id.txtv_sapid);
        txtVcgpa = view.findViewById(R.id.txtv_cgpa);
        txtVsem = view.findViewById(R.id.txtv_semester);
        txtVProgram = view.findViewById(R.id.txtv_program);

        setStudentDetails();

        btnLogout.setOnClickListener(this);
        return view;
    }

    private void setStudentDetails() {
        txtVName.setText(student.getFirstname()+" "+student.getLastname());
        txtVExtra.setText(student.getProgram());
        txtVEnrNo.setText(student.getEnrNo());
        txtVcgpa.setText(student.getCgpa()+"");
        txtVsem.setText(student.getSemeser());
        txtVProgram.setText(student.getProgram());
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
        student = null;
    }

    @Override
    public void onClick(View view) {
        RetrofitApiClient.getInstance().getAuthClient()
                .logout(Utility.authHeader(this.getContext()), student.getUsername())
                .enqueue(new Callback<LogoutResponse>() {
                    @Override
                    public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                        Log.i(TAG,"response code : "+response.code());
                        try {
                            Log.i(TAG, "response error : " + response.errorBody().string());
                        }catch(Exception e) {
                            e.printStackTrace();
                        }
                        mListener.onStuLogout(true,null);
                    }

                    @Override
                    public void onFailure(Call<LogoutResponse> call, Throwable t) {
                        Log.i(TAG,"failed to logout");
                        t.printStackTrace();
                        mListener.onStuLogout(false,"failed to logout");
                    }
                });
    }

    public interface OnFragmentInteractionListener {
        void onStuLogout(boolean success, String errorMsg);
    }
}
