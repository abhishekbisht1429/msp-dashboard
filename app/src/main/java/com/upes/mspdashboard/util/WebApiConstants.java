package com.upes.mspdashboard.util;

import android.os.Parcel;
import android.os.Parcelable;

public interface WebApiConstants {

    /**
     * ****************************************************
     * Urls for the backend
     */
    String STUDENT_URL = "student";
    String FACULTY_URL = "teacher";
    /**
     * ****************************************************
     */



    /**
     * ****************************************************
     * User type id constants
     */
    int STUDENT_TYPE_ID = 0;
    int HOD_TYPE_ID = 1;
    int AC_TYPE_ID = 2;
    int PROFESSOR_TYPE_ID = 3;

    enum UserType implements Parcelable {
        HOD(HOD_TYPE_ID),
        PROFESSOR(PROFESSOR_TYPE_ID),
        AC(AC_TYPE_ID),
        STUDENT(STUDENT_TYPE_ID);

        UserType(int typeId){
            this.typeId = typeId;
        }
        private int typeId;

        UserType(Parcel in) {
            typeId = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(typeId);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<UserType> CREATOR = new Creator<UserType>() {
            @Override
            public UserType createFromParcel(Parcel in) {
                int typeId = in.readInt();
                return getType(typeId);
            }

            @Override
            public UserType[] newArray(int size) {
                return new UserType[size];
            }
        };

        public int getTypeId() {
            return typeId;
        }

        public static UserType getType(int typeId) {
            switch (typeId) {
                case HOD_TYPE_ID: return HOD;
                case PROFESSOR_TYPE_ID: return PROFESSOR;
                case AC_TYPE_ID : return AC;
                case STUDENT_TYPE_ID : return STUDENT;
                default: return null;
            }
        }
    }
    /**
     * *****************************************************
     */



    /**
     * *****************************************************
     * Serialized names
     */
    String PROPOSAL_TITLE = "title";
    String PROPOSAL_DESCRIPTION = "abstract";
    String PROPOSAL_ID = "id";
    String PROPOSAL_MENTOR = "mentor";
    /**
     * ******************************************************
     */
}
