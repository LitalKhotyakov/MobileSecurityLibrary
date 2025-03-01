package com.example.demoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.permissionslibrary.Permission;
import com.example.permissionslibrary.PermissionManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.widget.ArrayAdapter;

import java.util.List;

public class PermissionsCheckActivity extends AppCompatActivity {
    private AutoCompleteTextView userDropdown;
    private TextView resultText;
    private PermissionManager permissionManager;
    private LinearProgressIndicator progressIndicator;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions_check);

        // Initialize views
        userDropdown = findViewById(R.id.userSpinner);
        resultText = findViewById(R.id.resultText);
        progressIndicator = findViewById(R.id.progressIndicator);
        permissionManager = new PermissionManager(this);

        // Setup toolbar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        loadUsers();
    }

    private void loadUsers() {
        showLoading(true);
        executor.execute(() -> {
            permissionManager.getAllUsernames(usernames -> runOnUiThread(() -> {
                ArrayAdapter<String> userAdapter = new ArrayAdapter<>(
                        this,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        usernames
                );
                userDropdown.setAdapter(userAdapter);
                showLoading(false);

                if (usernames.isEmpty()) {
                    userDropdown.setHint("No users available");
                } else {
                    userDropdown.setText(usernames.get(0), false);
                }
            }));
        });
    }

    public void checkPermission(View view) {
        String username = userDropdown.getText().toString();
        if (username.isEmpty() || username.equals("No Users Found")) {
            showError("Please select a valid user");
            return;
        }

        showLoading(true);
        permissionManager.getUserPermissions(username, permissions -> {
            runOnUiThread(() -> {
                showLoading(false);
                if (permissions.isEmpty()) {
                    showNoPermissions();
                } else {
                    showPermissions(permissions);
                }
            });
        });
    }

    private void showLoading(boolean show) {
        progressIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showError(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getColor(R.color.red))
                .setTextColor(getColor(R.color.white))
                .show();
    }

    private void showNoPermissions() {
        resultText.setText("❌ No permissions assigned to this user");
    }

    private void showPermissions(List<Permission> permissions) {
        StringBuilder permissionsText = new StringBuilder();
        for (Permission perm : permissions) {
            permissionsText.append("✅ ").append(perm.getPermission()).append("\n");
        }
        resultText.setText(permissionsText.toString().trim());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}