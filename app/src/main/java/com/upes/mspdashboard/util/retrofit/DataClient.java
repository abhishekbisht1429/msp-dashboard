package com.upes.mspdashboard.util.retrofit;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.retrofit.model.ProposalSubmitResp;
import com.upes.mspdashboard.util.retrofit.model.UserDetailsResponse;

import java.io.File;
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
import retrofit2.http.Path;

public interface DataClient {
    @GET("accounts/teachers")
    Call<List<UserDetailsResponse>> getFacultyList(@HeaderMap Map<String,String> headers);

    @Multipart
    @POST("application/propose/")
    Call<ProposalSubmitResp> submitProposal(@HeaderMap Map<String, String> headers,
                                            @Part MultipartBody.Part username,
                                            @Part MultipartBody.Part mentorUname,
                                            @Part MultipartBody.Part title,
                                            @Part MultipartBody.Part desc,
                                            @Part MultipartBody.Part file);

    @GET("application/proposal/mentor/{facultyUsername}")
    Call<List<Proposal>> getNewProposals(@HeaderMap Map<String,String> headers,
                                         @Path(value="facultyUsername") String username);


    @GET("application/proposal/mentor/{studentUsername}")
    Call<List<Proposal>> getSubmittedProposals(@HeaderMap Map<String,String> headers,
                                         @Path(value="studentUsername") String username);
}
