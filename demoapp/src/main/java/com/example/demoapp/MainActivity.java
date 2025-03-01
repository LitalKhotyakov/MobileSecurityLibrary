package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void openUserManagement(View view) {
        startActivity(new Intent(this, UserManagementActivity.class));
    }

    public void openPermissionsCheck(View view) {
        startActivity(new Intent(this, PermissionsCheckActivity.class));
    }

    public void openPermissionLogs(View view) {
        startActivity(new Intent(this, PermissionLogActivity.class));
    }
}
