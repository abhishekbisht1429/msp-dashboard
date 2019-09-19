package com.upes.mspdashboard.model;

import com.upes.mspdashboard.util.WebApiConstants;
import com.upes.mspdashboard.util.retrofit.model.UserDetailsResponse;

public class Faculty extends User {
    private String imageUrl;
    private Faculty(String username, String password, WebApiConstants.UserType type) {
        super(username,password,type);
    }

    public static class Builder {
        private String username;
        private String password;
        private WebApiConstants.UserType type;
        private String imageUrl;

        public Builder() {

        }

        public Faculty build() {
            Faculty faculty = new Faculty(username,password,type);
            faculty.imageUrl = imageUrl;
            return faculty;
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

        public Builder userDetails(UserDetailsResponse userDetailsResponse) {
            imageUrl = userDetailsResponse.getImageUrl();
            return this;
        }
    }
}
