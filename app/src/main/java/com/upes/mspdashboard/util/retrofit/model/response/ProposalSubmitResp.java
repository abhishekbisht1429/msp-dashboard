package com.upes.mspdashboard.util.retrofit.model.response;

import com.google.gson.annotations.SerializedName;

public class ProposalSubmitResp {
    @SerializedName("errors")
    String errors;

    public String getErrors() {
        return errors;
    }
}
