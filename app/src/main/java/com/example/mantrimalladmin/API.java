package com.example.mantrimalladmin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

public class API {
    public static String userNode = "User";
    public static String withdrawRequest = "WithdrawRequests";
    public static String lotteryResultNode ="LotteryResults";
    public static String userTransactionNode ="transactionsList";
    public static String userComplaintsNode ="usersComplaints";
    public static String userBiddingResultsNode ="biddingResults";
    public static String userWithdrawsNode ="userWithdraws";
    public static String complaintsNode = "Complaints";
    public static String withdrawRequestNode = "WithdrawRequests";
    public static String userId;
    public static String winNumber;
    public static List<String> colorResults;

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {

                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public API() {
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        API.userId = userId;
    }
}
