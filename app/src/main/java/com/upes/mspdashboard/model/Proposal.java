package com.upes.mspdashboard.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.upes.mspdashboard.util.WebApiConstants;

import java.util.List;

public class Proposal implements Parcelable {
    public static final Creator<Proposal> CREATOR = new Creator<Proposal>() {
        @Override
        public Proposal createFromParcel(Parcel in) {
            return new Proposal(in);
        }

        @Override
        public Proposal[] newArray(int size) {
            return new Proposal[size];
        }
    };

    @SerializedName(WebApiConstants.PROPOSAL_TITLE)
    private String title;

    @SerializedName(WebApiConstants.PROPOSAL_DESCRIPTION)
    private String description;

    private Student stu;

    private Faculty mentor;

    private List<String> teamList;

    private Uri proposalUri;
    public Proposal() {

    }
    protected Proposal(Parcel in) {
        title = in.readString();
        description = in.readString();
        proposalUri = in.readParcelable(getClass().getClassLoader());
        stu = in.readParcelable(getClass().getClassLoader());
        mentor = in.readParcelable(getClass().getClassLoader());
        in.readList(teamList,getClass().getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeParcelable(proposalUri,flags);
        parcel.writeParcelable(stu,flags);
        parcel.writeParcelable(mentor,flags);
        parcel.writeList(teamList);
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

    public void setDescription(String desc) {
        this.description = desc;
    }

    public Uri getProposalUri() {
        return proposalUri;
    }

    public void setProposalUri(Uri proposalUri) {
        this.proposalUri = proposalUri;
    }

    public Student getStudent() {
        return stu;
    }

    public void setStudent(Student stu) {
        this.stu = stu;
    }

    public Faculty getMentor() {
        return mentor;
    }

    public void setMentor(Faculty mentor) {
        this.mentor = mentor;
    }

    public List<String> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<String> teamList) {
        this.teamList = teamList;
    }
}
