package com.example.smartscreens;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

/**
 * Interactive Home Control Center
 * Allows dynamic brightness adjustment, screen wake lock toggle, and haptic preferences.
 */
public class HomeActivity extends AppCompatActivity {

    private ImageView btnHomeBack;
    private SeekBar seekBarBrightness;
    private TextView tvBrightnessValue;
    private SwitchCompat switchHapticFeedback;
    private SwitchCompat switchKeepScreenOn;
    private SwitchCompat switchAuditLogs;
    private Button btnHomeReturn;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        btnHomeBack = findViewById(R.id.btnHomeBack);
        seekBarBrightness = findViewById(R.id.seekBarBrightness);
        tvBrightnessValue = findViewById(R.id.tvBrightnessValue);
        switchHapticFeedback = findViewById(R.id.switchHapticFeedback);
        switchKeepScreenOn = findViewById(R.id.switchKeepScreenOn);
        switchAuditLogs = findViewById(R.id.switchAuditLogs);
        btnHomeReturn = findViewById(R.id.btnHomeReturn);
    }

    private void setupListeners() {
        btnHomeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnHomeReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Real-time window brightness adjustment
        seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int safeProgress = Math.max(10, progress); // avoid complete black screen
                tvBrightnessValue.setText(safeProgress + "%");

                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.screenBrightness = safeProgress / 100.0f;
                getWindow().setAttributes(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sessionManager.logActivity("Adjusted window brightness to " + seekBar.getProgress() + "%");
            }
        });

        // Keep Screen On toggle
        switchKeepScreenOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    Toast.makeText(HomeActivity.this, "Screen wake-lock enabled.", Toast.LENGTH_SHORT).show();
                    sessionManager.logActivity("Enabled screen wake-lock");
                } else {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    Toast.makeText(HomeActivity.this, "Screen wake-lock disabled.", Toast.LENGTH_SHORT).show();
                    sessionManager.logActivity("Disabled screen wake-lock");
                }
            }
        });

        switchHapticFeedback.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String state = isChecked ? "enabled" : "disabled";
                Toast.makeText(HomeActivity.this, "Haptic feedback " + state, Toast.LENGTH_SHORT).show();
                sessionManager.logActivity("Touch haptics " + state);
            }
        });

        switchAuditLogs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String state = isChecked ? "active" : "paused";
                Toast.makeText(HomeActivity.this, "Audit logs " + state, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
