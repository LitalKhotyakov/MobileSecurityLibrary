package com.example.permissionslibrary;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "permissions")
public class Permission {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String permission;
    private long timestamp; // For temporary permissions

    public Permission(String username, String permission, long timestamp) {
        this.username = username;
        this.permission = permission;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public String getPermission() { return permission; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}