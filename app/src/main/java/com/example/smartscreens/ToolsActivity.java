package com.example.smartscreens;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Tools & Diagnostics Screen
 * Reads live hardware specs, display resolution, battery level, and provides
 * an interactive display panel calibration test.
 */
public class ToolsActivity extends AppCompatActivity {

    private ImageView btnToolsBack;
    private TextView tvDeviceModel;
    private TextView tvAndroidVersion;
    private TextView tvBatteryStatus;
    private TextView tvScreenResolution;
    private TextView tvScreenDensity;
    private TextView tvScreenRefreshRate;
    private LinearLayout cardColorTest;
    private LinearLayout layoutSwatchBar;
    private TextView tvSwatchLabel;
    private Button btnToolsReturn;

    private int colorIndex = 0;
    private final int[] testColors = new int[]{
            Color.WHITE,
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.BLACK
    };
    private final String[] colorNames = new String[]{
            "White", "Pure Red", "Pure Green", "Pure Blue", "True Black"
    };

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        sessionManager = new SessionManager(this);

        initializeViews();
        loadHardwareInfo();
        setupListeners();
    }

    private void initializeViews() {
        btnToolsBack = findViewById(R.id.btnToolsBack);
        tvDeviceModel = findViewById(R.id.tvDeviceModel);
        tvAndroidVersion = findViewById(R.id.tvAndroidVersion);
        tvBatteryStatus = findViewById(R.id.tvBatteryStatus);
        tvScreenResolution = findViewById(R.id.tvScreenResolution);
        tvScreenDensity = findViewById(R.id.tvScreenDensity);
        tvScreenRefreshRate = findViewById(R.id.tvScreenRefreshRate);
        cardColorTest = findViewById(R.id.cardColorTest);
        layoutSwatchBar = findViewById(R.id.layoutSwatchBar);
        tvSwatchLabel = findViewById(R.id.tvSwatchLabel);
        btnToolsReturn = findViewById(R.id.btnToolsReturn);
    }

    private void loadHardwareInfo() {
        // Device Model & Manufacturer
        String model = Build.MODEL;
        String manufacturer = Build.MANUFACTURER;
        tvDeviceModel.setText("Device: " + manufacturer.toUpperCase() + " " + model);

        // Android OS Version
        tvAndroidVersion.setText("OS Version: Android " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")");

        // Screen Resolution & Metrics
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            display.getRealMetrics(metrics);

            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            int densityDpi = metrics.densityDpi;
            float refreshRate = display.getRefreshRate();

            tvScreenResolution.setText("Resolution: " + width + " × " + height + " px");
            tvScreenDensity.setText("Pixel Density: " + densityDpi + " dpi");
            tvScreenRefreshRate.setText("Refresh Rate: " + Math.round(refreshRate) + " Hz");
        }

        // Live Battery Status
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        if (batteryStatus != null) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryPct = (int) ((level / (float) scale) * 100);

            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            tvBatteryStatus.setText("Battery: " + batteryPct + "% (" + (isCharging ? "Charging" : "Discharging") + ")");
        }
    }

    private void setupListeners() {
        btnToolsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnToolsReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Interactive Screen Swatch Color Cycle
        cardColorTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorIndex = (colorIndex + 1) % testColors.length;
                int selectedColor = testColors[colorIndex];
                String name = colorNames[colorIndex];

                layoutSwatchBar.setBackgroundColor(selectedColor);
                if (selectedColor == Color.BLACK || selectedColor == Color.BLUE) {
                    tvSwatchLabel.setTextColor(Color.WHITE);
                } else {
                    tvSwatchLabel.setTextColor(Color.BLACK);
                }
                tvSwatchLabel.setText("Active Test Swatch: " + name);

                Toast.makeText(ToolsActivity.this, "Display Swatch: " + name, Toast.LENGTH_SHORT).show();
                sessionManager.logActivity("Run screen diagnostic: " + name);
            }
        });
    }
}
