package com.example.smartscreens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Screen 5: Reset Password Screen
 * Handles setting and confirming a new password for the verified account.
 */
public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etNewPassword;
    private EditText etConfirmNewPassword;
    private ImageView btnToggleNewPassword;
    private ImageView btnToggleConfirmNewPassword;
    private Button btnResetPassword;
    private TextView tvBackToLogin;

    private boolean isNewPasswordVisible = false;
    private boolean isConfirmNewPasswordVisible = false;

    private SessionManager sessionManager;
    private String targetEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        sessionManager = new SessionManager(this);

        targetEmail = getIntent().getStringExtra(ForgotPasswordActivity.EXTRA_RESET_EMAIL);
        if (targetEmail == null) {
            targetEmail = SessionManager.DEMO_EMAIL;
        }

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        etNewPassword = findViewById(R.id.etResetNewPassword);
        etConfirmNewPassword = findViewById(R.id.etResetConfirmPassword);
        btnToggleNewPassword = findViewById(R.id.btnToggleResetNewPassword);
        btnToggleConfirmNewPassword = findViewById(R.id.btnToggleResetConfirmPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        tvBackToLogin = findViewById(R.id.tvResetBackToLogin);
    }

    private void setupClickListeners() {
        btnToggleNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNewPasswordVisible) {
                    etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnToggleNewPassword.setImageResource(R.drawable.ic_visibility_off);
                    isNewPasswordVisible = false;
                } else {
                    etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnToggleNewPassword.setImageResource(R.drawable.ic_visibility_on);
                    isNewPasswordVisible = true;
                }
                etNewPassword.setSelection(etNewPassword.getText().length());
            }
        });

        btnToggleConfirmNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConfirmNewPasswordVisible) {
                    etConfirmNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnToggleConfirmNewPassword.setImageResource(R.drawable.ic_visibility_off);
                    isConfirmNewPasswordVisible = false;
                } else {
                    etConfirmNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnToggleConfirmNewPassword.setImageResource(R.drawable.ic_visibility_on);
                    isConfirmNewPasswordVisible = true;
                }
                etConfirmNewPassword.setSelection(etConfirmNewPassword.getText().length());
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performPasswordReset();
            }
        });

        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToLogin();
            }
        });
    }

    private void performPasswordReset() {
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmNewPassword = etConfirmNewPassword.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword)) {
            etNewPassword.setError(getString(R.string.error_empty_fields));
            etNewPassword.requestFocus();
            return;
        }

        if (newPassword.length() < 6) {
            etNewPassword.setError(getString(R.string.error_short_password));
            etNewPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmNewPassword)) {
            etConfirmNewPassword.setError(getString(R.string.error_empty_fields));
            etConfirmNewPassword.requestFocus();
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            etConfirmNewPassword.setError(getString(R.string.error_password_mismatch));
            etConfirmNewPassword.requestFocus();
            return;
        }

        boolean success = sessionManager.updatePassword(targetEmail, newPassword);
        if (success) {
            Toast.makeText(this, getString(R.string.reset_success), Toast.LENGTH_LONG).show();
            returnToLogin();
        } else {
            Toast.makeText(this, "Failed to update password. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void returnToLogin() {
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
