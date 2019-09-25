package com.upes.mspdashboard.util.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class represents the retrofit client for the application.
 * It is the endpoint through which the app communicates with the backend server
 * @author reckoner1429
 */
public class RetrofitApiClient {
    private static final boolean PRODUCTION  = false;
    private static final String TEST_URL = "http://10.0.2.2:8000";
    private static final String PRODUCTION_URL = "";
    private static final String BASE_URL = PRODUCTION?PRODUCTION_URL:TEST_URL;
    private Retrofit retrofit;
    private static  RetrofitApiClient apiClient;
    private AuthClient authClient;
    private DataClient dataClient;
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

    public DataClient getDataClient() {
        if(dataClient==null)
            dataClient = retrofit.create(DataClient.class);
        return dataClient;
    }
}
