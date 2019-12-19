package com.upes.mspdashboard.util.retrofit;

import com.upes.mspdashboard.util.WebApiConstants;
import com.upes.mspdashboard.util.retrofit.model.response.ProposalResponse;
import com.upes.mspdashboard.util.retrofit.model.response.ProposalSubmitResp;
import com.upes.mspdashboard.util.retrofit.model.response.UserDetailsResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
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
                                            @Part("member1") RequestBody members,
                                            @Part("member2_sapid") RequestBody member2Sap,
                                            @Part("member3_sapid") RequestBody member3Sap,
                                            @Part("mentor") RequestBody mentor,
                                            @Part("title") RequestBody title,
                                            @Part ("abstract") RequestBody desc,
                                            @Part MultipartBody.Part file);

    @GET("application/proposal/mentor/{facultyUserid}")
    Call<List<ProposalResponse>> getNewProposals(@HeaderMap Map<String,String> headers,
                                                 @Path(value="facultyUserid") int userid);


    @GET("application/proposal/student/{studentUserId}")
    Call<List<ProposalResponse>> getSubmittedProposals(@HeaderMap Map<String,String> headers,
                                         @Path(value="studentUserId") int userid);

    @PUT("application/proposal/mentor/{prop_id}/changestatus/"+ WebApiConstants.PROPOSAL_STATUS_ACCEPTED)
    Call<ResponseBody> acceptProposal(@HeaderMap Map<String,String> headers,
                                @Path(value="prop_id")int propId);

    @PUT("application/proposal/mentor/{prop_id}/changestatus/"+ WebApiConstants.PROPOSAL_STATUS_REJECTED)
    Call<ResponseBody> rejectProposal(@HeaderMap Map<String,String> headers,
                                @Path(value="prop_id")int propId);

    @GET("application/proposals/mentor/accepted/{id}")
    Call<List<ProposalResponse>> getAcceptedProposals(@HeaderMap Map<String,String> headers,
                                              @Path(value="id") int id);

    @GET("application/proposals/excel/accepted/{fac_id}/status/{prop_status}")
    @Streaming
    Call<ResponseBody> downloadFile(@HeaderMap Map<String,String> headers,@Path(value="fac_id") int fac_id,
                                    @Path(value="prop_status") int propStatus);

}
