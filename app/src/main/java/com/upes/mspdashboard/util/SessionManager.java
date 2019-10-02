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
    private static final String STU_ENR_NO_KEY = "student enr key";
    private static final String STU_SAP_ID_KEY = "sap id key";
    private static final String STU_SEMESTER_KEY = "student sem key";
    private static final String STU_PROGRAM_KEY = "student program key";
    private static final String STU_CGPA_KEY = "student cgpa key";
    private static final String FAC_FIELD_OF_STUDY_KEY = "field of study faculty";
    private static final String FAC_SLOTS_OCCUPIED_KEY = "faculty slots occupied";
    private static final String FAC_PHONE_NO_KEY = "faculty phone no key";
    private static final String FAC_DEPARTMENT_KEY = "faculty department key";
    private static final String FAC_FIRSTNAME_KEY = "faculty first name";
    private static final String FAC_LASTNAME_KEY = "faculty lastname";
    private static final String FAC_EMAIL_KEY = "faculty email key";
    private static final String STU_FIRSTNAME_KEY = "student firstname key";
    private static final String STU_LASTNAME_KEY = "student lastname key";
    private static final String STU_EMAIL_KEY = "student email key";

    private static SessionManager sessionManager;
    private SharedPreferences shPreference;
    private Context context;
    private String authToken;
    private int sessionType;
    private User user;

    private SessionManager(Context context) {
        this.context = context;
        shPreference = context.getSharedPreferences(SESSION_DATA_PREFERENCE_FILE_KEY,Context.MODE_PRIVATE);
        retrieveSavedInfo();
    }
    public static SessionManager getInstance(Context context) {
        if(sessionManager==null)
            sessionManager = new SessionManager(context);
        return sessionManager;
    }

    private void retrieveSavedInfo() {
        authToken = shPreference.getString(AUTH_TOKEN_KEY,null);
        sessionType = shPreference.getInt(SESSION_TYPE_KEY,SESSION_TYPE_NONE);
        String username = shPreference.getString(USERNAME_KEY,null);
        String password = shPreference.getString(PASSWORD_KEY,null);
        int typeId = shPreference.getInt(USER_TYPE_KEY,-1);

        if(sessionType == SESSION_TYPE_FACULTY) {
            retrieveFaculty(username,password,typeId);
        } else if(sessionType==SESSION_TYPE_STUDENT){
            retrieveStudent(username,password,typeId);
        } else {
            user = null;
        }
    }

    /**
     * @param token
     * @param sessionType
     * @param user
     * Call this function to create a new user session.
     */
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
        if(user.getType()== WebApiConstants.UserType.STUDENT) {
            saveStudent(editor,(Student)user);
        } else {
            saveFaculty(editor,(Faculty)user);
        }
        editor.commit();
    }

    /**
     * Call this method to destroy any existing session
     */
    public void logout() {
        SharedPreferences.Editor editor = shPreference.edit();
        editor.clear();
        editor.commit();
        //clear the fields of session manager
        clearFields();
    }

    /**
     * Helper method to save student details locally
     * @param editor
     * @param student
     */
    private void saveStudent(SharedPreferences.Editor editor,Student student) {
        editor.putString(STU_FIRSTNAME_KEY,student.getFirstname());
        editor.putString(STU_LASTNAME_KEY,student.getLastname());
        editor.putString(STU_EMAIL_KEY,student.getEmail());
        editor.putString(STU_ENR_NO_KEY,student.getEnrNo());
        editor.putString(STU_SAP_ID_KEY,student.getSapId());
        editor.putString(STU_SEMESTER_KEY,student.getSemeser());
        editor.putString(STU_PROGRAM_KEY,student.getProgram());
        editor.putFloat(STU_CGPA_KEY,student.getCgpa());
    }

    /**
     * Helper method to retrieve the student details from local storage
     * @param username
     * @param password
     * @param typeId
     */
    private void retrieveStudent(String username, String password,int typeId) {
        Student student = new Student.Builder()
                .username(username)
                .password(password)
                .type(WebApiConstants.UserType.getType(typeId))
                .firstname(shPreference.getString(STU_FIRSTNAME_KEY,null))
                .lastname(shPreference.getString(STU_LASTNAME_KEY,null))
                .email(shPreference.getString(STU_EMAIL_KEY,null))
                .enrNo(shPreference.getString(STU_ENR_NO_KEY,null))
                .sapId(shPreference.getString(STU_SAP_ID_KEY,null))
                .semester(shPreference.getString(STU_SEMESTER_KEY,null))
                .program(shPreference.getString(STU_PROGRAM_KEY,null))
                .cgpa(shPreference.getFloat(STU_CGPA_KEY,0.0f))
                .build();
        user = student;
    }

    /**
     * Helper method to save Faculty details locally
     * @param editor
     * @param faculty
     */
    private void saveFaculty(SharedPreferences.Editor editor,Faculty faculty) {
        editor.putString(FAC_FIRSTNAME_KEY,faculty.getFirstname());
        editor.putString(FAC_LASTNAME_KEY,faculty.getLastname());
        editor.putString(FAC_EMAIL_KEY,faculty.getEmail());
        editor.putString(FAC_FIELD_OF_STUDY_KEY,faculty.getFieldOfStudy());
        editor.putInt(FAC_SLOTS_OCCUPIED_KEY,faculty.getSlotsOccupied());
        editor.putString(FAC_PHONE_NO_KEY,faculty.getPhoneNo());
        editor.putString(FAC_DEPARTMENT_KEY,faculty.getDepartment());
    }

    /**
     * Helper method to retrieve facutly details from local storage
     * @param username
     * @param password
     * @param typeId
     */
    private void retrieveFaculty(String username, String password,int typeId) {
        Faculty faculty = new Faculty.Builder()
                .username(username)
                .password(password)
                .type(WebApiConstants.UserType.getType(typeId))
                .firstname(shPreference.getString(FAC_FIRSTNAME_KEY,null))
                .lastname(shPreference.getString(FAC_LASTNAME_KEY,null))
                .email(shPreference.getString(FAC_EMAIL_KEY,null))
                .fieldOfStudy(shPreference.getString(FAC_FIELD_OF_STUDY_KEY,null))
                .slotsOccupied(shPreference.getInt(FAC_SLOTS_OCCUPIED_KEY,0))
                .phoneNo(shPreference.getString(FAC_PHONE_NO_KEY,null))
                .department(shPreference.getString(FAC_DEPARTMENT_KEY,null))
                .build();
        user = faculty;
    }

    private void clearFields() {
        this.authToken = null;
        this.sessionType = SESSION_TYPE_NONE;
        this.user = null;
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
