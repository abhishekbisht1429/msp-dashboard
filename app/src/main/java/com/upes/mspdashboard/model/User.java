package com.upes.mspdashboard.model;

import com.upes.mspdashboard.util.WebApiConstants;

abstract public class User {
    private int id;
    private String username;
    private String password;
    private WebApiConstants.UserType type;
    protected User() {

    }
    protected User(int id, String username, String password, WebApiConstants.UserType type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public int getUserId() {
        return id;
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
