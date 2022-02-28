package com.example.mantrimalladmin.Adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mantrimalladmin.Fragments.BiddingResultFragment;
import com.example.mantrimalladmin.Fragments.TransactionListFragment;
import com.example.mantrimalladmin.Fragments.UserComplaintsFragment;
import com.example.mantrimalladmin.Fragments.UserWithdrawFragment;

public class UserFragmentAdapter extends FragmentPagerAdapter {

    Context context;
    public UserFragmentAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return BiddingResultFragment.getINSTANCE();
        else if(position == 1)
            return UserWithdrawFragment.getINSTANCE();
        else if(position == 2)
            return TransactionListFragment.getINSTANCE();
        else if (position == 3)
            return UserComplaintsFragment.getINSTANCE();
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Withdraws";
            case 1:
                return "Bidding Results";
            case 2:
                return "Transactions";
            case 3:
                return "Complaints";
        }
        return "";
    }
}
