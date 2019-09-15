package com.upes.mspdashboard.util;

import android.os.Parcel;
import android.os.Parcelable;

public interface WebApiConstants {
    enum UserType implements Parcelable {
        FACULTY(1),
        STUDENT(2),
        AC(3);
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
                    case 1 : return FACULTY;
                    case 2 : return STUDENT;
                    case 3 : return AC;
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
                case 1 : return FACULTY;
                case 2 : return STUDENT;
                case 3 : return AC;
                default: return null;
            }
        }
    }
}
