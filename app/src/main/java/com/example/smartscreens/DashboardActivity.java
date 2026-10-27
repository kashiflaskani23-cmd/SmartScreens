package com.example.smartscreens;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Screen 6: Dashboard Screen
 * Central hub displaying interactive 2x2 module cards that open dedicated activities,
 * session details, and confirmation logout dialog.
 */
public class DashboardActivity extends AppCompatActivity {

    private TextView tvWelcomeUser;
    private TextView tvLastLoginBadge;
    private LinearLayout cardHome;
    private LinearLayout cardTools;
    private LinearLayout cardHistory;
    private LinearLayout cardProfile;
    private ImageView btnQuickLogout;
    private Button btnLogoutDashboard;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sessionManager = new SessionManager(this);

        if (!sessionManager.isLoggedIn()) {
            performLogout();
            return;
        }

        initializeViews();
        displayUserData();
        setupModuleClickListeners();
    }

    private void initializeViews() {
        tvWelcomeUser = findViewById(R.id.tvWelcomeUser);
        tvLastLoginBadge = findViewById(R.id.tvLastLoginBadge);
        cardHome = findViewById(R.id.cardHome);
        cardTools = findViewById(R.id.cardTools);
        cardHistory = findViewById(R.id.cardHistory);
        cardProfile = findViewById(R.id.cardProfile);
        btnQuickLogout = findViewById(R.id.btnQuickLogout);
        btnLogoutDashboard = findViewById(R.id.btnLogoutDashboard);
    }

    private void displayUserData() {
        String userName = sessionManager.getLoggedInUserName();
        tvWelcomeUser.setText(getString(R.string.dashboard_welcome, userName));
        tvLastLoginBadge.setText("Last Active: " + sessionManager.getLastLoginTimestamp());
    }

    private void setupModuleClickListeners() {
        // Module 1: Home Control Center Activity
        cardHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logActivity("Opened Home Control Center");
                Intent intent = new Intent(DashboardActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Module 2: Tools & Diagnostics Activity
        cardTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logActivity("Opened Tools & Diagnostics");
                Intent intent = new Intent(DashboardActivity.this, ToolsActivity.class);
                startActivity(intent);
            }
        });

        // Module 3: Activity & Audit Logs
        cardHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logActivity("Viewed Activity Logs");
                Intent intent = new Intent(DashboardActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        // Module 4: Profile Screen
        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logActivity("Navigated to User Profile");
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Quick Logout Toolbar Icon with Confirmation Dialog
        btnQuickLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });

        // Bottom Logout Button with Confirmation Dialog
        btnLogoutDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Logout")
                .setMessage("Are you sure you want to log out of SmartScreens?")
                .setIcon(R.drawable.ic_logout)
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performLogout();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sessionManager.isLoggedIn()) {
            displayUserData();
        }
    }

    private void performLogout() {
        sessionManager.logoutUser();
        Toast.makeText(this, getString(R.string.logout_confirmation), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
