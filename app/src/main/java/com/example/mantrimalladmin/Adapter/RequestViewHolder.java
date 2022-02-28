package com.example.mantrimalladmin.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mantrimalladmin.R;

public class RequestViewHolder extends RecyclerView.ViewHolder {
   public TextView accountNumber,amount,status,timeStamp,userId,userNumber;
    public RequestViewHolder(@NonNull View itemView) {
        super(itemView);
        accountNumber = itemView.findViewById(R.id.request_accountNo);
        userId = itemView.findViewById(R.id.request_userId);
        amount = itemView.findViewById(R.id.request_amount);
        timeStamp = itemView.findViewById(R.id.request_timestamp);
        status = itemView.findViewById(R.id.request_status);
        userNumber = itemView.findViewById(R.id.request_userNo);
    }
}
