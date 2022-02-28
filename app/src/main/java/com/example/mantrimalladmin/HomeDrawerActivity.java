package com.example.mantrimalladmin;

import static java.lang.Double.parseDouble;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mantrimalladmin.Adapter.BiddingAdapter;
import com.example.mantrimalladmin.InnerActivities.ComplaintsActivity;
import com.example.mantrimalladmin.InnerActivities.LotteryActivity;
import com.example.mantrimalladmin.InnerActivities.LotteryResultActivity;
import com.example.mantrimalladmin.InnerActivities.RequestsActivity;
import com.example.mantrimalladmin.InnerActivities.UserActivity;
import com.example.mantrimalladmin.Model.ActiveLottery;
import com.example.mantrimalladmin.Model.BiddingUser;
import com.example.mantrimalladmin.Model.LotteryResults;
import com.example.mantrimalladmin.Model.LotteryWinners;
import com.example.mantrimalladmin.Model.UserModels.UserBiddingResult;
import com.example.mantrimalladmin.databinding.ActivityHomeDrawerBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final String KEY_HIGH = "HIGH";
    final String KEY_LOW = "LOW";
    final String KEY_MIDDLE = "MIDDLE";
    DrawerLayout drawer;
    NavigationView navigationView;
    ArrayList <Long> multiples = new ArrayList <>();
    TextView countdown;
    int temp;
    String img1, img2;
    AppCompatButton btnRedSelect, btnVioletSelect, btnGreenSelect;
    boolean canTrigger = true;
    boolean canTriggerActive = true;
    boolean canUpdateData = true;
    boolean canUpdateUserResult = true;
    RecyclerView biddingRecycler;
    List <BiddingUser> list;
    List <String> color;
    BiddingAdapter bidingadapter;
    ImageView first_image, second_img, third_img;
    TextView mostTradeNumber;
    String TAG = "le_test";
    boolean canCountdown = true;
    Map <String, String> updateColor = new HashMap <>();
    int currentId = 0;
    int lastCurrent = 0;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeDrawerBinding binding;
    private Button btnChangeData;
    //Declare Views of Active Lottery here
    private TextView currentIdTV, lastIdTV, priceTV, winNumberTV;
    private ImageView imageOne, imageTwo;
    private DatabaseReference myRef;
    private MaterialEditText edtWinNumber;
    private ImageView imgOneSelect, imgTwoSelect;
    private BiddingUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHomeDrawer.toolbar);
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        list = new ArrayList <>();
        color = new ArrayList <>();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, binding.appBarHomeDrawer.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Init Views Here
        initViews();

        //Settup CountDown here
        countDown();
        //Setup Database Here
        LoadActiveLottery();
        //loading Bidding User

        //Change Active Lottery by Admin
        UpdateLottery();
        //start service
