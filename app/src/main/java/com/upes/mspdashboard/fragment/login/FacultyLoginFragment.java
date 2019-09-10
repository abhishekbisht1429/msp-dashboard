package com.upes.mspdashboard.fragment.login;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.upes.mspdashboard.R;
import com.upes.mspdashboard.model.User;
import com.upes.mspdashboard.util.SessionManager;
import com.upes.mspdashboard.util.retrofit.RetrofitApiClient;
import com.upes.mspdashboard.util.retrofit.model.LoginResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacultyLoginFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "Faculty Login";
    private OnFragmentInteractionListener mListener;
    private Toolbar toolbar;
    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;
    public FacultyLoginFragment() {
        // Required empty public constructor
    }

    public static FacultyLoginFragment newInstance() {
        FacultyLoginFragment fragment = new FacultyLoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty_login, container, false);
        toolbar = view.findViewById(R.id.toolbar_faculty_login);
        btnLogin = view.findViewById(R.id.button_login);
        etUsername = view.findViewById(R.id.edit_text_username);
        etPassword = view.findViewById(R.id.edit_text_password);
        btnLogin.setOnClickListener(this);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Faculty Login");
        return view;
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
    public void onClick(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        User user = new User.Builder()
                .username(username).password(password).build();
        RetrofitApiClient.getInstance().getLoginClient().login(user)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginResponse = response.body();
                        if(loginResponse!=null) {
                            SessionManager.getInstance(FacultyLoginFragment.this.getContext())
                                    .login(loginResponse.getAuthToken());
                            Log.i(TAG, "auth token : " + loginResponse.getAuthToken());
                        }
                        else {
                            Log.i(TAG, "loginresponse is null " + response.code());
                            Log.i(TAG, response.message());
                            try {
                                Log.i(TAG, response.errorBody().string());
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        t.printStackTrace();
                        Log.i(TAG,"error");
                    }
                });
    }

    public interface OnFragmentInteractionListener {
        void onFacultyLogin();
    }
}
