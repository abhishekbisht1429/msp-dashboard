package com.upes.mspdashboard.util.retrofit.model;

import com.google.gson.annotations.SerializedName;
import com.upes.mspdashboard.util.WebApiConstants;

import java.util.List;

public class ProposalResponse {


    @SerializedName(WebApiConstants.PROPOSAL_ID)
    private int id;

    @SerializedName(WebApiConstants.PROPOSAL_TEAM_LIST)
    private List<UserDetailsResponse> teamList;

    @SerializedName(WebApiConstants.PROPOSAL_MENTOR)
    private UserDetailsResponse mentor;

    @SerializedName(WebApiConstants.PROPOSAL_TYPE)
    private int type;
    @SerializedName(WebApiConstants.PROPOSAL_TITLE)
    private String title;

    @SerializedName(WebApiConstants.PROPOSAL_DESCRIPTION)
    private String description;

    @SerializedName(WebApiConstants.PROPOSAL_STATUS)
    private int status;

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
