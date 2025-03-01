package com.example.permissionslibrary;

import android.content.Context;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class PermissionLog {
    private static final String PREF_NAME = "permission_logs";
    private static final String LOG_KEY = "logs";
    private SharedPreferences sharedPreferences;

    public PermissionLog(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void logPermissionChange(String username, String permission, String action) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> logs = sharedPreferences.getStringSet(LOG_KEY, new HashSet<>());

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String logEntry = timestamp + " | User: " + username + " | " + action + " Permission: " + permission;

        logs.add(logEntry);
        editor.putStringSet(LOG_KEY, logs);
        editor.apply();
    }

    public Set<String> getLogs() {
        return sharedPreferences.getStringSet(LOG_KEY, new HashSet<>());
    }
}