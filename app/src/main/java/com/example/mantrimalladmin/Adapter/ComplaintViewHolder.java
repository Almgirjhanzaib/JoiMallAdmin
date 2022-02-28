package com.example.mantrimalladmin.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mantrimalladmin.R;

public class ComplaintViewHolder extends RecyclerView.ViewHolder {
 public TextView msg,status,userId,timeStamp;
    public ComplaintViewHolder(@NonNull View itemView) {
        super(itemView);

        status = itemView.findViewById(R.id.complaint_status);
        msg = itemView.findViewById(R.id.complaint_msg);
        userId = itemView.findViewById(R.id.complaint_userId);
        timeStamp = itemView.findViewById(R.id.complaint_timestamp);
    }

}