/*        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(new Intent(this,ServerService.class));
        }else
              startService(new Intent(this,ServerService.class));*/
        //  countdownToLoadData();


    }

    private void countdownToLoadData() {
        if (!canCountdown) {
            return;
        }
        new CountDownTimer(10000, 1000) {
            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: ");
                loadBiddingUser();
                countdownToLoadData();
            }

            @Override
            public void onTick(long l) {

            }
        }.start();

    }

    public void publishResult() {
        API.winNumber = activeLottery.getWinNumber();
        Log.d("update_", "publishResult: " + API.winNumber);
        canTrigger = false;
        biddingRecycler.setHasFixedSize(true);
        biddingRecycler.setLayoutManager(new LinearLayoutManager(this));
        myRef.child("ActiveLottery").child("biddingUsers").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("update_", "onDataChange: startroot");
                double greenColorPrice = 0.0, redColorPrice = 0.0, voiletColorPrice = 0.0;
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    try {
                        user = snapshot1.getValue(BiddingUser.class);
                        list.add(user);
                        color.add(user.getColor());
                        if (user.getColor().equals(IEnums.RED.toString().toLowerCase())) {
                            redColorPrice += parseDouble(user.getTradePrice());
                           // if (API.colorResults != null) {
                                if (user.getNumber().equals(API.winNumber)) {
                                    // Log.d("123","here is trade price"+ user.getNumber());
                                    if (activeLottery.getResultColors().get(0).equalsIgnoreCase("red")) {
                                        updateBalanceForRed(user.getUserId(), API.winNumber, user.getTradePrice(), user.getName(),user.getNumber());
                                    } else if (activeLottery.getResultColors().get(1).equalsIgnoreCase("red")) {
                                        updateBalanceForRed(user.getUserId(), API.winNumber, user.getTradePrice(), user.getName(),user.getNumber());
                                    }
                                }

                           // }
                        }else
                        if (user.getColor().equals(IEnums.GREEN.toString().toLowerCase())) {
                            greenColorPrice += parseDouble(user.getTradePrice());


                                if (user.getNumber().equals(API.winNumber)) {
                                    Log.d("123", "here is trade price" + user.getNumber());
                                    if (activeLottery.getResultColors().get(0).equalsIgnoreCase("green")) {
                                        updateBalanceForGreen(user.getUserId(), API.winNumber, user.getTradePrice(), user.getName(),user.getNumber());
                                    } else if (activeLottery.getResultColors().get(1).equalsIgnoreCase("green")) {
                                        updateBalanceForGreen(user.getUserId(), API.winNumber, user.getTradePrice(), user.getName(),user.getNumber());
                                    }
                                }


                        }else
                        if (user.getColor().equals(IEnums.VOILET.toString().toLowerCase())) {
                            voiletColorPrice += parseDouble(user.getTradePrice());
                            //if (API.colorResults != null) {
                                if (user.getNumber().equals(API.winNumber)) {
                                    // Log.d("123","here is trade price"+ user.getNumber());
                                    if (activeLottery.getResultColors().get(0).equalsIgnoreCase("voilet")) {
                                        updateBalanceForVoilet(user.getUserId(), API.winNumber, user.getTradePrice(), user.getName(),user.getNumber(),user.getNumber());
                                    } else if (activeLottery.getResultColors().get(1).equalsIgnoreCase("voilet")) {
                                        updateBalanceForVoilet(user.getUserId(), API.winNumber, user.getTradePrice(), user.getName(),user.getNumber(),user.getNumber());
                                    }
                                }
                         //   }
                        }else{
                            /*if (user.getNumber().equals(API.winNumber)) {
                                // Log.d("123","here is trade price"+ user.getNumber());
                                if (activeLottery.getResultColors().get(0).equalsIgnoreCase("voilet")) {
                                    updateBalanceFor(user.getUserId(), API.winNumber, user.getTradePrice(), user.getName(),user.getNumber(),user.getNumber());
                                } else if (activeLottery.getResultColors().get(1).equalsIgnoreCase("voilet")) {
                                    updateBalanceForVoilet(user.getUserId(), API.winNumber, user.getTradePrice(), user.getName(),user.getNumber(),user.getNumber());
                                }
                            }*/
                        }
                        // Toast.makeText(getApplicationContext(), ""+ user.getName(), Toast.LENGTH_SHORT).show();
                        bidingadapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    //Thread.sleep(5000);
                    updatePublishResult();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setResulColors(redColorPrice, greenColorPrice, voiletColorPrice);
                //  Toast.makeText(HomeDrawerActivity.this, ""+greenColorPrice, Toast.LENGTH_SHORT).show();
                String highest, lowest, middle;
                int green_count = Collections.frequency(color, IEnums.GREEN.toString().toLowerCase());
                Log.d(TAG, "onDataChange:green " + green_count);

                int voilet_count = Collections.frequency(color, IEnums.VOILET.toString().toLowerCase());
                Log.d(TAG, "onDataChange:voilet " + voilet_count);
                int red_count = Collections.frequency(color, IEnums.RED.toString().toLowerCase());
                Log.d(TAG, "onDataChange:red " + red_count);
                Map <String, Integer> colors = new HashMap <>();
                colors.put(IEnums.GREEN.toString(), green_count);
                colors.put(IEnums.RED.toString(), red_count);
                colors.put(IEnums.VOILET.toString(), voilet_count);
                //sort now
                //loopColors(colors);
                // publish the result
                Log.d("update_", "onDataChange: before");
                updateActiveLottery();
                canTrigger = false;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        bidingadapter = new BiddingAdapter(list);
        biddingRecycler.setAdapter(bidingadapter);
        bidingadapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadBiddingUser() {
        Toast.makeText(this, "LoadBidding", Toast.LENGTH_SHORT).show();
        try {
            list.clear();
            biddingRecycler.setLayoutManager(new LinearLayoutManager(this));
            myRef.child("ActiveLottery").child("biddingUsers").addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    double greenColorPrice = 0.0, redColorPrice = 0.0, voiletColorPrice = 0.0;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        try {
                            user = snapshot1.getValue(BiddingUser.class);
                            list.add(user);
                            color.add(user.getColor());
                            if (user.getColor().equals(IEnums.RED.toString().toLowerCase())) {
                                redColorPrice += parseDouble(user.getTradePrice());
                     /*   if (API.colorResults != null) {
                            if (user.getNumber().equals(API.winNumber)) {
                                // Log.d("123","here is trade price"+ user.getNumber());
                                if (API.colorResults.get(0).equalsIgnoreCase("red")) {
                                    updateBalanceForRed(user.getUserId(), API.winNumber, user.getTradePrice(), user.getName());
                                } else if (API.colorResults.get(1).equalsIgnoreCase("red")) {
                                    updateBalanceForRed(user.getUserId(), API.winNumber, user.getTradePrice(), user.getName());
                                }
                            }

                        }*/
                            }
                            if (user.getColor().equals(IEnums.GREEN.toString().toLowerCase())) {
                                greenColorPrice += parseDouble(user.getTradePrice());

                      /*  if (API.colorResults != null){
                            if (user.getNumber().equals(API.winNumber)){
                                Log.d("123","here is trade price"+ user.getNumber());
                                if (API.colorResults.get(0).equalsIgnoreCase("green")){
                                    updateBalanceForGreen(user.getUserId(),API.winNumber,user.getTradePrice(),user.getName());
                                }
                                else if (API.colorResults.get(1).equalsIgnoreCase("green")){
                                    updateBalanceForGreen(user.getUserId(),API.winNumber,user.getTradePrice(), user.getName());
                                }
                            }
                        }*/

                            }
                            if (user.getColor().equals(IEnums.VOILET.toString().toLowerCase())) {
                                voiletColorPrice += parseDouble(user.getTradePrice());
                       /* if (API.colorResults != null){
                            if (user.getNumber().equals(API.winNumber)){
                               // Log.d("123","here is trade price"+ user.getNumber());
                                if (API.colorResults.get(0).equalsIgnoreCase("voilet")){
                                    updateBalanceForVoilet(user.getUserId(),API.winNumber,user.getTradePrice(),user.getName());
                                }
                                else if (API.colorResults.get(1).equalsIgnoreCase("voilet")){
                                    updateBalanceForVoilet(user.getUserId(),API.winNumber,user.getTradePrice(), user.getName());
                                }
                            }
                        }*/
                            }
                            // Toast.makeText(getApplicationContext(), ""+ user.getName(), Toast.LENGTH_SHORT).show();
                            bidingadapter.notifyDataSetChanged();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("le_test", "onDataChange: " + redColorPrice + greenColorPrice + voiletColorPrice);
                    setResulColors(redColorPrice, greenColorPrice, voiletColorPrice);
                    //  Toast.makeText(HomeDrawerActivity.this, ""+greenColorPrice, Toast.LENGTH_SHORT).show();
                    String highest, lowest, middle;
                    int green_count = Collections.frequency(color, IEnums.GREEN.toString().toLowerCase());
                    Log.d(TAG, "onDataChange:green " + green_count);

                    int voilet_count = Collections.frequency(color, IEnums.VOILET.toString().toLowerCase());
                    Log.d(TAG, "onDataChange:voilet " + voilet_count);
                    int red_count = Collections.frequency(color, IEnums.RED.toString().toLowerCase());
                    Log.d(TAG, "onDataChange:red " + red_count);
                    Map <String, Integer> colors = new HashMap <>();
                    colors.put(IEnums.GREEN.toString(), green_count);
                    colors.put(IEnums.RED.toString(), red_count);
                    colors.put(IEnums.VOILET.toString(), voilet_count);
                    //sort now
                    Log.d("debug_", "onDataChange: loopcolor");
                    loopColors(colors);
                    // publish the result


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    loadBiddingUser();
                    Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            bidingadapter = new BiddingAdapter(list);
            biddingRecycler.setAdapter(bidingadapter);
            bidingadapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    List<BiddingUser> winnerList= new ArrayList <>();
    List<BiddingUser> lostLiser = new ArrayList <>();
    private void updatePublishResult(){
        //String period, String price, String number, List <String> resultList, String id, List <BiddingUser> winnerList, List <BiddingUser> lostList
        LotteryResults results = new LotteryResults(activeLottery.getCurrentId(),activeLottery.getPrice(),activeLottery.getWinNumber(),activeLottery.getResultColors(),myRef.getKey(),winnerList,lostLiser);
        myRef.child(API.lotteryResultNode).push().setValue(results);
    }

    private void updateActiveLottery() {
        Log.d("update_", "updateActiveLottery: ");
        int cId = currentId + 1;
        List <String> colorsList = new ArrayList <>();
        colorsList.clear();
        colorsList.add("null");
        BiddingUser u = new BiddingUser("null", "null", "null", "null", "null");
        ActiveLottery activeLottery = new ActiveLottery(String.valueOf(cId), String.valueOf(currentId), "-1", colorsList, "-1");
        myRef.child("ActiveLottery").child("currentId").setValue(String.valueOf(cId));
        myRef.child("ActiveLottery").child("lastId").setValue(String.valueOf(currentId));
        myRef.child("ActiveLottery").child("price").setValue("-1");
        myRef.child("ActiveLottery").child("resultColors").setValue(colorsList);
        myRef.child("ActiveLottery").child("winNumber").setValue(String.valueOf(generateInt()));
        myRef.child("ActiveLottery").child("biddingUsers").removeValue();


        /*myRef.child("ActiveLottery").setValue(activeLottery).addOnCompleteListener(new OnCompleteListener <Void>() {
            @Override
            public void onComplete(@NonNull Task <Void> task) {
                Toast.makeText(HomeDrawerActivity.this, "active", Toast.LENGTH_SHORT).show();
                    //LoadActiveLottery();


            }
        });*/


    }

    private void updateBalanceForRed(String userId, String winNumber, String tradePrice, String name,String usernumber) {
        Log.d(TAG, "updateBalanceForRed: ");
        String tradePriceUpdated = "0";
        if (winNumber.equals("2") || winNumber.equals("4") || winNumber.equals("6") || winNumber.equals("8")) {
            double price = parseDouble(tradePrice) * 2;
            tradePriceUpdated = String.valueOf(price);

        } else if (winNumber.equals("0")) {
            double price = parseDouble(tradePrice) * 1.5;
            tradePriceUpdated = String.valueOf(price);
        }
        if (!tradePriceUpdated.equals("0")) {
            //String lotteryId, String number, String color, String loss, String win, String timestamp, String tradePrice
            myRef.child(API.userNode).child(userId).child("biddingResults").push()
                    .setValue(new UserBiddingResult(activeLottery.getCurrentId(), winNumber, Color.RED.name().toLowerCase(), "false", tradePriceUpdated, String.valueOf(System.currentTimeMillis()), tradePrice));
            winnerList.add(new BiddingUser(userId,name,winNumber,Color.RED.name().toLowerCase(),tradePrice));
        }else{
            myRef.child(API.userNode).child(userId).child("biddingResults").push()
                    .setValue(new UserBiddingResult(activeLottery.getCurrentId(), usernumber, Color.RED.name().toLowerCase(), tradePrice, "false", String.valueOf(System.currentTimeMillis()), tradePrice));
            lostLiser.add(new BiddingUser(userId,name,winNumber,Color.RED.name().toLowerCase(),tradePrice));
        }
    }

    private void updateBalanceForVoilet(String userId, String winNumber, String tradePrice, String name,String tradNumber,String userNumb) {
        Log.d(TAG, "updateBalanceForVoilet: ");
        String tradePriceUpdated = "0";
        if (winNumber.equals("0") || winNumber.equals("5")) {
            double price = parseDouble(tradePrice) * 4.5;
            tradePriceUpdated = String.valueOf(price);
        }
        if (!tradePriceUpdated.equals("0")) {
//            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("LotteryWinners");
//            LotteryWinners model = new LotteryWinners(name, tradePriceUpdated, userId);
//            reference.push().setValue(model);
            /*tring userId;
            String name;
            String number;
            String color;
            String tradePrice;*/
            myRef.child(API.userNode).child(userId).child("biddingResults").push()
                    .setValue(new UserBiddingResult(activeLottery.getCurrentId(), winNumber, "voilet", "false", tradePriceUpdated, String.valueOf(System.currentTimeMillis()), tradePrice));
            winnerList.add(new BiddingUser(userId,name,winNumber,Color.VOILET.name().toLowerCase(),tradePrice));


        }else{
            myRef.child(API.userNode).child(userId).child("biddingResults").push()
                    .setValue(new UserBiddingResult(activeLottery.getCurrentId(), userNumb, Color.VOILET.name().toLowerCase(), tradePrice, "false", String.valueOf(System.currentTimeMillis()), tradePrice));
            lostLiser.add(new BiddingUser(userId,name,tradNumber,Color.VOILET.name().toLowerCase(),tradePrice));
        }
    }

    private void updateBalanceForGreen(String userId, String winNumber, String tradePrice, String name,String usernumber) {
        Log.d(TAG, "updateBalanceForGreen: ");
        String tradePriceUpdated = "0";
        if (winNumber.equals("1") || winNumber.equals("3") || winNumber.equals("7") || winNumber.equals("9")) {
            double price = parseDouble(tradePrice) * 2;
            tradePriceUpdated = String.valueOf(price);

        } else if (winNumber.equals("5")) {
            double price = parseDouble(tradePrice) * 1.5;
            tradePriceUpdated = String.valueOf(price);
        }
        if (!tradePriceUpdated.equals("0")) {
            myRef.child(API.userNode).child(userId).child("biddingResults").push()
                    .setValue(new UserBiddingResult(activeLottery.getCurrentId(), winNumber, Color.GREEN.name().toLowerCase(), "false", tradePriceUpdated, String.valueOf(System.currentTimeMillis()), tradePrice));
            winnerList.add(new BiddingUser(userId,name,usernumber,Color.GREEN.name().toLowerCase(),tradePrice));


        }else{
            myRef.child(API.userNode).child(userId).child("biddingResults").push()
                    .setValue(new UserBiddingResult(activeLottery.getCurrentId(), winNumber, Color.GREEN.name().toLowerCase(), tradePrice, "false", String.valueOf(System.currentTimeMillis()), tradePrice));

            lostLiser.add(new BiddingUser(userId,name,usernumber,Color.GREEN.name().toLowerCase(),tradePrice));
        }
    }

    private void setResulColors(double redColorPrice, double greenColorPrice, double voiletColorPrice) {
        double maxPrice = redColorPrice;
        String name = IEnums.RED.toString();
        if (maxPrice < greenColorPrice) {
            maxPrice = greenColorPrice;
            name = IEnums.GREEN.toString();
        }
        if (maxPrice < voiletColorPrice) {
            maxPrice = voiletColorPrice;
            name = IEnums.VOILET.toString();
        }
        if (name.equals(IEnums.RED.toString())) {
            setColorsToDatabase(IEnums.GREEN.toString().toLowerCase(), IEnums.VOILET.toString().toLowerCase());
        } else if (name.equals(IEnums.GREEN.toString())) {
            setColorsToDatabase(IEnums.RED.toString().toLowerCase(), IEnums.VOILET.toString().toLowerCase());
        } else if (name.equals(IEnums.VOILET.toString())) {
            setColorsToDatabase(IEnums.RED.toString().toLowerCase(), IEnums.GREEN.toString().toLowerCase());
        }
    }

    private void setColorsToDatabase(String colorOne, String colorTwo) {
        Log.d(TAG, "setColorsToDatabase: " + colorOne + colorTwo);
        //Toast.makeText(HomeDrawerActivity.this, "colorONe=="+ colorOne+"ColorTwo=="+colorTwo, Toast.LENGTH_SHORT).show();
        Map <String, Object> taskMap = new HashMap <>();
        taskMap.put("0", colorOne);
        taskMap.put("1", colorTwo);
        ;
        myRef.child("ActiveLottery").child("resultColors").setValue(taskMap);
    }

    private void setImage(String val, ImageView v) {
        if (val.equals(IEnums.RED.toString()))
            v.setImageResource(R.drawable.red_circle);
        else if (val.equals(IEnums.GREEN.toString()))
            v.setImageResource(R.drawable.green_circle);
        else if (val.equals(IEnums.VOILET.toString()))
            v.setImageResource(R.drawable.voilet_circle);

    }

    private void loopColors(Map <String, Integer> colors) {
        Map.Entry <String, Integer> maxEntry = null;

        for (Map.Entry <String, Integer> entry : colors.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
                Log.d(TAG, "Hashmap: " + entry.getKey() + "  :  " + entry.getValue());
            }
        }
        Log.d(TAG, "Hashmap:max " + maxEntry.getKey() + "  :  " + maxEntry.getValue());
        if (colors.size() == 3) {
            if (maxEntry.getValue() != 0)
                setImage(maxEntry.getKey(), first_image);
            updateColor.put(KEY_HIGH, maxEntry.getKey());
        } else if (colors.size() == 2) {
            updateColor.put(KEY_MIDDLE, maxEntry.getKey());
            if (maxEntry.getValue() != 0)
                setImage(maxEntry.getKey(), second_img);
        } else if (colors.size() == 1) {
            updateColor.put(KEY_LOW, maxEntry.getKey());
            if (maxEntry.getValue() != 0)
                setImage(maxEntry.getKey(), third_img);
        }
        colors.remove(maxEntry.getKey());
        if (colors.size() >= 1)
            loopColors(colors);
        else
            Log.d(TAG, "loopColors: ends");


    }

    private void UpdateLottery() {
        btnChangeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDataINLottery();
            }
        });
    }

    private void UpdateDataINLottery() {

        img1 = "";
        img2 = "";
        temp = 0;
        AlertDialog.Builder alert = new AlertDialog.Builder(HomeDrawerActivity.this);
        alert.setTitle("Update Information:");


        LayoutInflater inflater = this.getLayoutInflater();
        View change_lottery = inflater.inflate(R.layout.change_active_lottery, null);

        edtWinNumber = change_lottery.findViewById(R.id.edtWinNumberUpdate);
        btnGreenSelect = change_lottery.findViewById(R.id.btn_join_green);
        btnRedSelect = change_lottery.findViewById(R.id.btn_join_red);
        btnVioletSelect = change_lottery.findViewById(R.id.btn_join_violet);
        imgOneSelect = change_lottery.findViewById(R.id.img_one_select);
        imgTwoSelect = change_lottery.findViewById(R.id.img_two_select);

        btnRedSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp == 0) {
                    imgOneSelect.setImageResource(R.drawable.red_circle);
                    img1 = "RED";
                    temp = 1;
                } else if (temp == 1) {
                    imgTwoSelect.setImageResource(R.drawable.red_circle);
                    img2 = "RED";
                    temp = 2;
                }

            }
        });
        btnVioletSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp == 0) {
                    imgOneSelect.setImageResource(R.drawable.voilet_circle);
                    img1 = "VOILET";
                    temp = 1;
                } else if (temp == 1) {
                    imgTwoSelect.setImageResource(R.drawable.voilet_circle);
                    img2 = "VOILET";
                    temp = 2;
                }
            }
        });
        btnGreenSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp == 0) {
                    imgOneSelect.setImageResource(R.drawable.green_circle);
                    img1 = "GREEN";
                    temp = 1;
                } else if (temp == 1) {
                    imgTwoSelect.setImageResource(R.drawable.green_circle);
                    img2 = "GREEN";
                    temp = 2;
                }
            }
        });

        alert.setView(change_lottery);
        alert.setIcon(R.drawable.cart_icon);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                // Here we create category
                if (!edtWinNumber.getText().toString().isEmpty() && !img1.isEmpty() && !img2.isEmpty()) {

               /*     Toast.makeText(ActiveLotteryActivity.this, "Data is Updated"+
                            edtWinNumber.getText().toString()+ " " +
                            img1 + " " + img2, Toast.LENGTH_SHORT).show();*/
                    List <String> list = new ArrayList <>();
                    list.add(img1);
                    list.add(img2);
                    String win_number = edtWinNumber.getText().toString();
                   /* myRef.child("winNumber").setValue(win_number);
                    myRef.child("resultColors").setValue(list);*/
                    Map map = new HashMap();
                    map.put("winNumber", win_number);
                    map.put("resultColors", list);
                    myRef.updateChildren(map);
                    //Snackbar.make(drawer,"New Category "+newCategory.getName()+" was added", Snackbar.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getApplicationContext(), "please Fill all Fields", Toast.LENGTH_SHORT).show();
                }


            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();

    }
