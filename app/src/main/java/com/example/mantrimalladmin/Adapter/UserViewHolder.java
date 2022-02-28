package com.example.mantrimalladmin.Adapter;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mantrimalladmin.ItemClickListner;
import com.example.mantrimalladmin.R;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView textViewUserName,textViewUserPhone,textViewUserPassword,textViewUserBalance;
    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    ItemClickListner itemClickListner;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewUserName = itemView.findViewById(R.id.textViewNameUser);
        textViewUserBalance = itemView.findViewById(R.id.textViewBalance);
        textViewUserPassword = itemView.findViewById(R.id.textViewPasswordUser);
        textViewUserPhone = itemView.findViewById(R.id.textViewPhoneNoUser);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAbsoluteAdapterPosition(),false);
    }
}
