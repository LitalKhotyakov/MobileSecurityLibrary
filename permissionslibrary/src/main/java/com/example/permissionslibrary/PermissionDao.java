package com.example.permissionslibrary;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PermissionDao {

    @Query("SELECT DISTINCT username FROM users")
    List<String> getAllUsernames();

    // User operations
    @Insert
    long insertUser(User user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    User getUserByUsername(String username);

    @Query("SELECT * FROM permissions WHERE username = :username")
    List<Permission> getPermissionsByUsername(String username);

    @Query("DELETE FROM permissions WHERE username = :username AND permission = :permission")
    void deletePermission(String username, String permission);

    @Insert
    void insertPermission(Permission permission);

    @Query("UPDATE permissions SET permission = :newPermission WHERE username = :username AND permission = :oldPermission")
    void updatePermission(String username, String oldPermission, String newPermission);

    @Query("SELECT * FROM permissions WHERE timestamp > 0 AND timestamp < :currentTime")
    List<Permission> getExpiredPermissions(long currentTime);
}

