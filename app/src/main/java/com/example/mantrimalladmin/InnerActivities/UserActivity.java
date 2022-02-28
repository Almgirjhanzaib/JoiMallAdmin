package com.example.mantrimalladmin.InnerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mantrimalladmin.API;
import com.example.mantrimalladmin.Adapter.UserViewHolder;
import com.example.mantrimalladmin.ItemClickListner;
import com.example.mantrimalladmin.Model.UserModel;
import com.example.mantrimalladmin.R;
import com.example.mantrimalladmin.databinding.ActivityUserBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserActivity extends AppCompatActivity {

    ActivityUserBinding binding;
    FirebaseRecyclerAdapter<UserModel, UserViewHolder> adapter;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("User Activity");
        binding.userRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.userRecycler.setHasFixedSize(true);
        myRef = FirebaseDatabase.getInstance().getReference("User");
        loadUser();


    }

    private void loadUser() {
        FirebaseRecyclerOptions<UserModel> options = new FirebaseRecyclerOptions.Builder<UserModel>()
                .setQuery(myRef,UserModel.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<UserModel, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull UserModel model) {
                holder.textViewUserName.setText(model.getName());
                holder.textViewUserPhone.setText(model.getPhoneNumber());
                holder.textViewUserPassword.setText(model.getPassword());
                holder.textViewUserBalance.setText(model.getBalance());
                holder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClicked) {
                        Intent intent = new Intent(UserActivity.this, UserDetailActivity.class);
                        intent.putExtra("userName",model.getName());
                        intent.putExtra("userId",model.getId());
                        view.getContext().startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_items_for_recycler,parent,false);
                return new UserViewHolder(view);
            }
        };

        binding.userRecycler.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (API.isConnectedToInternet(this)) {
            adapter.startListening();
            adapter.notifyDataSetChanged();
        }else {
            Snackbar snackbar = Snackbar.make(binding.getRoot()," No Internet! Please Check your Connection", Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}