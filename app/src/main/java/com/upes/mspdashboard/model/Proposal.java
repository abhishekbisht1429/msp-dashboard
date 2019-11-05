package com.upes.mspdashboard.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.upes.mspdashboard.util.WebApiConstants;
import com.upes.mspdashboard.util.retrofit.model.response.ProposalResponse;
import com.upes.mspdashboard.util.retrofit.model.response.UserDetailsResponse;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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

    private int id;

    private String title;

    private String description;

    private Faculty mentor;

    private List<Student> teamList;

    private Uri proposalUri;

    private int projectType;

    private int status;

    private Date createdAt;

    private Date updatedAt;

    public int getProjectType() {
        return projectType;
    }

    public int getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Proposal() {

    }
    protected Proposal(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        proposalUri = in.readParcelable(getClass().getClassLoader());
        mentor = in.readParcelable(getClass().getClassLoader());
        teamList = new ArrayList<>();
        in.readList(teamList,Student.class.getClassLoader());
        projectType = in.readInt();
        status = in.readInt();
        long creationTS = in.readLong();
        long updationTS = in.readLong();
        createdAt = new Date(creationTS);
        updatedAt = new Date(updationTS);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeParcelable(proposalUri,flags);
        parcel.writeParcelable(mentor,flags);
        parcel.writeList(teamList);
        parcel.writeInt(projectType);
        parcel.writeInt(status);
        parcel.writeLong(createdAt.getTime());
        parcel.writeLong(updatedAt.getTime());
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public Faculty getMentor() {
        return mentor;
    }

    public void setMentor(Faculty mentor) {
        this.mentor = mentor;
    }

    public List<Student> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Student> teamList) {
        this.teamList = teamList;
    }

    public void setPropsalDetails(ProposalResponse propResp) {
        this.id = propResp.getId();

        List<UserDetailsResponse> udrList = new ArrayList<>();
        if(propResp.getMember1()!=null) udrList.add(propResp.getMember1());
        if(propResp.getMember2()!=null) udrList.add(propResp.getMember2());
        if(propResp.getMember3()!=null) udrList.add(propResp.getMember3());
        if(propResp.getMember4()!=null) udrList.add(propResp.getMember4());
        List<Student> teamList = new ArrayList<>();
        for(UserDetailsResponse udr:udrList) {
            teamList.add(new Student.Builder()
                    .id(udr.getUserCred().getId())
                    .username(udr.getUserCred().getUsername())
                    .type(WebApiConstants.UserType.getType(udr.getTypeId()))
                    .userDetails(udr)
                    .build());
        }
        this.teamList = teamList;

        UserDetailsResponse udr = propResp.getMentor();
        this.mentor = new Faculty.Builder()
                            .id(udr.getUserCred().getId())
                            .username(udr.getUserCred().getUsername())
                            .type(WebApiConstants.UserType.getType(udr.getTypeId()))
                            .userDetails(udr)
                            .build();

        this.projectType = propResp.getType();
        this.title = propResp.getTitle();
        this.description = propResp.getDescription();
        this.status = propResp.getStatus();
        try {
            this.createdAt = WebApiConstants.SERVER_DATE_FORMAT.parse(propResp.getCreatedAt());
            this.updatedAt = WebApiConstants.SERVER_DATE_FORMAT.parse(propResp.getUpdatedAt());
        } catch(ParseException pE) {
            pE.printStackTrace();
        }
    }

}
