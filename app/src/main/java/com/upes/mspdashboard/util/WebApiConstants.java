package com.upes.mspdashboard.util;

import android.os.Parcel;
import android.os.Parcelable;

public interface WebApiConstants {
    String STUDENT_URL = "student";
    String FACULTY_URL = "teacher";
    int STUDENT_TYPE_ID = 0;
    int FACULTY_TYPE_ID = 1;
    int AC_TYPE_ID = 2;
    enum UserType implements Parcelable {
        FACULTY(FACULTY_TYPE_ID),
        STUDENT(STUDENT_TYPE_ID),
        AC(AC_TYPE_ID);

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
                switch (typeId) {
                    case FACULTY_TYPE_ID : return FACULTY;
                    case STUDENT_TYPE_ID : return STUDENT;
                    case AC_TYPE_ID  : return AC;
                    default: return null;
                }
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
                case FACULTY_TYPE_ID : return FACULTY;
                case STUDENT_TYPE_ID : return STUDENT;
                case AC_TYPE_ID : return AC;
                default: return null;
            }
        }
    }
}
