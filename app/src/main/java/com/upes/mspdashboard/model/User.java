package com.upes.mspdashboard.model;

public class User {
    private String username;
    private String password;
    private User() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {
        private String username;
        private String password;
        public Builder() {

        }
        public User build() {
            User user = new User();
            user.username = this.username;
            user.password = this.password;
            return user;
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
