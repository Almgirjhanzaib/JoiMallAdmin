package com.example.mantrimalladmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.mantrimalladmin.InnerActivities.LotteryActivity;
import com.example.mantrimalladmin.InnerActivities.LotteryResultActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, HomeDrawerActivity.class));
                finish();
            }
        },3000);
    }


}