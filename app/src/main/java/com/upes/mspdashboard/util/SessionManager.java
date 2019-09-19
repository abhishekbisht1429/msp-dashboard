package com.upes.mspdashboard.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.upes.mspdashboard.model.Faculty;
import com.upes.mspdashboard.model.Student;
import com.upes.mspdashboard.model.User;

/**
 * The SessionManager manages all the user sessions on the android app. It has a singleton pattern.
 * Vairous parts of the app can use the methods of this class to check whether a seessoion is in
 * progress and if yes then which session it is and what are the user details associated with
 * the session
 * @author reckoner1429
 */
public class SessionManager {
    public static final int SESSION_TYPE_NONE = 0;
    public static final int SESSION_TYPE_FACULTY = 1;
    public static final int SESSION_TYPE_STUDENT = 2;
    private static final String SESSION_DATA_PREFERENCE_FILE_KEY = "org.upes.mspdashboard.session_file_key";
    private static final String SESSION_TYPE_KEY = "session type";
    private static final String AUTH_TOKEN_KEY = "auth_token";
    private static final String USERNAME_KEY = "username key";
    private static final String PASSWORD_KEY = "password_key";
    private static final String USER_TYPE_KEY = "Type key";
    private static SessionManager sessionManager;
    private SharedPreferences shPreference;
    private String authToken;
    private int sessionType;
    private User user;
    private Context context;
    private SessionManager(Context context) {
        this.context = context;
        shPreference = context.getSharedPreferences(SESSION_DATA_PREFERENCE_FILE_KEY,Context.MODE_PRIVATE);
        authToken = shPreference.getString(AUTH_TOKEN_KEY,null);
        sessionType = shPreference.getInt(SESSION_TYPE_KEY,SESSION_TYPE_NONE);
        String username = shPreference.getString(USERNAME_KEY,null);
        String password = shPreference.getString(PASSWORD_KEY,null);
        int typeId = shPreference.getInt(USER_TYPE_KEY,-1);
        if(sessionType == SESSION_TYPE_FACULTY) {
            user = new Faculty.Builder()
                    .username(username)
                    .password(password)
                    .type(WebApiConstants.UserType.getType(typeId))
                    //TODO: add other faculty details
                    .build();
        } else if(sessionType==SESSION_TYPE_STUDENT){
            user = new Student.Builder()
                    .username(username)
                    .password(password)
                    .type(WebApiConstants.UserType.STUDENT)
                    .build();
        }
    }
    public static SessionManager getInstance(Context context) {
        if(sessionManager==null)
            sessionManager = new SessionManager(context);
        return sessionManager;
    }

    public void login(String token, int sessionType, User user) {
        this.authToken = token;
        this.sessionType = sessionType;
        this.user = user;
        SharedPreferences.Editor editor = shPreference.edit();
        editor.putString(AUTH_TOKEN_KEY,authToken);
        editor.putInt(SESSION_TYPE_KEY,sessionType);
        editor.putString(USERNAME_KEY,user.getUsername());
        editor.putString(PASSWORD_KEY,user.getPassword());
        editor.putInt(USER_TYPE_KEY,user.getType().getTypeId());
        editor.commit();
    }
    private void clearFields() {
        this.authToken = null;
        this.sessionType = SESSION_TYPE_NONE;
        this.user = null;
    }

    public void logout() {
        clearFields();
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

    public User getUser() {
        return user;
    }
}
