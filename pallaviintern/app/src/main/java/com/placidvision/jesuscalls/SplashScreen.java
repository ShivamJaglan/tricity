package com.placidvision.jesuscalls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
private ImageView imageVieww;
Handler handler;
    public static final String PREFS_NAME = "LoginPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initAnim();
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                if (settings.getString("logged", "").toString().equals("logged")) {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(intent);
                finish();
                }
//                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        },3000);
    }
    void initAnim() {
        imageVieww = findViewById(R.id.jc_anim_ivv);

        startAnimation(500);
    }

    void startAnimation(int delayTime) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatedVectorDrawable avdc = (AnimatedVectorDrawable) getDrawable(R.drawable.jc_anim);
                imageVieww.setImageDrawable(avdc);
                avdc.start();
            }
        }, delayTime);

    }
}