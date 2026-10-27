package com.example.smartscreens;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**
 * Screen displaying audit log history of user interactions.
 */
public class HistoryActivity extends AppCompatActivity {

    private ImageView btnHistoryBack;
    private ImageView btnClearHistory;
    private ListView listHistory;
    private Button btnHistoryReturn;

    private SessionManager sessionManager;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        sessionManager = new SessionManager(this);

        initializeViews();
        loadHistoryList();
        setupListeners();
    }

    private void initializeViews() {
        btnHistoryBack = findViewById(R.id.btnHistoryBack);
        btnClearHistory = findViewById(R.id.btnClearHistory);
        listHistory = findViewById(R.id.listHistory);
        btnHistoryReturn = findViewById(R.id.btnHistoryReturn);
    }

    private void loadHistoryList() {
        List<String> logs = sessionManager.getActivityLogs();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                logs
        );
        listHistory.setAdapter(adapter);
    }

    private void setupListeners() {
        btnHistoryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnHistoryReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HistoryActivity.this)
                        .setTitle("Clear History")
                        .setMessage("Do you want to purge all recorded activity history logs?")
                        .setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sessionManager.clearHistoryLogs();
                                loadHistoryList();
                                Toast.makeText(HistoryActivity.this, "History cleared.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }
}
