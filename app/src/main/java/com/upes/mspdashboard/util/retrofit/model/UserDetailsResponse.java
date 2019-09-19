package com.upes.mspdashboard.util.retrofit.model;

import com.google.gson.annotations.SerializedName;
import com.upes.mspdashboard.model.Faculty;
import com.upes.mspdashboard.model.Student;
import com.upes.mspdashboard.model.User;
import com.upes.mspdashboard.util.WebApiConstants;

public class UserDetailsResponse {
    @SerializedName("id")
    int id;

    @SerializedName("user")
    String email;

    @SerializedName("avatar")
    String imageUrl;

    @SerializedName("field_of_study")
    String fieldOfStudy;

    @SerializedName("slots_occupied")
    String slotsOccupied;

    @SerializedName("phone")
    String phoneNo;

    @SerializedName("department")
    String department;

    @SerializedName("type_of_user")
    int typeId;

    @SerializedName("enrollment_number")
    String enrNo;
    
    @SerializedName("program")
    String program;
    
    @SerializedName("semester")
    String semester;
    
    @SerializedName("cgpa")
    double cgpa;
    
    UserDetailsResponse() {

    }

    public int getTypeId() {
        return typeId;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public String getSlotsOccupied() {
        return slotsOccupied;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getDepartment() {
        return department;
    }

    public String getEnrNo() {
        return enrNo;
    }

    public String getProgram() {
        return program;
    }

    public String getSemester() {
        return semester;
    }

    public double getCgpa() {
        return cgpa;
    }
}
