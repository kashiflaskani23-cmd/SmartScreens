package com.example.smartscreens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Screen 1: Splash Screen
 * Features entrance animations and clean branding before transitioning to
 * either the Login screen or directly to the Dashboard if an active session exists.
 */
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY_MS = 2500;
    private final Handler splashHandler = new Handler(Looper.getMainLooper());
    private boolean hasNavigated = false;
    private SessionManager sessionManager;

    private final Runnable navigationRunnable = new Runnable() {
        @Override
        public void run() {
            if (!hasNavigated && !isFinishing()) {
                hasNavigated = true;

                // If user is already authenticated, take them directly to the Dashboard
                Intent intent;
                if (sessionManager.isLoggedIn()) {
                    intent = new Intent(SplashActivity.this, DashboardActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }

                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManager = new SessionManager(this);

        // Visual Entrance Animation
        ImageView imgLogo = findViewById(R.id.imgSplashLogo);
        TextView tvTitle = findViewById(R.id.tvSplashTitle);
        TextView tvTagline = findViewById(R.id.tvSplashTagline);

        applyEntranceAnimation(imgLogo, 1000);
        applyEntranceAnimation(tvTitle, 1200);
        applyEntranceAnimation(tvTagline, 1400);

        // Schedule transition
        splashHandler.postDelayed(navigationRunnable, SPLASH_DELAY_MS);
    }

    private void applyEntranceAnimation(android.view.View view, int duration) {
        AnimationSet animSet = new AnimationSet(true);

        AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        alpha.setDuration(duration);
        animSet.addAnimation(alpha);

        ScaleAnimation scale = new ScaleAnimation(
                0.85f, 1.0f,
                0.85f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scale.setDuration(duration);
        animSet.addAnimation(scale);

        view.startAnimation(animSet);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashHandler.removeCallbacks(navigationRunnable);
    }
}
