package com.example.smartscreens;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * SessionManager handles local storage simulation for authentication, sessions,
 * and activity audit logs using Android SharedPreferences.
 *
 * CLASSROOM DEMO NOTE:
 * This simulation allows full offline demonstration of an enterprise-style
 * multi-screen application with zero external cloud or server dependencies.
 */
public class SessionManager {

    private static final String PREF_NAME = "SmartScreensPrefs";

    // Session Keys
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_ACTIVE_EMAIL = "activeEmail";
    private static final String KEY_ACTIVE_NAME = "activeName";
    private static final String KEY_LAST_LOGIN = "lastLoginTimestamp";
    private static final String KEY_HISTORY_LOGS = "activityHistoryLogs";

    // Account Storage Prefixes
    private static final String PREFIX_PASSWORD = "pwd_";
    private static final String PREFIX_NAME = "name_";

    // Documented Demo Credentials
    public static final String DEMO_EMAIL = "demo@example.com";
    public static final String DEMO_PASSWORD = "123456";
    public static final String DEMO_NAME = "Demo Student User";

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        seedDemoAccountIfNecessary();
    }

    /**
     * Seeds the documented demo account if it does not already exist.
     */
    private void seedDemoAccountIfNecessary() {
        if (!isEmailRegistered(DEMO_EMAIL)) {
            registerUser(DEMO_NAME, DEMO_EMAIL, DEMO_PASSWORD);
            logActivity("Demo account initialized (demo@example.com)");
        }
    }

    /**
     * Registers a new simulated user account locally.
     */
    public boolean registerUser(String fullName, String email, String password) {
        if (email == null || password == null) {
            return false;
        }
        String normalizedEmail = email.trim().toLowerCase();
        editor.putString(PREFIX_NAME + normalizedEmail, fullName.trim());
        editor.putString(PREFIX_PASSWORD + normalizedEmail, password);
        boolean saved = editor.commit();
        if (saved) {
            logActivity("New account registered: " + normalizedEmail);
        }
        return saved;
    }

    /**
     * Checks if an email is registered in local storage or matches demo account.
     */
    public boolean isEmailRegistered(String email) {
        if (email == null) return false;
        String normalizedEmail = email.trim().toLowerCase();
        return preferences.contains(PREFIX_PASSWORD + normalizedEmail);
    }

    /**
     * Validates credentials against locally registered users or the demo account.
     */
    public boolean validateCredentials(String email, String password) {
        if (email == null || password == null) return false;
        String normalizedEmail = email.trim().toLowerCase();
        String storedPassword = preferences.getString(PREFIX_PASSWORD + normalizedEmail, null);
        return storedPassword != null && storedPassword.equals(password);
    }

    /**
     * Creates an active login session for the user and records the login timestamp.
     */
    public void createLoginSession(String email, String fullName) {
        String currentTimestamp = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(new Date());
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_ACTIVE_EMAIL, email.trim().toLowerCase());
        editor.putString(KEY_ACTIVE_NAME, fullName != null ? fullName.trim() : getUserNameByEmail(email));
        editor.putString(KEY_LAST_LOGIN, currentTimestamp);
        editor.apply();

        logActivity("User logged in: " + email);
    }

    /**
     * Checks if there is an active login session.
     */
    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    /**
     * Returns the name of the currently logged-in user.
     */
    public String getLoggedInUserName() {
        return preferences.getString(KEY_ACTIVE_NAME, DEMO_NAME);
    }

    /**
     * Returns the email of the currently logged-in user.
     */
    public String getLoggedInUserEmail() {
        return preferences.getString(KEY_ACTIVE_EMAIL, DEMO_EMAIL);
    }

    /**
     * Returns the timestamp of the last successful login.
     */
    public String getLastLoginTimestamp() {
        return preferences.getString(KEY_LAST_LOGIN, "First active session");
    }

    /**
     * Retrieves the stored full name for a specific email address.
     */
    public String getUserNameByEmail(String email) {
        if (email == null) return DEMO_NAME;
        String normalizedEmail = email.trim().toLowerCase();
        return preferences.getString(PREFIX_NAME + normalizedEmail, DEMO_NAME);
    }

    /**
     * Updates the password for an existing registered user.
     */
    public boolean updatePassword(String email, String newPassword) {
        if (email == null || newPassword == null) return false;
        String normalizedEmail = email.trim().toLowerCase();
        if (isEmailRegistered(normalizedEmail)) {
            editor.putString(PREFIX_PASSWORD + normalizedEmail, newPassword);
            boolean updated = editor.commit();
            if (updated) {
                logActivity("Password updated for: " + normalizedEmail);
            }
            return updated;
        }
        return false;
    }

    /**
     * Appends an audit entry into the activity history log.
     */
    public void logActivity(String action) {
        String timestamp = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(new Date());
        String entry = "[" + timestamp + "] " + action;
        String currentLogs = preferences.getString(KEY_HISTORY_LOGS, "");
        if (currentLogs.isEmpty()) {
            currentLogs = entry;
        } else {
            currentLogs = entry + "\n" + currentLogs; // latest first
        }
        editor.putString(KEY_HISTORY_LOGS, currentLogs);
        editor.apply();
    }

    /**
     * Retrieves list of recent activity logs.
     */
    public List<String> getActivityLogs() {
        String logsRaw = preferences.getString(KEY_HISTORY_LOGS, "");
        List<String> list = new ArrayList<>();
        if (!logsRaw.isEmpty()) {
            String[] split = logsRaw.split("\n");
            for (String s : split) {
                if (!s.trim().isEmpty()) {
                    list.add(s.trim());
                }
            }
        }
        if (list.isEmpty()) {
            list.add("No recent activity logged yet.");
        }
        return list;
    }

    /**
     * Clears all activity audit logs.
     */
    public void clearHistoryLogs() {
        editor.remove(KEY_HISTORY_LOGS);
        editor.apply();
        logActivity("Activity history cleared.");
    }

    /**
     * Clears only the active login session, preserving all registered account records.
     */
    public void logoutUser() {
        String email = getLoggedInUserEmail();
        editor.remove(KEY_IS_LOGGED_IN);
        editor.remove(KEY_ACTIVE_EMAIL);
        editor.remove(KEY_ACTIVE_NAME);
        editor.remove(KEY_LAST_LOGIN);
        editor.apply();

        logActivity("User logged out: " + email);
    }
}
