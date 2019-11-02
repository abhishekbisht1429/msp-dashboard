package com.upes.mspdashboard.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.upes.mspdashboard.util.WebApiConstants;
import com.upes.mspdashboard.util.retrofit.model.ProposalResponse;
import com.upes.mspdashboard.util.retrofit.model.UserDetailsResponse;

import java.util.ArrayList;
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

    private Student stu;

    private Faculty mentor;

    private List<Student> teamList;

    private Uri proposalUri;

    private int projectType;

    private int status;

    public Proposal() {

    }
    protected Proposal(Parcel in) {
        title = in.readString();
        description = in.readString();
        proposalUri = in.readParcelable(getClass().getClassLoader());
        mentor = in.readParcelable(getClass().getClassLoader());
        in.readList(teamList,getClass().getClassLoader());
        projectType = in.readInt();
        status = in.readInt();
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
        parcel.writeInt(projectType);
        parcel.writeInt(status);
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

    public List<Student> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Student> teamList) {
        this.teamList = teamList;
    }

    public void setPropsalDetails(ProposalResponse propResp) {
        this.id = propResp.getId();


        List<Student> teamList = new ArrayList<>();
        for(UserDetailsResponse udr:propResp.getTeamList()) {
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
    }

}
