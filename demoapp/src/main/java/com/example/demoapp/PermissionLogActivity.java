package com.example.demoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.permissionslibrary.PermissionLog;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
public class PermissionLogActivity extends AppCompatActivity {
    private RecyclerView logsRecyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private TextView emptyView;
    private LinearProgressIndicator progressIndicator;
    private PermissionLog permissionLog;
    private LogsAdapter logsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_log);

        initializeViews();
        setupToolbar();
        setupRecyclerView();
        setupSwipeRefresh();
        loadLogs();
    }

    private void initializeViews() {
        logsRecyclerView = findViewById(R.id.logsRecyclerView);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        emptyView = findViewById(R.id.emptyView);
        progressIndicator = findViewById(R.id.progressIndicator);
        permissionLog = new PermissionLog(this);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView() {
        logsAdapter = new LogsAdapter();
        logsRecyclerView.setAdapter(logsAdapter);
        logsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener(this::loadLogs);
        swipeRefresh.setColorSchemeResources(R.color.primary);
    }

    private void loadLogs() {
        showLoading(true);
        new Thread(() -> {
            Set<String> logs = permissionLog.getLogs();
            List<String> sortedLogs = new ArrayList<>(logs);
            // Sort by timestamp (assuming timestamp is at the start of the string)
            Collections.sort(sortedLogs, Collections.reverseOrder());

            runOnUiThread(() -> {
                showLoading(false);
                logsAdapter.setLogs(sortedLogs);
                updateEmptyView(sortedLogs.isEmpty());
            });
        }).start();
    }

    private void showLoading(boolean show) {
        progressIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
        swipeRefresh.setRefreshing(false);
    }

    private void updateEmptyView(boolean isEmpty) {
        emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        logsRecyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }


}