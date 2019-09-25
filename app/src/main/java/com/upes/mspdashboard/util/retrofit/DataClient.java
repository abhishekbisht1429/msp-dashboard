package com.upes.mspdashboard.util.retrofit;

import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.retrofit.model.ProposalSubmitResp;
import com.upes.mspdashboard.util.retrofit.model.UserDetailsResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {
    @GET("accounts/teachers")
    Call<List<UserDetailsResponse>> getFacultyList(@HeaderMap Map<String,String> headers);

    @Multipart
    @POST("application/propose/")
    Call<ProposalSubmitResp> submitProposal(@HeaderMap Map<String,String> headers, @Part MultipartBody.Part part);
}