ActiveLottery activeLottery ;
    private void LoadActiveLottery() {
        if (API.isConnectedToInternet(this)) {
            myRef.child("ActiveLottery").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("deee", "onDataChange: " + snapshot.toString());
                    ActiveLottery lists = snapshot.getValue(ActiveLottery.class);
                    activeLottery = lists;
                    currentIdTV.setText(lists.getCurrentId());
                    currentId = Integer.parseInt(lists.getCurrentId());
                    lastCurrent = Integer.parseInt(lists.getLastId());
                    lastIdTV.setText(lists.getLastId());
                    priceTV.setText(lists.getPrice());
                    winNumberTV.setText(lists.getWinNumber());
                    API.winNumber = lists.getWinNumber();
                    API.colorResults = lists.getResultColors();
                    if (lists.getResultColors().size() == 2) {
                        if (lists.getResultColors().get(0).toLowerCase().contains("red")) {
                            imageOne.setImageResource(R.drawable.red_circle);
                        } else if (lists.getResultColors().get(0).toLowerCase().contains("green")) {
                            imageOne.setImageResource(R.drawable.green_circle);
                        } else if (lists.getResultColors().get(0).toLowerCase().contains("voilet")) {
                            imageOne.setImageResource(R.drawable.voilet_circle);
                        }
                        if (lists.getResultColors().get(1).toLowerCase().contains("red")) {
                            imageTwo.setImageResource(R.drawable.red_circle);
                        } else if (lists.getResultColors().get(1).toLowerCase().contains("green")) {
                            imageTwo.setImageResource(R.drawable.green_circle);
                        } else if (lists.getResultColors().get(1).toLowerCase().contains("voilet")) {
                            imageTwo.setImageResource(R.drawable.voilet_circle);
                        }
                    } else {
                        if (lists.getResultColors().get(0).toLowerCase().contains("red")) {
                            imageOne.setImageResource(R.drawable.red_circle);
                        } else if (lists.getResultColors().get(0).toLowerCase().contains("green")) {
                            imageOne.setImageResource(R.drawable.green_circle);
                        } else if (lists.getResultColors().get(0).toLowerCase().contains("voilet")) {
                            imageOne.setImageResource(R.drawable.voilet_circle);
                        }
                    }
                    loadBiddingUser();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            Snackbar snackbar = Snackbar.make(drawer, " No Internet! Please Check your Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void initViews() {
        countdown = findViewById(R.id.count_down);
        myRef = FirebaseDatabase.getInstance().getReference();
        imageOne = findViewById(R.id.img_one);
        imageTwo = findViewById(R.id.img_two);
        currentIdTV = findViewById(R.id.currentIdTV);
        lastIdTV = findViewById(R.id.lastIdTV);
        priceTV = findViewById(R.id.priceTV);
        winNumberTV = findViewById(R.id.winNumberTV);
        btnChangeData = findViewById(R.id.btnChangeData);
        biddingRecycler = findViewById(R.id.BiddingRecycler);
        first_image = findViewById(R.id.first_img_);
        second_img = findViewById(R.id.second_img);
        third_img = findViewById(R.id.third_img);
        mostTradeNumber = findViewById(R.id.most_trade_color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_drawer, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_Lottery:
                startActivity(new Intent(this, LotteryActivity.class));
                break;
            case R.id.nav_LotteryResult:
                startActivity(new Intent(this, LotteryResultActivity.class));
                break;
            case R.id.nav_Users:
                startActivity(new Intent(this, UserActivity.class));
                break;
            case R.id.nav_complaints:
                startActivity(new Intent(this, ComplaintsActivity.class));
                break;
            case R.id.nav_withdraw_requests:
                startActivity(new Intent(this, RequestsActivity.class));
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(HomeDrawerActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            sweetAlertDialog.setTitleText("Are you Sure?");
            sweetAlertDialog.setContentText("want to exit this App!");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCustomImage(R.drawable.exit_icon);
            sweetAlertDialog.setConfirmButton("Exit", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    finish();
                }
            });
            sweetAlertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                }
            });
            sweetAlertDialog.show();
        }
    }

    private void countDown() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        long current_mints = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());

                        long greaterMinute = search(current_mints, multiples);

                        if (search(current_mints, multiples) < current_mints) {
                            current_mints++;
                            if (search(current_mints, multiples) == current_mints) {
                                greaterMinute = search(current_mints + 3, multiples);
                            }
                        }
                        if (search(current_mints, multiples) == current_mints) {
                            greaterMinute = search(current_mints + 3, multiples);
                        }

                        String rndm = (greaterMinute + 7) + "";
                        String one = String.valueOf(rndm.charAt(rndm.length() - 1));
                        int oneCH = Integer.parseInt(one);

                        String finalNmbr = "2";

                        if (oneCH > 8) {
                            finalNmbr = "3";
                        } else if (oneCH > 0 && oneCH < 4) {
                            finalNmbr = "4";
                        } else if (oneCH > 4 && oneCH < 7) {
                            finalNmbr = "1";
                        } else {
                            finalNmbr = "2";
                        }

                        // randomNmbrTv.setText(finalNmbr);


                        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

                        long difference = TimeUnit.MINUTES.toMillis(greaterMinute) - System.currentTimeMillis();

                        String formatted = sdf.format(new Date(difference));

                        if (formatted.startsWith("00") || formatted.startsWith("02")) {

                        } else {
                            formatted = "01" + formatted.substring(2);
                        }
                        Log.d("de_", "run: " + difference);
                        Log.d("de_", "run:formated " + formatted);

                        countdown.setText(formatted);
                        canCountdown = true;

                        if (!String.valueOf(difference).contains("-")) {
                            if (difference < 31000) {
                                enableViews(false);
                                setAlphaOnViews((float) 0.4);
                                Log.d("de_", "run: entry closed");
                                if (canTrigger) {
                                    //trigger();
                                    publishResult();
                                }

                            } else {
                                canTrigger = true;
                                canCountdown = false;
                                Log.d("de_", "entry open");
                                enableViews(true);
                                if (canTrigger) {
                                    // getLotteryResultData();

                                }

                                if (canUpdateUserResult) {
                                    // getUserLotteryResultData();
                                }
                                setAlphaOnViews((float) 1);
                                if (canTriggerActive) {
                                    // lastLotteryNumber = currentLotteryNumber;
                                    //  currentLotteryNumber += 1;
                                    // triggerActiveLottery();
                                }
                            }
                        }
                    }
                });
            }
        };

