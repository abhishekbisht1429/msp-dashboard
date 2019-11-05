package com.upes.mspdashboard.util.retrofit.model.request;

import android.net.Uri;

import com.upes.mspdashboard.model.Faculty;
import com.upes.mspdashboard.model.Student;

public class ProposalSubmissionRequest {
    Student member1;
    String member2Sap;
    String member3Sap;
    String member4Sap;
    Faculty mentor;
    String title;
    String description;
    Uri ProposalUri;

    public Student getMember1() {
        return member1;
    }

    public void setMember1(Student member1) {
        this.member1 = member1;
    }

    public String getMember2Sap() {
        return member2Sap;
    }

    public void setMember2Sap(String member2Sap) {
        this.member2Sap = member2Sap;
    }

    public String getMember3Sap() {
        return member3Sap;
    }

    public void setMember3Sap(String member3Sap) {
        this.member3Sap = member3Sap;
    }

    public String getMember4Sap() {
        return member4Sap;
    }

    public void setMember4Sap(String member4Sap) {
        this.member4Sap = member4Sap;
    }

    public Faculty getMentor() {
        return mentor;
    }

    public void setMentor(Faculty mentor) {
        this.mentor = mentor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getProposalUri() {
        return ProposalUri;
    }

    public void setProposalUri(Uri proposalUri) {
        ProposalUri = proposalUri;
    }
}
