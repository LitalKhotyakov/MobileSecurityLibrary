package com.example.permissionslibrary;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;

    public User(String username) {
        this.username = username;
    }
    @Override
    public String toString() {
        return username;  // This will show only the username in ListView
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
}
