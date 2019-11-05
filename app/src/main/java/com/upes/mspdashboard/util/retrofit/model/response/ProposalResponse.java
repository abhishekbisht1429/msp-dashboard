package com.upes.mspdashboard.util.retrofit.model.response;

import com.google.gson.annotations.SerializedName;
import com.upes.mspdashboard.util.WebApiConstants;

import java.util.List;

public class ProposalResponse {


    @SerializedName(WebApiConstants.PROPOSAL_ID)
    private int id;

    @SerializedName(WebApiConstants.PROPOSAL_TEAM_LIST)
    private List<UserDetailsResponse> teamList;

    @SerializedName(WebApiConstants.PROPOSAL_MEMBER_1)
    private UserDetailsResponse member1;

    @SerializedName(WebApiConstants.PROPOSAL_MEMBER_2)
    private UserDetailsResponse member2;

    @SerializedName(WebApiConstants.PROPOSAL_MEMBER_3)
    private UserDetailsResponse member3;

    @SerializedName(WebApiConstants.PROPOSAL_MEMBER_4)
    private UserDetailsResponse member4;

    @SerializedName(WebApiConstants.PROPOSAL_MENTOR)
    private UserDetailsResponse mentor;

    @SerializedName(WebApiConstants.PROPOSAL_TYPE)
    private int type;

    @SerializedName(WebApiConstants.PROPOSAL_TITLE)
    private String title;

    public UserDetailsResponse getMember1() {
        return member1;
    }

    public UserDetailsResponse getMember2() {
        return member2;
    }

    public UserDetailsResponse getMember3() {
        return member3;
    }

    public UserDetailsResponse getMember4() {
        return member4;
    }

    @SerializedName(WebApiConstants.PROPOSAL_DESCRIPTION)
    private String description;

    @SerializedName(WebApiConstants.PROPOSAL_STATUS)
    private int status;

    @SerializedName(WebApiConstants.PROPOSAL_CREATED_AT)
    private String createdAt;

    @SerializedName(WebApiConstants.PROPOSAL_UPDATED_AT)
    private String updatedAt;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public List<UserDetailsResponse> getTeamList() {
        return teamList;
    }

    public UserDetailsResponse getMentor() {
        return mentor;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }
}
