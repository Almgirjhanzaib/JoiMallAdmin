package com.example.mantrimalladmin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mantrimalladmin.API;
import com.example.mantrimalladmin.Model.UserModels.UserTransactions;
import com.example.mantrimalladmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TransactionListFragment extends Fragment {

    public static TransactionListFragment INSTANCE = null;
    TextView transactionid, timeStamp, price, bank_number, bank_name;

    public static TransactionListFragment getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new TransactionListFragment();
        return INSTANCE;
    }

    public TransactionListFragment() {
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
        return inflater.inflate(R.layout.fragment_transaction_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transactionid = view. findViewById(R.id.TranscationID);
        timeStamp = view.findViewById(R.id.TranscationTimeStamp);
        bank_name = view.findViewById(R.id.TranscationBankName);
        bank_number = view.findViewById(R.id.TranscationBankNumber);
        price = view.findViewById(R.id.TranscationPrice);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(API.userNode).child(API.getUserId()).child(API.userTransactionNode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserTransactions model = snapshot.getValue(UserTransactions.class);
                transactionid.setText(model.getTransactionid());
                timeStamp.setText(model.getTimeStamp());
                bank_name.setText(model.getBank_name());
                bank_number.setText(model.getBank_number());
                price.setText(model.getPrice());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}