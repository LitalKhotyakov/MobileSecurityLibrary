package com.example.permissionslibrary;

import android.content.Context;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PermissionManager {
    private AppDatabase db;
    private PermissionDao dao;
    private ExecutorService executor;

    public PermissionManager(Context context) {
        this.db = AppDatabase.getInstance(context);
        this.dao = db.permissionDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    // Create user
    public void createUser(String username, UserCreationCallback callback) {
        executor.execute(() -> {
            if (dao.getUserByUsername(username) == null) {
                User user = new User(username);
                dao.insertUser(user);
                callback.onUserCreated(user);
            } else {
                callback.onUserAlreadyExists();
            }
        });
    }
    // Fetch all permissions assigned to a user
    public void getUserPermissions(String username, PermissionsCallback callback) {
        executor.execute(() -> {
            List<Permission> permissions = dao.getPermissionsByUsername(username);
            callback.onPermissionsFetched(permissions);
        });
    }

    // Define the callback interface to handle async fetching
    public interface PermissionsCallback {
        void onPermissionsFetched(List<Permission> permissions);
    }
    // Assign temporary permission
    public void assignTemporaryPermission(String username, String permission, long duration) {
        long expiryTime = System.currentTimeMillis() + duration;
        executor.execute(() -> {
            dao.insertPermission(new Permission(username, permission, expiryTime));
        });
    }

    // Get all users
    public void getAllUsers(UsersCallback callback) {
        executor.execute(() -> {
            List<User> users = dao.getAllUsers();
            callback.onUsersFetched(users);
        });
    }

    // Get all usernames
    public void getAllUsernames(UsernamesCallback callback) {
        executor.execute(() -> {
            List<String> usernames = dao.getAllUsernames();
            callback.onUsernamesFetched(usernames);
        });
    }

    // Callbacks
    public interface UserCreationCallback {
        void onUserCreated(User user);
        void onUserAlreadyExists();
    }


    public interface UsersCallback {
        void onUsersFetched(List<User> users);
    }

    public interface UsernamesCallback {
        void onUsernamesFetched(List<String> usernames);
    }
}