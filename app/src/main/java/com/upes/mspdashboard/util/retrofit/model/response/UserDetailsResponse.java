package com.upes.mspdashboard.util.retrofit.model.response;

import com.google.gson.annotations.SerializedName;
import com.upes.mspdashboard.util.WebApiConstants;

public class UserDetailsResponse {
    @SerializedName("user")
    UserCred userCred;

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

    @SerializedName(WebApiConstants.STUDENT_LOCK)
    int lock;

    UserDetailsResponse() {

    }

    public int getLock() {
        return lock;
    }

    public int getTypeId() {
        return typeId;
    }

    public UserCred getUserCred() {
        return userCred;
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


    public class UserCred {
        @SerializedName("id")
        int id;

        @SerializedName("username")
        String username;

        @SerializedName("email")
        String email;

        @SerializedName("first_name")
        String firstName;

        @SerializedName("last_name")
        String lastName;

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }
}
