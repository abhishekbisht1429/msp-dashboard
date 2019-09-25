package com.upes.mspdashboard.util.retrofit.model;

import com.google.gson.annotations.SerializedName;

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
    int slotsOccupied;

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
    float cgpa;
    
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

    public int getSlotsOccupied() {
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

    public float getCgpa() {
        return cgpa;
    }
}
