package com.upes.mspdashboard.util.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class ProposalSubmitResp {
    @SerializedName("errors")
    String errors;

    public String getErrors() {
        return errors;
    }
}
