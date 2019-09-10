package com.upes.mspdashboard.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    public static final int SESSION_TYPE_NONE = 0;
    public static final int SESSION_TYPE_FACULTY = 1;
    public static final int SESSION_TYPE_STUDENT = 2;
    private static final String SESSION_DATA_PREFERENCE_FILE_KEY = "org.upes.mspdashboard.session_file_key";
    private static final String SESSION_TYPE_KEY = "session type";
    private static final String AUTH_TOKEN_KEY = "auth_token";
    private static SessionManager sessionManager;
    private SharedPreferences shPreference;
    private String authToken;
    private int sessionType;
    private Context context;
    private SessionManager(Context context) {
        this.context = context;
        shPreference = context.getSharedPreferences(SESSION_DATA_PREFERENCE_FILE_KEY,Context.MODE_PRIVATE);
        authToken = shPreference.getString(AUTH_TOKEN_KEY,null);
        sessionType = shPreference.getInt(SESSION_TYPE_KEY,SESSION_TYPE_NONE);
    }
    public static SessionManager getInstance(Context context) {
        if(sessionManager==null)
            sessionManager = new SessionManager(context);
        return sessionManager;
    }

    public void login(String token, int sessionType) {
        this.authToken = token;
        this.sessionType = sessionType;
        SharedPreferences.Editor editor = shPreference.edit();
        editor.putString(AUTH_TOKEN_KEY,authToken);
        editor.putInt(SESSION_TYPE_KEY,sessionType);
        editor.commit();
    }

    public void logout() {
        this.authToken = null;
        SharedPreferences.Editor editor = shPreference.edit();
        editor.clear();
        editor.commit();
    }

    public String getAuthToken() {
        return authToken;
    }

    public boolean isSessionAlive() {
        return authToken!=null;
    }

    public int getSessionType() {
        return sessionType;
    }
}
