package com.kevin.github.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.kevin.github.R;
import com.kevin.github.view.search.SearchActivity;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(SearchActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#2196F3"))
                .withHeaderText("")
                .withFooterText("Copyright 2021")
                .withBeforeLogoText("")
                .withAfterLogoText("Github API")
                .withLogo(R.drawable.github);

        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getBeforeLogoTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}

