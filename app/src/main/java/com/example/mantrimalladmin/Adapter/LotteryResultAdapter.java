package com.example.mantrimalladmin.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mantrimalladmin.InnerActivities.UserActivity;
import com.example.mantrimalladmin.ItemClickListner;
import com.example.mantrimalladmin.Model.LotteryModel;
import com.example.mantrimalladmin.R;

import java.io.Serializable;
import java.util.List;

public class LotteryResultAdapter extends RecyclerView.Adapter<LotteryResultAdapter.LotteryViewHolder> {
    List<LotteryModel> list;


    public LotteryResultAdapter(List<LotteryModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public LotteryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LotteryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lottery_result_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LotteryViewHolder holder, int position) {
        //Toast.makeText(holder.itemView.getContext(), ""+list.get(position).getPeriod(), Toast.LENGTH_SHORT).show();
        LotteryModel lists = list.get(position);
        holder.numberTV.setText(lists.getNumber());
        holder.periodTV.setText(lists.getPeriod());
        holder.priceTV.setText(lists.getPrice());

        if (lists.getResultList().size() == 2) {
            if (lists.getResultList().get(0).toLowerCase().contains("red")) {
                holder.imgOne.setImageResource(R.drawable.red_circle);
            } else if (lists.getResultList().get(0).toLowerCase().contains("green")) {
                holder.imgOne.setImageResource(R.drawable.green_circle);
            } else if (lists.getResultList().get(0).toLowerCase().contains("voilet")) {
                holder.imgOne.setImageResource(R.drawable.voilet_circle);
            }
            if (lists.getResultList().get(1).toLowerCase().contains("red")) {
                holder.imgTwo.setImageResource(R.drawable.red_circle);
            } else if (lists.getResultList().get(1).toLowerCase().contains("green")) {
                holder.imgTwo.setImageResource(R.drawable.green_circle);
            } else if (lists.getResultList().get(1).toLowerCase().contains("voilet")) {
                holder.imgTwo.setImageResource(R.drawable.voilet_circle);
            }
        } else {
            if (lists.getResultList().get(0).toLowerCase().contains("red")) {
                holder.imgOne.setImageResource(R.drawable.red_circle);
            } else if (lists.getResultList().get(0).toLowerCase().contains("green")) {
                holder.imgOne.setImageResource(R.drawable.green_circle);
            } else if (lists.getResultList().get(0).toLowerCase().contains("voilet")) {
                holder.imgOne.setImageResource(R.drawable.voilet_circle);
            }
        }

        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int position, boolean isLongClicked) {
               // Toast.makeText(view.getContext(), "Clicked"+ lists.getNumber(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class LotteryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView priceTV, periodTV, numberTV;
        ImageView imgOne, imgTwo;

        public void setItemClickListner(ItemClickListner itemClickListner) {
            this.itemClickListner = itemClickListner;
        }

        ItemClickListner itemClickListner;
        public LotteryViewHolder(@NonNull View itemView) {
            super(itemView);
            priceTV = itemView.findViewById(R.id.perice_id);
            periodTV = itemView.findViewById(R.id.tv_period_id);
            numberTV = itemView.findViewById(R.id.tv_lotery_number);
            imgOne = itemView.findViewById(R.id.img_one);
            imgTwo = itemView.findViewById(R.id.img_two);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            itemClickListner.onClick(v,getAbsoluteAdapterPosition(),false);
        }
    }
}
