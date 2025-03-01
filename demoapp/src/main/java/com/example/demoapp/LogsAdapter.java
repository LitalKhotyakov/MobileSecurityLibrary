package com.example.demoapp;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.LogViewHolder> {
    private List<String> logs = new ArrayList<>();
    private final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private final SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault());

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        String log = logs.get(position);
        holder.bind(log);
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    void setLogs(List<String> newLogs) {
        logs = newLogs;
        notifyDataSetChanged();
    }

    class LogViewHolder extends RecyclerView.ViewHolder {
        private final ImageView statusIcon;
        private final TextView userNameText;
        private final TextView actionText;
        private final TextView timestampText;

        LogViewHolder(@NonNull View itemView) {
            super(itemView);
            statusIcon = itemView.findViewById(R.id.statusIcon);
            userNameText = itemView.findViewById(R.id.userNameText);
            actionText = itemView.findViewById(R.id.actionText);
            timestampText = itemView.findViewById(R.id.timestampText);
        }

        void bind(String log) {
            String[] parts = log.split(" \\| ");
            if (parts.length >= 3) {
                // Parse timestamp
                String timestamp = parts[0];
                try {
                    Date date = inputFormat.parse(timestamp);
                    if (date != null) {
                        timestampText.setText(outputFormat.format(date));
                    } else {
                        timestampText.setText(timestamp);
                    }
                } catch (ParseException e) {
                    timestampText.setText(timestamp);
                }

                // Parse username
                String username = parts[1].replace("User: ", "");
                userNameText.setText(username);

                // Parse action and permission
                String[] actionParts = parts[2].split(" Permission: ");
                String action = actionParts[0];
                String permission = actionParts.length > 1 ? actionParts[1] : "";
                actionText.setText(String.format("%s permission: %s", action, permission));


            }
        }
    }
}