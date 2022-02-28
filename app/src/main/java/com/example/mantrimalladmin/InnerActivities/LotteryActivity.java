package com.example.mantrimalladmin.InnerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mantrimalladmin.Model.LotteryModel;
import com.example.mantrimalladmin.R;
import com.example.mantrimalladmin.databinding.ActivityLotteryBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LotteryActivity extends AppCompatActivity {
    ActivityLotteryBinding binding;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLotteryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        myRef = FirebaseDatabase.getInstance().getReference("Lottery");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot mySnap : dataSnapshot.getChildren()) {
                LotteryModel lists = mySnap.getValue(LotteryModel.class);
                binding.numberTV.setText(lists.getNumber());
                binding.periodTV.setText(lists.getPeriod());
                binding.priceTV.setText(lists.getPrice());
                if (lists.getResultList().size() == 2) {
                    if (lists.getResultList().get(0).toLowerCase().contains("red")) {
                        binding.imgOne.setImageResource(R.drawable.red_circle);
                    } else if (lists.getResultList().get(0).toLowerCase().contains("green")) {
                        binding.imgOne.setImageResource(R.drawable.green_circle);
                    } else if (lists.getResultList().get(0).toLowerCase().contains("voilet")) {
                        binding.imgOne.setImageResource(R.drawable.voilet_circle);
                    }
                    if (lists.getResultList().get(1).toLowerCase().contains("red")) {
                        binding.imgTwo.setImageResource(R.drawable.red_circle);
                    } else if (lists.getResultList().get(1).toLowerCase().contains("green")) {
                        binding.imgTwo.setImageResource(R.drawable.green_circle);
                    } else if (lists.getResultList().get(1).toLowerCase().contains("voilet")) {
                        binding.imgTwo.setImageResource(R.drawable.voilet_circle);
                    }
                } else {
                    if (lists.getResultList().get(0).toLowerCase().contains("red")) {
                        binding.imgOne.setImageResource(R.drawable.red_circle);
                    } else if (lists.getResultList().get(0).toLowerCase().contains("green")) {
                        binding.imgOne.setImageResource(R.drawable.green_circle);
                    } else if (lists.getResultList().get(0).toLowerCase().contains("voilet")) {
                        binding.imgOne.setImageResource(R.drawable.voilet_circle);
                    }
                }
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}