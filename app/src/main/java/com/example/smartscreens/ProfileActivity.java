package com.example.smartscreens;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Screen 7: Profile Screen
 * Displays authenticated user information and handles session termination (logout).
 */
public class ProfileActivity extends AppCompatActivity {

    private ImageView btnBackArrow;
    private TextView tvFullName;
    private TextView tvEmail;
    private Button btnBackToDashboard;
    private Button btnLogout;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = new SessionManager(this);

        if (!sessionManager.isLoggedIn()) {
            performLogout();
            return;
        }

        initializeViews();
        loadUserProfile();
        setupClickListeners();
    }

    private void initializeViews() {
        btnBackArrow = findViewById(R.id.btnBackArrow);
        tvFullName = findViewById(R.id.tvProfileFullName);
        tvEmail = findViewById(R.id.tvProfileEmail);
        btnBackToDashboard = findViewById(R.id.btnBackToDashboard);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void loadUserProfile() {
        String name = sessionManager.getLoggedInUserName();
        String email = sessionManager.getLoggedInUserEmail();

        tvFullName.setText(name);
        tvEmail.setText(email);
    }

    private void setupClickListeners() {
        // Return to Dashboard via Toolbar arrow
        btnBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Return to Dashboard via dedicated Button
        btnBackToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Logout action with confirmation dialog
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Confirm Logout")
                        .setMessage("Are you sure you want to log out?")
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
        });
    }

    /**
     * Terminates user session and clears back stack to prevent navigating back to Dashboard.
     */
    private void performLogout() {
        sessionManager.logoutUser();
        Toast.makeText(this, getString(R.string.logout_confirmation), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
