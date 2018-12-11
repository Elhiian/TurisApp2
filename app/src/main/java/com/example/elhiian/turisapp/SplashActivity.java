package com.example.elhiian.turisapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity {
    LinearLayout layoutLogo,layoutName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        layoutLogo=findViewById(R.id.layoutloogo);
        layoutName=findViewById(R.id.layoutname);
        layoutLogo.setAnimation(AnimationUtils.loadAnimation(this,R.anim.downtoup));
        layoutName.setAnimation(AnimationUtils.loadAnimation(this,R.anim.uptodown));
        Configuracion.fragment=null;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        }, 2000);
    }
}
