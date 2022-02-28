package com.example.mantrimalladmin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mantrimalladmin.API;
import com.example.mantrimalladmin.Model.UserModels.UserBiddingResult;
import com.example.mantrimalladmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BiddingResultFragment extends Fragment {


    public static BiddingResultFragment INSTANCE = null;
    private TextView lotteryId,number,color,loss,win,timestamp,tradePrice;
    private DatabaseReference myref;
    public static BiddingResultFragment getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new BiddingResultFragment();
        return INSTANCE;
    }

    public BiddingResultFragment() {
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
        //binding = FragmentBiddingResultBinding.inflate(getLayoutInflater());
        return inflater.inflate(R.layout.fragment_bidding_result, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            lotteryId = view.findViewById(R.id.BiddinglotteryId);
            number = view.findViewById(R.id.BiddingNumber);
            color = view.findViewById(R.id.BiddingColor);
            loss = view.findViewById(R.id.BiddingLoss);
            win = view.findViewById(R.id.BiddingWin);
            timestamp = view.findViewById(R.id.BiddingTimeStamp);
            tradePrice = view.findViewById(R.id.BiddingTradePrice);

            myref = FirebaseDatabase.getInstance().getReference(API.userNode);
            myref.child(API.getUserId()).child(API.userBiddingResultsNode).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserBiddingResult model  = snapshot.getValue(UserBiddingResult.class);
                    lotteryId.setText(model.getLotteryId());
                    number.setText(model.getNumber());
                    color.setText(model.getColor());
                    loss.setText(model.getLoss());
                    win.setText(model.getWin());
                    timestamp.setText(model.getTimestamp());
                    tradePrice.setText(model.getTradePrice());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("error",error.getMessage());
                }
            });

    }

}