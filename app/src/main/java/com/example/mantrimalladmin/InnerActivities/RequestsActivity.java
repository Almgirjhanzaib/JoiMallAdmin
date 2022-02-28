package com.example.mantrimalladmin.InnerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mantrimalladmin.API;
import com.example.mantrimalladmin.Adapter.RequestViewHolder;
import com.example.mantrimalladmin.Model.RequestModel;
import com.example.mantrimalladmin.R;
import com.example.mantrimalladmin.databinding.ActivityRequestsBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestsActivity extends AppCompatActivity {
    ActivityRequestsBinding binding;
    FirebaseRecyclerAdapter<RequestModel, RequestViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Withdraw Request");
        binding.requestRecycler.setHasFixedSize(true);
        binding.requestRecycler.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        FirebaseRecyclerOptions<RequestModel> options = new FirebaseRecyclerOptions.Builder<RequestModel>()
                .setQuery(reference.child(API.withdrawRequestNode),RequestModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<RequestModel, RequestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RequestViewHolder holder, int position, @NonNull RequestModel model) {
                holder.userNumber.setText(model.getUserNumber());
                holder.status.setText(model.getStatus());
                holder.timeStamp.setText(model.getTimeStamp());
                holder.amount.setText(model.getAmount());
                holder.accountNumber.setText(model.getAccountNumber());
                holder.userId.setText(model.getUserId());
            }

            @NonNull
            @Override
            public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requests_item,parent,false);
                return new RequestViewHolder(view);
            }
        };

        binding.requestRecycler.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}