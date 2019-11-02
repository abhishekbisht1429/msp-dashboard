package com.upes.mspdashboard.util.retrofit;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.upes.mspdashboard.model.Proposal;
import com.upes.mspdashboard.util.WebApiConstants;
import com.upes.mspdashboard.util.retrofit.model.ProposalResponse;
import com.upes.mspdashboard.util.retrofit.model.ProposalSubmitResp;
import com.upes.mspdashboard.util.retrofit.model.UserDetailsResponse;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface DataClient {
    @GET("accounts/teachers")
    Call<List<UserDetailsResponse>> getFacultyList(@HeaderMap Map<String,String> headers);

    @Multipart
    @POST("application/propose/")
    Call<ProposalSubmitResp> submitProposal(@HeaderMap Map<String, String> headers,
                                            @Part("membershhh") RequestBody members,
                                            @Part("mentor") RequestBody mentor,
                                            @Part("title") RequestBody title,
                                            @Part ("abstract") RequestBody desc,
                                            @Part MultipartBody.Part file);

    @GET("application/proposal/mentor/{facultyUserid}")
    Call<List<ProposalResponse>> getNewProposals(@HeaderMap Map<String,String> headers,
                                                 @Path(value="facultyUserid") int userid);


    @GET("application/proposal/mentor/{studentUsername}")
    Call<List<ProposalResponse>> getSubmittedProposals(@HeaderMap Map<String,String> headers,
                                         @Path(value="studentUsername") String username);

    @PUT("application/proposal/mentor/{prop_id}/changestatus/"+ WebApiConstants.PROPOSAL_STATUS_ACCEPTED)
    Call<String> acceptProposal(@HeaderMap Map<String,String> headers,
                                @Path(value="prop_id")int propId);

    @PUT("application/proposal/mentor/{id}/changestatus/"+ WebApiConstants.PROPOSAL_STATUS_REJECTED)
    Call<String> rejectProposal(@HeaderMap Map<String,String> headers,
                                @Path(value="prop_id")int propId);

    @GET("application/proposals/mentor/accepted/{id}")
    Call<List<ProposalResponse>> getAcceptedProposals(@HeaderMap Map<String,String> headers,
                                              @Path(value="id") int id);

    @GET("application/proposals/excel/{fac_id}")
    @Streaming
    Call<ResponseBody> downloadFile(@HeaderMap Map<String,String> headers,@Path(value="fac_id") int fac_id);

}
