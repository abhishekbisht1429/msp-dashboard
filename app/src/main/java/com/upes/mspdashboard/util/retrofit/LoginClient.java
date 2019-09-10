package com.upes.mspdashboard.util.retrofit;

import com.upes.mspdashboard.model.User;
import com.upes.mspdashboard.util.retrofit.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginClient {
    @Headers("Content-Type: application/json")
    @POST("api/auth/token/login/")
    Call<LoginResponse> login(@Body User user);
}
