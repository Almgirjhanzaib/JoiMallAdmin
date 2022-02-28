package com.example.mantrimalladmin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mantrimalladmin.Model.BiddingUser;
import com.example.mantrimalladmin.R;

import java.util.List;

public class BiddingAdapter extends RecyclerView.Adapter<BiddingAdapter.BiddingViewHolder> {
    List<BiddingUser> list;

    public BiddingAdapter(List<BiddingUser> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public BiddingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bidding_user_item,parent,false);
        return new BiddingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiddingViewHolder holder, int position) {
        BiddingUser model = list.get(position);
        holder.tradePrice.setText(model.getTradePrice());
        holder.color.setText(model.getColor());
        holder.number.setText(model.getNumber());
        holder.userId.setText(model.getUserId());
        holder.name.setText(model.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BiddingViewHolder extends RecyclerView.ViewHolder{
        TextView userId;
        TextView name;
        TextView number;
        TextView color;
        TextView tradePrice;

        public BiddingViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.bidding_user_id);
            name = itemView.findViewById(R.id.bidding_user_name);
            number = itemView.findViewById(R.id.bidding_number);
            color = itemView.findViewById(R.id.bidding_color);
            tradePrice = itemView.findViewById(R.id.bidding_trade_price);
        }
    }
}
