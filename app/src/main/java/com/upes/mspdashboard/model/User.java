package com.upes.mspdashboard.model;

import com.upes.mspdashboard.util.WebApiConstants;

abstract public class User {
    private String username;
    private String password;
    private WebApiConstants.UserType type;
    protected User() {

    }
    protected User(String username, String password, WebApiConstants.UserType type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public WebApiConstants.UserType getType() {
        return type;
    }
}
