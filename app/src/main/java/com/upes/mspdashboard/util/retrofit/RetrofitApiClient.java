package com.upes.mspdashboard.util.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiClient {
    private static final String BASE_URL = "http://10.0.2.2:8000";
    private Retrofit retrofit;
    private static  RetrofitApiClient apiClient;
    private AuthClient authClient;
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

    public AuthClient getAuthClient() {
        if(authClient == null)
            authClient = retrofit.create(AuthClient.class);
        return authClient;
    }
}
