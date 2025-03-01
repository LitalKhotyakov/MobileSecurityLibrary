package com.example.demoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.permissionslibrary.PermissionLog;
import com.example.permissionslibrary.PermissionManager;
import com.example.permissionslibrary.User;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class UserManagementActivity extends AppCompatActivity {
    private TextInputEditText usernameInput;
    private AutoCompleteTextView permissionDropdown;
    private ArrayAdapter<String> permissionAdapter;
    private ListView userListView;
    private PermissionManager permissionManager;
    private PermissionLog permissionLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        usernameInput = findViewById(R.id.usernameInput);
        permissionDropdown = findViewById(R.id.permissionSpinner);
        userListView = findViewById(R.id.logListView);
        permissionManager = new PermissionManager(this);
        permissionLog = new PermissionLog(this); // Initialize permissionLog



        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed(); // Or finish() if you prefer
        });

        // Set permissions in Spinner
        String[] permissions = {
                "READ_CONTACTS", "ACCESS_FINE_LOCATION", "CAMERA", "READ_EXTERNAL_STORAGE",
                "WRITE_EXTERNAL_STORAGE", "RECORD_AUDIO", "CALL_PHONE"
        };
        permissionAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                permissions
        );
        permissionDropdown.setAdapter(permissionAdapter);

        // Set default selection
        permissionDropdown.setText(permissions[0], false);
        loadUsers();
    }

    public void createUser(View view) {
        String username = usernameInput.getText().toString().trim();
        if (username.isEmpty()) {
            Toast.makeText(this, "Enter a username", Toast.LENGTH_SHORT).show();
            return;
        }
        permissionManager.createUser(username, new PermissionManager.UserCreationCallback() {
            @Override
            public void onUserCreated(User user) {
                runOnUiThread(() -> {
                    Toast.makeText(UserManagementActivity.this, "User Created: " + user.getUsername(), Toast.LENGTH_SHORT).show();
                    loadUsers();
                    usernameInput.setText("");

                });
            }

            @Override
            public void onUserAlreadyExists() {
                runOnUiThread(() -> {
                    Toast.makeText(UserManagementActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    public void assignTemporaryPermission(View view) {
        String username = usernameInput.getText().toString().trim();
        if (username.isEmpty()) {
            Toast.makeText(this, "Enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        String permission = permissionDropdown.getText().toString();
        if (permission.isEmpty()) {
            Toast.makeText(this, "Select a permission level", Toast.LENGTH_SHORT).show();
            return;
        }

        long duration = 60000; // 1 minute
        permissionManager.assignTemporaryPermission(username, permission, duration);

        Toast.makeText(this, "Permission Assigned: " + permission, Toast.LENGTH_SHORT).show();
        permissionLog.logPermissionChange(username, permission, "Assigned");

        // Clear input fields
        usernameInput.setText("");
        permissionDropdown.setText(permissionAdapter.getItem(0), false); // Reset to default
    }
    private void loadUsers() {
        permissionManager.getAllUsers(users -> {
            runOnUiThread(() -> {
                if (users != null && !users.isEmpty()) {
                    ArrayAdapter<User> adapter = new ArrayAdapter<>(UserManagementActivity.this,
                            android.R.layout.simple_list_item_1, users);
                    userListView.setAdapter(adapter);

                    // Handle item click - populate EditText
                    userListView.setOnItemClickListener((parent, view, position, id) -> {
                        User selectedUser = users.get(position);
                        usernameInput.setText(selectedUser.getUsername()); // Set username in EditText
                    });
                } else {
                    Toast.makeText(this, "No users found", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }


}
