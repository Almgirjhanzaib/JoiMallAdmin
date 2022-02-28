package com.example.mantrimalladmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mantrimalladmin.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;

public class PaymentActivity extends AppCompatActivity implements PaymentStatusListener {
    TextView soo,teenSoo,fiveHund,thousand,twoThousand,fiveThousand,tenThousand,fiftyThousand;
    EditText userTxt,edtTextEmail,editTextPhone,enterPayAmmount;
    Button payBtn;
    FirebaseDatabase mUserDb;
    DatabaseReference mRef;
    ProgressDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Processing...");
        dialog.setCanceledOnTouchOutside(false);
        soo = findViewById(R.id.txt_soo);
        teenSoo = findViewById(R.id.txt_teen_so);
        fiveHund = findViewById(R.id.txt_five_so);
        thousand = findViewById(R.id.txt_hazar);
        twoThousand = findViewById(R.id.txt_do_hazar);
        fiveThousand = findViewById(R.id.txt_five_hazar);
        tenThousand = findViewById(R.id.txt_dus_hazar);
        fiftyThousand = findViewById(R.id.txt_fifty_hazar);
        userTxt = findViewById(R.id.reg_name);
        edtTextEmail = findViewById(R.id.reg_email);
        editTextPhone = findViewById(R.id.reg_phon);
        payBtn = findViewById(R.id.btn_make_payment);
        enterPayAmmount = findViewById(R.id.edit_get_ammount);




        soo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterPayAmmount.setText("100");
            }
        }); teenSoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterPayAmmount.setText("300");
            }
        }); fiveHund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterPayAmmount.setText("500");
            }
        }); thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterPayAmmount.setText("1000");
            }
        }); twoThousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterPayAmmount.setText("2000");
            }
        });
        fiveThousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterPayAmmount.setText("5000");
            }
        });
        tenThousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterPayAmmount.setText("10000");
            }
        });
        fiftyThousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterPayAmmount.setText("50000");
            }
        });
        payBtn.setOnClickListener(v->{
            validateViews();
            makePayment(enterPayAmmount.getText().toString(),userTxt.getText().toString(),"Recharge");
        });


    }
    UserModel userSnap;
    private void getUserSnapshot() {
        mRef.child(API.userNode).child("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user = snapshot.getValue(UserModel.class);
                userSnap = user;
                //updateUserData(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                getUserSnapshot();

            }
        });
    }

    private void makePayment(String amount, String name, String desc) {
        // on below line we are calling an easy payment method and passing
        // all parameters to it such as upi id,name, description and others.
        showDialog();
        try {
            String transactionId = "Trx" + System.currentTimeMillis();
            EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
                    .setPayeeVpa("BHARATPE09903648217@yesbankltd")
                    .setPayeeName(name)
                    .setPayeeMerchantCode("12345")
                    .setTransactionId(transactionId)
                    //.setTransactionRefId("T2020090212345")
                    .setDescription(desc)
                    .setAmount(amount);
            EasyUpiPayment upiPayment = builder.build();
            // on below line we are calling a start
            // payment method to start a payment.
            upiPayment.startPayment();
            // on below line we are calling a set payment
            // status listener method to call other payment methods.
            upiPayment.setPaymentStatusListener(this);
        }catch (Exception e){
            Toast.makeText(PaymentActivity.this, "Invalid merchant account number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTransactionCancelled() {
        hideDialog();
        Toast.makeText(this, "User cancel the transaction!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionCompleted(@NonNull TransactionDetails transactionDetails) {
        Toast.makeText(this, "Transaction Completed", Toast.LENGTH_SHORT).show();
        Log.d("de_billing", "onTransactionCompleted: "+ transactionDetails.getTransactionStatus());
        //     transactionDetails.get
        updateDatabase(transactionDetails.getAmount());


    }
    private void updateDatabase(String ammount){
        int price = Integer.parseInt(userSnap.getBalance()) ;
        int balnce = price + Integer.parseInt(ammount);
        userSnap.setBalance(String.valueOf(balnce));
        mRef.child(API.userNode).child("").child("balance").setValue(balnce, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    hideDialog();
                    Toast.makeText(PaymentActivity.this, "Payment made successfully", Toast.LENGTH_SHORT).show();
                }else{
                    updateDatabase(ammount);
                }
            }
        });

    }
    private boolean isDialogShowing = false;

    private void showDialog(){
        if(dialog!=null){
            if(!dialog.isShowing()){
                dialog.show();
                isDialogShowing = true;
            }
        }
    } private void hideDialog(){
        if(dialog!=null){
            if(dialog.isShowing()){
                dialog.dismiss();
                isDialogShowing = false;
            }
        }
    }
    private void  validateViews(){
        if(TextUtils.isEmpty(userTxt.getText().toString())){
            userTxt.setError("Empty Text");
            return ;
        }
        if(TextUtils.isEmpty(editTextPhone.getText().toString())){
            editTextPhone.setError("Invalid number!");
            return ;
        }
        if(TextUtils.isEmpty(edtTextEmail.getText().toString())){
            edtTextEmail.setError("Empty email");
            return ;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(edtTextEmail.toString()).matches()){
            edtTextEmail.setError("Invalid Email Format!");
            return;
        }
        if(TextUtils.isEmpty(enterPayAmmount.getText().toString())){
            enterPayAmmount.setError("Enter an amount");
            return;
        }

    }

    @Override
    public void onBackPressed() {
        if(isDialogShowing){
            Toast.makeText(PaymentActivity.this, "App in processing...", Toast.LENGTH_SHORT).show();
        }else {
            super.onBackPressed();
        }
    }

}