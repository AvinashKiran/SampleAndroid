package com.demo.android.demoapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.android.demoapplication.PrefManager;

public class SplashActivity extends AppCompatActivity {


    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!prefManager.isFirstTimeLaunch()) {
                    launchHomeScreen();
                    finish();
                } else {
                    launchTutorialScreen();
                }
            }
        }, 1000);





    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    private void launchTutorialScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(SplashActivity.this, TutorialActivity.class));
        finish();
    }

}