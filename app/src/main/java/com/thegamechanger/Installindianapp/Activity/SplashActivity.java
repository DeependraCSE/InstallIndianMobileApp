package com.thegamechanger.Installindianapp.Activity;

import android.os.Bundle;

import com.facebook.stetho.Stetho;
import com.thegamechanger.Installindianapp.Fragment.Splash;
import com.thegamechanger.Installindianapp.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Stetho.initializeWithDefaults(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Splash()).commit();
    }
}
