package com.example.mantrimalladmin.InnerActivities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mantrimalladmin.API;
import com.example.mantrimalladmin.Adapter.ComplaintViewHolder;
import com.example.mantrimalladmin.Model.ComplaintModel;
import com.example.mantrimalladmin.R;
import com.example.mantrimalladmin.databinding.ActivityComplaintsBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComplaintsActivity extends AppCompatActivity {

    ActivityComplaintsBinding binding;
    FirebaseRecyclerAdapter<ComplaintModel, ComplaintViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComplaintsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Complaints");
        binding.complaintRecycler.setHasFixedSize(true);
        binding.complaintRecycler.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        FirebaseRecyclerOptions<ComplaintModel> options = new FirebaseRecyclerOptions.Builder<ComplaintModel>()
                .setQuery(reference.child(API.complaintsNode), ComplaintModel.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<ComplaintModel, ComplaintViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position, @NonNull ComplaintModel model) {
                holder.status.setText(model.getStatus());
                holder.timeStamp.setText(model.getTimeStamp());
                holder.userId.setText(model.getUserId());
                holder.msg.setText(model.getMsg());
            }

            @NonNull
            @Override
            public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ComplaintViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.complaints_item,parent,false));
            }
        };

            binding.complaintRecycler.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.startListening();

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