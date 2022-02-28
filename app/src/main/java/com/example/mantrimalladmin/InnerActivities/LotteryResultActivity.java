package com.example.mantrimalladmin.InnerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mantrimalladmin.Adapter.LotteryResultAdapter;
import com.example.mantrimalladmin.Model.LotteryModel;
import com.example.mantrimalladmin.R;
import com.example.mantrimalladmin.databinding.ActivityLotteryResultBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class LotteryResultActivity extends AppCompatActivity {
        ActivityLotteryResultBinding binding;
        DatabaseReference myRef;
        LotteryResultAdapter adapter;
      private AlertDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLotteryResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        waitingDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();
        waitingDialog.show();

        binding.lotteryResultRecycler.setHasFixedSize(true);
        binding.lotteryResultRecycler.setLayoutManager(new LinearLayoutManager(this));
        List<LotteryModel> list = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference("LotteryResults");
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot mySnap : snapshot.getChildren()){
                    LotteryModel model  = mySnap.getValue(LotteryModel.class);

                    list.add(model);
                }
                adapter = new LotteryResultAdapter(list);
                binding.lotteryResultRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                waitingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                waitingDialog.dismiss();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        waitingDialog.dismiss();
    }
}