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
import com.upes.mspdashboard.model.Faculty;
import com.upes.mspdashboard.model.Student;
import com.upes.mspdashboard.model.User;
import com.upes.mspdashboard.util.SessionManager;
import com.upes.mspdashboard.util.Utility;
import com.upes.mspdashboard.util.WebApiConstants;
import com.upes.mspdashboard.util.retrofit.RetrofitApiClient;
import com.upes.mspdashboard.util.retrofit.model.LoginResponse;
import com.upes.mspdashboard.util.retrofit.model.UserDetailsResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "Faculty Login";
    private static final String LOGIN_OPT_KEY = "login opt key";
    private OnFragmentInteractionListener mListener;
    private Toolbar toolbar;
    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;
    private int loginOpt;
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(int loginOpt) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putInt(LOGIN_OPT_KEY,loginOpt);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        loginOpt = args.getInt(LOGIN_OPT_KEY);
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
        btnLogin.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        final WebApiConstants.UserType uType;
        if(loginOpt == LoginOptionFragment.FACULTY_LOGIN) {
            uType = WebApiConstants.UserType.FACULTY;
        }
        else if(loginOpt == LoginOptionFragment.STUDENT_LOGIN) {
            uType = WebApiConstants.UserType.STUDENT;
        } else {
            uType = null;
        }

        Map<String,String> cred = new HashMap<>();
        cred.put("username",username);
        cred.put("password",password);
        RetrofitApiClient.getInstance().getAuthClient().login(cred)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        final LoginResponse loginResponse = response.body();
                        if(loginResponse!=null) {
                            Log.i(TAG, "auth token : " + loginResponse.getAuthToken());
                            Map<String,String> headers = new HashMap<>();
                            headers.put("Authorization","Token "+loginResponse.getAuthToken());
                            String path = "";
                            if(uType== WebApiConstants.UserType.FACULTY) path = WebApiConstants.FACULTY_URL;
                            else path = WebApiConstants.STUDENT_URL;


                            RetrofitApiClient.getInstance().getAuthClient()
                                    .getUserDetails(headers,path,username)
                                    .enqueue(new Callback<UserDetailsResponse>() {
                                        @Override
                                        public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                                            UserDetailsResponse uResponse = response.body();
                                            if(uResponse!=null &&
                                                    uType == WebApiConstants.UserType.getType(uResponse.getTypeId())) {
                                                Log.i(TAG,"user type : "+uResponse.getTypeId());
                                                User user;
                                                Log.i(TAG,uResponse.getPhoneNo()+"");
                                                Log.i(TAG,uResponse.getEnrNo()+"");
                                                if(uType== WebApiConstants.UserType.FACULTY) {
                                                    user = new Faculty.Builder()
                                                            .username(username)
                                                            .password(password)
                                                            .type(uType)
                                                            .userDetails(uResponse)
                                                            .build();
                                                } else {
                                                    user = new Student.Builder()
                                                            .username(username)
                                                            .password(password)
                                                            .type(uType)
                                                            .userDetails(uResponse)
                                                            .build();
                                                }
                                                SessionManager.getInstance(LoginFragment.this.getContext())
                                                        .login(loginResponse.getAuthToken(),SessionManager.SESSION_TYPE_STUDENT,user);
                                                mListener.onLogin(true,user,null );
                                            } else {
                                                Log.i(TAG,"usertype response is null or utype mismatch");
                                                try {
                                                    Log.i(TAG, response.errorBody().string());
                                                }catch(Exception e) {
                                                    e.printStackTrace();
                                                }
                                                mListener.onLogin(false,null,"Authentication Failure or mismatch");
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                                            Log.i(TAG,"login failure");
                                            t.printStackTrace();
                                        }
                                    });
                        }
                        else {
                            Log.i(TAG, "loginresponse is null " + response.code());
                            Log.i(TAG, response.message());
                            try {
                                Log.i(TAG, response.errorBody().string());
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                            mListener.onLogin(false, null,"Failed to authenticate" );
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        t.printStackTrace();
                        Log.i(TAG,"error");
                        mListener.onLogin(false, null,"network error");
                    }
                });
    }

    public interface OnFragmentInteractionListener {
        void onLogin(boolean authenticated, User user, String errorMsg);
    }
}
