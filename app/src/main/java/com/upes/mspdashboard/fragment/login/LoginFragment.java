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
import com.upes.mspdashboard.util.WebApiConstants;
import com.upes.mspdashboard.util.retrofit.RetrofitApiClient;
import com.upes.mspdashboard.util.retrofit.model.LoginResponse;
import com.upes.mspdashboard.util.retrofit.model.UserTypeResponse;

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
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        final User user;
        if(loginOpt == LoginOptionFragment.FACULTY_LOGIN) {
            user = new Faculty.Builder()
                    .username(username).password(password)
                    .type(WebApiConstants.UserType.FACULTY).build();
        }
        else if(loginOpt == LoginOptionFragment.STUDENT_LOGIN) {
            user = new Student.Builder()
                    .username(username).password(password)
                    .type(WebApiConstants.UserType.STUDENT)
                    .build();
        } else {
            user = null;
        }

        RetrofitApiClient.getInstance().getAuthClient().login(user)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        final LoginResponse loginResponse = response.body();
                        if(loginResponse!=null) {
                            Log.i(TAG, "auth token : " + loginResponse.getAuthToken());
                            Map<String,String> headers = new HashMap<>();
                            headers.put("Authorization","Token "+loginResponse.getAuthToken());
                            RetrofitApiClient.getInstance().getAuthClient().getUserType(headers,user.getUsername())
                                    .enqueue(new Callback<UserTypeResponse>() {
                                        @Override
                                        public void onResponse(Call<UserTypeResponse> call, Response<UserTypeResponse> response) {
                                            UserTypeResponse utResponse = response.body();
                                            if(utResponse!=null) {
                                                //TODO: check the appropriate type of user to create appropriate session
                                                SessionManager.getInstance(LoginFragment.this.getContext())
                                                        .login(loginResponse.getAuthToken(),SessionManager.SESSION_TYPE_STUDENT,user);
                                                Log.i(TAG,"user type : "+utResponse.getType());
                                                mListener.onLogin(true,user,null );
                                            } else {
                                                Log.i(TAG,"usertype response is null");
                                                try {
                                                    Log.i(TAG, response.errorBody().string());
                                                }catch(IOException ioe) {
                                                    ioe.printStackTrace();
                                                }
                                                mListener.onLogin(false,null,"Authentication Failure");
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<UserTypeResponse> call, Throwable t) {
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
