package com.upes.mspdashboard.util.retrofit;

import com.upes.mspdashboard.model.User;
import com.upes.mspdashboard.util.retrofit.model.LoginResponse;
import com.upes.mspdashboard.util.retrofit.model.LogoutResponse;
import com.upes.mspdashboard.util.retrofit.model.UserTypeResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthClient {
    @Headers("Content-Type: application/json")
    @POST("api/auth/token/login/")
    Call<LoginResponse> login(@Body User user);

    @GET("accounts/check/{username}")
    Call<UserTypeResponse> getUserType(@HeaderMap Map<String,String> headers, @Path(value="username")String username);

    @GET("accounts/check/{username}")
    Call<LogoutResponse> logout(@HeaderMap Map<String,String> headers, @Path(value="username")String username);
}
