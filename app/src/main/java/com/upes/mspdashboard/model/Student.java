package com.upes.mspdashboard.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.upes.mspdashboard.util.WebApiConstants;
import com.upes.mspdashboard.util.retrofit.model.response.UserDetailsResponse;

public class Student extends User implements Parcelable {
    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
    private String firstname;
    private String lastname;
    private String email;
    private String imageUrl;
    private String enrNo;
    private String semeser;
    private String sapId;
    private String program;
    private float cgpa;
    private int lock;

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

    public String getEnrNo() {
        return enrNo;
    }

    public String getSemeser() {
        return semeser;
    }

    public String getSapId() {
        return sapId;
    }

    public String getProgram() {
        return program;
    }

    public float getCgpa() {
        return cgpa;
    }

    public int getLock() {
        return lock;
    }

    private Student(int id,String username, String password, WebApiConstants.UserType type) {
        super(id, username, password, type);
    }

    protected Student(Parcel in) {
        this(in.readInt(),in.readString(),in.readString(), WebApiConstants.UserType.getType(in.readInt()));
        firstname = in.readString();
        lastname = in.readString();
        email = in.readString();
        imageUrl = in.readString();
        enrNo = in.readString();
        semeser = in.readString();
        sapId = in.readString();
        program = in.readString();
        cgpa = in.readFloat();
        lock = in.readInt();
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
        parcel.writeString(enrNo);
        parcel.writeString(semeser);
        parcel.writeString(sapId);
        parcel.writeString(program);
        parcel.writeFloat(cgpa);
        parcel.writeInt(lock);
    }

    public static class Builder {
        private int id;
        private String username;
        private String password;
        private WebApiConstants.UserType type;
        private String firstname;
        private String lastname;
        private String email;
        private String imageUrl;
        private String enrNo;
        private String semeser;
        private String sapId;
        private String program;
        private float cgpa;
        private int lock;

        public Builder() {

        }

        public Student build() {
            Student student = new Student(id,username,password,type);
            student.firstname = firstname;
            student.lastname = lastname;
            student.email = email;
            student.imageUrl = imageUrl;
            student.enrNo = enrNo;
            student.semeser = semeser;
            //TODO: sap id
            student.program = program;
            student.cgpa = cgpa;
            student.lock = lock;
            return student;
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

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
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

        public Builder enrNo(String enrNo) {
            this.enrNo = enrNo;
            return this;
        }

        public Builder semester(String semeser) {
            this.semeser = semeser;
            return this;
        }

        public Builder sapId(String sapId) {
            this.sapId = sapId;
            return this;
        }

        public Builder program(String program) {
            this.program = program;
            return this;
        }

        public Builder cgpa(float cgpa) {
            this.cgpa = cgpa;
            return this;
        }

        public Builder lock(int lock) {
            this.lock = lock;
            return this;
        }

        public Builder userDetails(UserDetailsResponse userDetailsResponse) {
            id = userDetailsResponse.getUserCred().getId();
            firstname = userDetailsResponse.getUserCred().getFirstName();
            lastname = userDetailsResponse.getUserCred().getLastName();
            email = userDetailsResponse.getUserCred().getEmail();
            imageUrl = userDetailsResponse.getImageUrl();
            enrNo = userDetailsResponse.getEnrNo();
            semeser = userDetailsResponse.getSemester();
            //sapId = userDetailsResponse.getSapId();
            program = userDetailsResponse.getProgram();
            cgpa = userDetailsResponse.getCgpa();
            lock = userDetailsResponse.getLock();
            return this;
        }
    }
}
