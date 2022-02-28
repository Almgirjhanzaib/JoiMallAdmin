package com.example.mantrimalladmin.InnerActivities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mantrimalladmin.API;
import com.example.mantrimalladmin.Adapter.UserFragmentAdapter;
import com.example.mantrimalladmin.databinding.ActivityUserDetailBinding;


public class UserDetailActivity extends AppCompatActivity {

    private ActivityUserDetailBinding binding;
    private String userName,userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userName = getIntent().getStringExtra("userName");
        userId = getIntent().getStringExtra("userId");

        binding.toolbar.setTitle(userName);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UserFragmentAdapter adapter = new UserFragmentAdapter(getSupportFragmentManager(),this);
        binding.ViewPagerUser.setAdapter(adapter);
        binding.tabLayoutUser.setupWithViewPager(binding.ViewPagerUser);
        API.setUserId(userId);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        API.setUserId("");
    }
}