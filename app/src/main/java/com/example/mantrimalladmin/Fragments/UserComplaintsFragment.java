package com.example.mantrimalladmin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mantrimalladmin.API;
import com.example.mantrimalladmin.Model.UserModels.UsersComplaints;
import com.example.mantrimalladmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserComplaintsFragment extends Fragment {

    public static UserComplaintsFragment INSTANCE = null;
    private TextView msg,timeStamp,userId,status;
    public static UserComplaintsFragment getINSTANCE() {
        if (INSTANCE == null){
            INSTANCE = new UserComplaintsFragment();
        }
        return INSTANCE;
    }

    public UserComplaintsFragment() {
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
        return inflater.inflate(R.layout.fragment_user_complaints, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        msg = view.findViewById(R.id.ComplaintsMessage);
        userId = view.findViewById(R.id.ComplaintsUserId);
        status = view.findViewById(R.id.ComplaintsStatus);
        timeStamp = view.findViewById(R.id.ComplaintsTimeStamp);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(API.userNode).child(API.getUserId()).child(API.userComplaintsNode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UsersComplaints model = snapshot.getValue(UsersComplaints.class);
                msg.setText(model.getMsg());
                userId.setText(model.getId());
                status.setText(model.getStatus());
                timeStamp.setText(model.getTimeStamp());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}