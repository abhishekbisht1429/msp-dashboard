package com.upes.mspdashboard.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.upes.mspdashboard.util.WebApiConstants;
import com.upes.mspdashboard.util.retrofit.model.response.UserDetailsResponse;

public class Faculty extends User implements Parcelable {
    public static final Creator<Faculty> CREATOR = new Creator<Faculty>() {
        @Override
        public Faculty createFromParcel(Parcel in) {
            return new Faculty(in);
        }

        @Override
        public Faculty[] newArray(int size) {
            return new Faculty[size];
        }
    };
    private String firstname;
    private String lastname;
    private String email;
    private String imageUrl;
    private String fieldOfStudy;
    private int slotsOccupied;
    private String department;
    private String phoneNo;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
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

    public String getDepartment() {
        return department;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    private Faculty(int id,String username, String password, WebApiConstants.UserType type) {
        super(id, username, password, type);
    }

    protected Faculty(Parcel in) {
        this(in.readInt(),in.readString(),in.readString(), WebApiConstants.UserType.getType(in.readInt()));
        firstname = in.readString();
        lastname = in.readString();
        email = in.readString();
        imageUrl = in.readString();
        fieldOfStudy = in.readString();
        slotsOccupied = in.readInt();
        department = in.readString();
        phoneNo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getUserId());
        parcel.writeString(getUsername());
        parcel.writeString(getPassword());
        parcel.writeInt(getType().getTypeId());
        parcel.writeString(firstname);
        parcel.writeString(lastname);
        parcel.writeString(email);
        parcel.writeString(imageUrl);
        parcel.writeString(fieldOfStudy);
        parcel.writeInt(slotsOccupied);
        parcel.writeString(phoneNo);
        parcel.writeString(department);
    }

    public static class Builder {
        private int id;
        private String username;
        private String password;
        private WebApiConstants.UserType type;
        private String firstName;
        private String lastName;
        private String email;
        private String imageUrl;
        private String fieldOfStudy;
        private int slotsOccupied;
        private String department;
        private String phoneNo;


        public Builder() {

        }

        public Faculty build() {
            Faculty faculty = new Faculty(id,username,password,type);
            faculty.firstname = firstName;
            faculty.lastname = lastName;
            faculty.email = email;
            faculty.imageUrl = imageUrl;
            faculty.fieldOfStudy = fieldOfStudy;
            faculty.slotsOccupied = slotsOccupied;
            faculty.department = department;
            faculty.phoneNo = phoneNo;
            return faculty;
        }
        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder type(WebApiConstants.UserType type) {
            this.type = type;
            return this;
        }

        public Builder firstname(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastName = lastname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder fieldOfStudy(String fieldOfStudy) {
            this.fieldOfStudy = fieldOfStudy;
            return this;
        }
        public Builder slotsOccupied(int slotsOccupied) {
            this.slotsOccupied = slotsOccupied;
            return this;
        }
        public Builder department(String department) {
            this.department = department;
            return this;
        }
        public Builder phoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
            return this;
        }
        public Builder userDetails(UserDetailsResponse userDetailsResponse) {
            id = userDetailsResponse.getUserCred().getId();
            firstName = userDetailsResponse.getUserCred().getFirstName();
            lastName = userDetailsResponse.getUserCred().getLastName();
            email = userDetailsResponse.getUserCred().getEmail();
            imageUrl = userDetailsResponse.getImageUrl();
            fieldOfStudy = userDetailsResponse.getFieldOfStudy();
            slotsOccupied = userDetailsResponse.getSlotsOccupied();
            department = userDetailsResponse.getDepartment();
            phoneNo = userDetailsResponse.getPhoneNo();
            return this;
        }
    }
}
