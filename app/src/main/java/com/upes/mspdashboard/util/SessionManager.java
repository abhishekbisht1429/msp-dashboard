package com.upes.mspdashboard.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String SESSION_DATA_PREFERENCE_FILE_KEY = "org.upes.mspdashboard.session_file_key";
    private static final String AUTH_TOKEN_KEY = "auth_token";
    private static SessionManager sessionManager;
    private SharedPreferences shPreference;
    private static String authToken;
    private Context context;
    private SessionManager(Context context) {
        this.context = context;
        shPreference = context.getSharedPreferences(SESSION_DATA_PREFERENCE_FILE_KEY,Context.MODE_PRIVATE);
    }
    public static SessionManager getInstance(Context context) {
        if(sessionManager!=null)
            sessionManager = new SessionManager(context);
        return sessionManager;
    }

    public void login(String token) {
        this.authToken = token;
        SharedPreferences.Editor editor = shPreference.edit();
        editor.putString(AUTH_TOKEN_KEY,authToken);
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
}
