package com.example.smartscreens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Screen 4: Forgot Password Screen
 * Verifies email address against simulated accounts before allowing password reset.
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    public static final String EXTRA_RESET_EMAIL = "extra_reset_email";

    private EditText etEmail;
    private Button btnRecover;
    private TextView tvBackToLogin;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        sessionManager = new SessionManager(this);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        etEmail = findViewById(R.id.etForgotEmail);
        btnRecover = findViewById(R.id.btnRecoverPassword);
        tvBackToLogin = findViewById(R.id.tvForgotBackToLogin);
    }

    private void setupClickListeners() {
        btnRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAndProceed();
            }
        });

        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void verifyAndProceed() {
        String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_empty_fields));
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            return;
        }

        if (sessionManager.isEmailRegistered(email)) {
            sessionManager.logActivity("Recovery initiated for: " + email);
            Toast.makeText(this, getString(R.string.forgot_success_simulated), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
            intent.putExtra(EXTRA_RESET_EMAIL, email);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.error_email_not_found), Toast.LENGTH_LONG).show();
            etEmail.setError(getString(R.string.error_email_not_found));
        }
    }
}
