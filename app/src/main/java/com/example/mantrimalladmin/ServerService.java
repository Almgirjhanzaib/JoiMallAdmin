package com.example.mantrimalladmin;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mantrimalladmin.Model.ActiveLottery;
import com.example.mantrimalladmin.Model.BiddingUser;
import com.example.mantrimalladmin.Model.LotteryResults;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ServerService extends Service {

    public ServerService() {
    }
    FirebaseDatabase mUserDb;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseUser currentUser;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        super.onCreate();
       FirebaseApp.initializeApp(this);
        mUserDb = FirebaseDatabase.getInstance();
        mRef = mUserDb.getReference();
        mAuth = FirebaseAuth.getInstance();
        countDown();
        Toast.makeText(this, "start ServerService", Toast.LENGTH_SHORT).show();

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
    ArrayList <Long> multiples = new ArrayList <>();
    Handler handler = new Handler();
    boolean canTriggerActive = true;
    boolean canUpdateData = true;
    boolean canTrigger = true;
    private void countDown() {
        TimerTask timerTask = new TimerTask() {
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


                if (!String.valueOf(difference).contains("-")) {
                    if (difference < 31000) {

                        Log.d("de_", "run: entry closed");
                        if (canTrigger)
                            trigger();


                    } else {
                        canTrigger = true;
                        getUpdateLotteryIds();
                        Log.d("de_", "entry open");


                        if (canUpdateData) {
                            getLotteryResultData();
                        }



                        if (canTriggerActive) {
                            lastLotteryNumber = currentLotteryNumber;
                            triggerActiveLottery();
                        }
                    }
                }

            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                multiples = new ArrayList <>();
                for (long i = 27344151; i < 27394151; i = i + 3) {
                    multiples.add(i);
                }


                new Timer().schedule(timerTask, 1000, 1000);

            }
        }).start();
    }

    private void getLotteryResultData() {
        List <LotteryResults> list = new ArrayList <>();
        mRef.child(API.lotteryResultNode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    LotteryResults results = snap.getValue(LotteryResults.class);
                    list.add(results);
                }

                canUpdateData = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

        private void triggerActiveLottery () {
            List <String> colorList = new ArrayList <>();
            colorList.add(generateColor());
            colorList.add(generateColor());
            winNumber = generateInt();
            price = getRandomprice();
            int current = Integer.parseInt(lastLotteryNumber) + 1;
            currentLotteryNumber = String.valueOf(current);
            ActiveLottery activeLottery = new ActiveLottery(currentLotteryNumber, lastLotteryNumber, String.valueOf(generateInt()), colorList, String.valueOf(price));
            mRef.child("ActiveLottery")
                    .setValue(activeLottery).addOnCompleteListener(new OnCompleteListener <Void>() {
                @Override
                public void onComplete(@NonNull Task <Void> task) {

                    getUpdateLotteryIds();
                    Log.d("de_", "onComplete: activeTrigger");
                    canTriggerActive = false;

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    triggerActiveLottery();
                    getUpdateLotteryIds();
                    canTriggerActive = true;
                }
            });
        }



        private int getRandomprice() {
            return (int) (Math.random() * (35665 - 500 + 1) + 500);
        }
    boolean canUpdateUserResult = true;
    String currentLotteryNumber = "000000000920";
    String lastLotteryNumber = "0000000919";
    List <BiddingUser> biddingUsers = new ArrayList <>();
    int winNumber = -1;
    int price = 0;
        private void trigger() {
            canUpdateUserResult = true;
            canUpdateData = true;
            Log.d("de_", "trigger: ");
            List <LotteryResults> lotteryResultsList = new ArrayList <>();
            List <BiddingUser> winner = new ArrayList <>();
            List <BiddingUser> losser = new ArrayList <>();

            List <String> colorList = new ArrayList <>();
            colorList.add(generateColor());
            colorList.add(generateColor());
            lotteryResultsList.add(new LotteryResults(String.valueOf(System.currentTimeMillis()), String.valueOf(getRandomprice()), String.valueOf(generateInt()), colorList));
            LotteryResults lotteryResults = new LotteryResults(currentLotteryNumber, String.valueOf(price), String.valueOf(winNumber), colorList, mRef.getKey(), winner, losser);
            mRef.child("LotteryResults").push().setValue(lotteryResults, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Log.d("de_com", "onComplete: ");
                    canTrigger = false;
                }
            });
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

        private int generateInt() {
            return (int) (Math.random() * (9 - 0 + 1) + 0);
        }




    private void getUpdateLotteryIds() {
        mRef.child("ActiveLottery").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ActiveLottery activeLottery = snapshot.getValue(ActiveLottery.class);
                assert activeLottery != null;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

               getUpdateLotteryIds();
            }
        });
    }
}