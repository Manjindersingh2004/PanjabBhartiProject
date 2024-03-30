package com.example.panjabbharti.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.panjabbharti.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
      // Animation slide1=AnimationUtils.loadAnimation(this, R.anim.slide);
        Animation move1=AnimationUtils.loadAnimation(this, R.anim.move);
        Animation blink1=AnimationUtils.loadAnimation(this, R.anim.blink);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
        ImageView imageView = findViewById(R.id.imageView);
        //imageView.startAnimation(animation);
//        TextView textView =findViewById(R.id.textView);
//        textView.startAnimation(move1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,Department_Activity.class));
                finish();
            }
        },1500);



    }
}