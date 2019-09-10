package com.upes.mspdashboard.util.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiClient {
    private static final String BASE_URL = "http://10.0.2.2:8000";
    private Retrofit retrofit;
    private static  RetrofitApiClient apiClient;
    private LoginClient loginClient;
    private RetrofitApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitApiClient getInstance() {
        if(apiClient==null)
            apiClient = new RetrofitApiClient();
        return apiClient;
    }

    public LoginClient getLoginClient() {
        if(loginClient == null)
            loginClient = retrofit.create(LoginClient.class);
        return loginClient;
    }
}
