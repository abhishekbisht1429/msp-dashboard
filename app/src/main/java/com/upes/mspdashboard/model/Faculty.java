package com.upes.mspdashboard.model;
public class Faculty extends User {
    private String imageUrl;
    private Faculty(String username, String password) {
        super(username,password);
    }

    public static class Builder {
        private String username;
        private String password;
        private String imageUrl;

        public Builder() {

        }

        public Faculty build() {
            Faculty faculty = new Faculty(username,password);
            faculty.imageUrl = imageUrl;
            return faculty;
        }
        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
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
    }
}