//30000000 is minuts of next 5 month this time work until 2027 then update the time to work more.
        new Thread(new Runnable() {
            @Override
            public void run() {
                multiples = new ArrayList <>();
                for (long i = 27344151; i < 30000000; i = i + 3) {
                    multiples.add(i);
                }


                new Timer().schedule(timerTask, 1000, 1000);

            }
        }).start();

    }

    private void setAlphaOnViews(float alpha) {
        countdown.setAlpha(alpha);
     /*   currentEntry.setAlpha(alpha);
        joinGreen.setAlpha(alpha);
        joinRed.setAlpha(alpha);
        joinViolet.setAlpha(alpha);
        btnOne.setAlpha(alpha);
        btntwo.setAlpha(alpha);
        btnThree.setAlpha(alpha);
        btnFour.setAlpha(alpha);
        btnFive.setAlpha(alpha);
        btnSix.setAlpha(alpha);
        btnSeven.setAlpha(alpha);
        btnEight.setAlpha(alpha);
        btnNine.setAlpha(alpha);
        btnTen.setAlpha(alpha);*/


    }

    private void enableViews(boolean enable) {

/*        joinGreen.setEnabled(enable);
        joinRed.setEnabled(enable);
        joinViolet.setEnabled(enable);
        btnOne.setEnabled(enable);
        btntwo.setEnabled(enable);
        btnThree.setEnabled(enable);
        btnFour.setEnabled(enable);
        btnFive.setEnabled(enable);
        btnSix.setEnabled(enable);
        btnSeven.setEnabled(enable);
        btnEight.setEnabled(enable);
        btnNine.setEnabled(enable);
        btnTen.setEnabled(enable);*/


    }

    private long search(long value, ArrayList <Long> aa) {

        if (value < aa.get(0)) {
            return aa.get(0);
        }
        if (value > aa.get(aa.size() - 1)) {
            return aa.get(aa.size() - 1);
        }

        int lo = 0;
        int hi = aa.size() - 1;

        while (lo <= hi) {
            int mid = (hi + lo) / 2;

            if (value < aa.get(mid)) {
                hi = mid - 1;

            } else if (value > aa.get(mid)) {
                lo = mid + 1;
            } else {
                return aa.get(mid);
            }
        }
        return (aa.get(lo) - value) < (value - aa.get(hi)) ? aa.get(lo) : aa.get(hi);
    }

    private int generateInt() {
        return (int) (Math.random() * (9 - 0 + 1) + 0);
    }

    private String generateColor() {
        int b = (int) (Math.random() * (3 - 1 + 1) + 1);
        if (b == 1) {
            return Color.GREEN.toString();
        } else if (b == 2)
            return Color.RED.toString();
        else
            return Color.VOILET.toString();
    }


}