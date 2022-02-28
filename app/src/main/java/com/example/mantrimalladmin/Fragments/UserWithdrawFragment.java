package com.example.mantrimalladmin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mantrimalladmin.API;
import com.example.mantrimalladmin.Model.UserModels.UserWithdraw;
import com.example.mantrimalladmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserWithdrawFragment extends Fragment {

public static UserWithdrawFragment INSTANCE = null;
private TextView amount,timeStamp,userNumber,userId,status,accountNumber;

    public static UserWithdrawFragment getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new UserWithdrawFragment();
        return INSTANCE;
    }

    public UserWithdrawFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_withdraw, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            userId = view.findViewById(R.id.WithdrawUserId);
            amount = view.findViewById(R.id.WithdrawAmount);
            accountNumber = view.findViewById(R.id.WithdrawAccountNumber);
            userNumber = view.findViewById(R.id.WithdrawUserNumber);
            status = view.findViewById(R.id.WithdrawStatus);
            timeStamp = view.findViewById(R.id.WithdrawTimeStamp);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(API.userNode);
        myRef.child(API.getUserId()).child(API.userWithdrawsNode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserWithdraw model = snapshot.getValue(UserWithdraw.class);
                userId.setText(model.getUserId());
                amount.setText(model.getAmount());
                accountNumber.setText(model.getAccountNumber());
                userNumber.setText(model.getUserNumber());
                status.setText(model.getStatus());
                timeStamp.setText(model.getTimeStamp());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("error",error.getMessage());
            }
        });
    }
}