package com.example.ksustudycabin;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {
    private static final String USER_EMAIL_KEY = "user_email";
    private static final String SHARED_PREF_NAME = "user_session";

    private static UserSessionManager instance;
    private final SharedPreferences sharedPreferences;

    private UserSessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static UserSessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserSessionManager(context);
        }
        return instance;
    }

    public void setUserEmail(String email) {
        sharedPreferences.edit().putString(USER_EMAIL_KEY, email).apply();
    }

    public String getUserEmail() {
        return sharedPreferences.getString(USER_EMAIL_KEY, null);
    }
}