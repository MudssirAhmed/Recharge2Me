package recahrge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonObject;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.recharge2mePlay.recharge2me.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Global.customAnimation.MyAnimation;
import Global.custom_Loading_Dialog.CustomToast;
import Global.custom_Loading_Dialog.LoadingDialog;
import LogInSignIn_Entry.DataTypes.CreateAccount_userDetails;
import LogInSignIn_Entry.DataTypes.User_googleAndOwn;
import Ui_Front_and_Back_end.Transactions.NotificationTransactionDetails;
import recahrge.DataTypes.rechargeFirbase.Order;
import recahrge.DataTypes.rechargeFirbase.Pay2All.Pay2All_rechargeFirebase;
import recahrge.DataTypes.rechargeFirbase.Paytm.PaytmRefundData;
import recahrge.DataTypes.rechargeFirbase.Paytm.PaytmTransactionData;
import recahrge.paytm.PaytmToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import Retrofit.JsonConvertor;

public class StartPaymentPaytm extends AppCompatActivity {

    TextView tv_number,
             tv_operator,
             tv_circle,
             tv_details,
             tv_amount,
             tv_rechargeAmount,
             tv_paytmStatus,
             tv_rechargeStatus,
             tv_paymentStatus;

    ImageView iv_paytm,
              iv_operator;

    LottieAnimationView lv_paytm,
                        lv_recharge;

    // ConsTraintLayouts
    ConstraintLayout cL_paymnet,
                     cL_status;

    // Customs
    LoadingDialog loadingDialog;
    CustomToast customToast;
    MyAnimation animation;

    //Firebase
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    //RequestCodes
    final int ActivityRequestCode = 121;

    // Flags
    int flag = 1;
    int paytmFlag = 1;
    int refundFlag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_payment_paytm);

        // TextView
        tv_number = findViewById(R.id.tv_payments_mobileNumber);
        tv_operator = findViewById(R.id.tv_payments_operator);
        tv_circle = findViewById(R.id.tv_payments_circle);
        tv_details = findViewById(R.id.tv_payments_planDetails);
        tv_amount = findViewById(R.id.tv_payments_amount);
        tv_rechargeAmount = findViewById(R.id.tv_payments_recharge);
        tv_paytmStatus = findViewById(R.id.tv_payments_paytmStatus);
        tv_rechargeStatus = findViewById(R.id.tv_payments_rechargeStatus);
        tv_paymentStatus = findViewById(R.id.tv_payments_status);

        // ImageView
        iv_paytm = findViewById(R.id.iv_payments_paytm);
        iv_operator = findViewById(R.id.iv_payments_operator);

        //LottieAnimation
        lv_paytm = findViewById(R.id.lv_payments_paytm);
        lv_recharge = findViewById(R.id.lv_payments_recharge);

        //ConstrintLayout
        cL_paymnet = findViewById(R.id.cL_payments_getPayments);
        cL_status = findViewById(R.id.cL_payments_status);

        //Customs
        customToast = new CustomToast(this);
        loadingDialog = new LoadingDialog(this);
        animation = new MyAnimation();

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        String number = getIntent().getExtras().getString("Number");
        String operator = getIntent().getExtras().getString("Operator");
        String circel = getIntent().getExtras().getString("circle");
        String details = getIntent().getExtras().getString("Details");
        String amount = getIntent().getExtras().getString("Amount");

        tv_number.setText(number);
        tv_operator.setText(operator);
        tv_circle.setText(circel);
        tv_details.setText(details);
        tv_amount.setText(setTransactionAmount(amount, operator));
        tv_rechargeAmount.setText(amount);

        cL_paymnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation.onClickAnimation(view);
                getPaymentsPaytm();
            }
        });

    } // End of onCreate()

    private void getPaymentsPaytm(){

        String TxnAmount = getTxtAmount();

        if(TxnAmount.equals("empty")){
            customToast.showToast("Error! in amount");
            return;
        }

        Log.i("Amount", TxnAmount);

        loadingDialog.startLoading();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://recharge2me.herokuapp.com/paytm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonConvertor jsonConvertor = retrofit.create(JsonConvertor.class);

        String orderId = getOrderId();
        String recAmount = getRechargeAmount();
        String recDetail = tv_details.getText().toString().trim();
        String number = tv_number.getText().toString().trim();
        String operator = tv_operator.getText().toString().trim();
        String opId = getOpId(tv_operator.getText().toString().trim());

        if(recAmount.equals("empty")){
            customToast.showToast("Error! in amount");
            loadingDialog.stopLoading();
            return;
        }

        Call<PaytmToken> call = jsonConvertor.getPaytmTransactionToken(orderId, TxnAmount, mAuth.getUid(),
                recAmount, recDetail, number, operator, opId);

        String finalAmount = TxnAmount;

        call.enqueue(new Callback<PaytmToken>() {
            @Override
            public void onResponse(Call<PaytmToken> call, Response<PaytmToken> response) {

                if(!response.isSuccessful()){
                    customToast.showToast("Error! code: " + response.code());
                    loadingDialog.stopLoading();
                    return;
                }

                PaytmToken data = response.body();
                PaytmToken.Body body = data.getBody();
                PaytmToken.Body.ResultInfo resultInfo = body.getResultInfo();

                String resultCode = resultInfo.getResultCode();
                String resultMessage = resultInfo.getResultMsg();
                String txnTokken = body.getTxnToken();

                Log.i("Token_resCode", resultCode);

                if(resultCode.equals("0000")){
                    startPaytmOrder(txnTokken, finalAmount, orderId);
                }
                else {
                    loadingDialog.stopLoading();
                    Log.i("PaytmResult", "msg: " + resultMessage + "Code: " + resultCode);
                    customToast.showToast("Error! " + resultMessage);
                }

            }

            @Override
            public void onFailure(Call<PaytmToken> call, Throwable t) {
                customToast.showToast("Error! " + t.getMessage());
                loadingDialog.stopLoading();
            }
        });
    }
    private void startPaytmOrder(String token, String amount, String orderId){


        String mid = "SIOyvP79178006245450";
        String host = "\"https://securegw.paytm.in";
        String callBackUrl = host + "/theia/paytmCallback?ORDER_ID="+orderId;
        PaytmOrder paytmOrder = new PaytmOrder(orderId, mid, token, amount, callBackUrl);

        loadingDialog.stopLoading();

        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle bundle) {

                String ORDERID = bundle.getString("ORDERID");
                String RESPCODE = bundle.getString("RESPCODE");
                String RESPMSG = bundle.getString("RESPMSG");

                setPaymnentStatus(ORDERID, RESPCODE, RESPMSG);
            }

            @Override
            public void networkNotAvailable() {
                customToast.showToast("nerwork not avialable");
            }

            @Override
            public void onErrorProceed(String s) {
                customToast.showToast(s);
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                customToast.showToast(s);
            }

            @Override
            public void someUIErrorOccurred(String s) {
                customToast.showToast(s);
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                customToast.showToast("Error! " + s + "\n" + s1);
            }

            @Override
            public void onBackPressedCancelTransaction() {
                customToast.showToast("Transaction Cancled");
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                customToast.showToast(s);
            }
        });// code statement);


        transactionManager.startTransaction(this, ActivityRequestCode);

        getRewards();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode && data != null) {
            Log.i("Response_bundle", "Res: " + data.getStringExtra("response"));

            String res = data.getStringExtra("response");

            try {
                JSONObject json = new JSONObject(res);

                Log.i("Response_bundle", "Res: " + json.getString("ORDERID") + " " + json.getString("RESPCODE") + " " + json.getString("RESPMSG"));

                setPaymnentStatus(json.getString("ORDERID"), json.getString("RESPCODE"), json.getString("RESPMSG"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setImageViewOnOperatorImageView(String operator){

        try {
            switch(operator){
                case "Idea" : iv_operator.setImageResource(R.drawable.idea); break;
                case "IDEA" : iv_operator.setImageResource(R.drawable.idea); break;
                case "Vodafone" : iv_operator.setImageResource(R.drawable.idea); break;
                case "VODAFONE" : iv_operator.setImageResource(R.drawable.idea); break;
                case "Reliance Jio" : iv_operator.setImageResource(R.drawable.jio); break;
                case "Jio" : iv_operator.setImageResource(R.drawable.jio); break;
                case "Airtel" : iv_operator.setImageResource(R.drawable.airtel); break;
                case "AIRTEL" : iv_operator.setImageResource(R.drawable.airtel); break;
                case "Bsnl" : iv_operator.setImageResource(R.drawable.bsnl); break;
                case "BSNL" : iv_operator.setImageResource(R.drawable.bsnl); break;
            }
        }
        catch (Exception e){
            customToast.showToast("Unexpected Error!");
        }

    } // This will set the operator Image on op_imageView

    private void setPaymnentStatus(String orderId, String resCode, String resMessage){

        cL_status.setVisibility(View.VISIBLE);
        setImageViewOnOperatorImageView(tv_operator.getText().toString().trim());

        if(resCode.equals(("01"))){
            iv_paytm.setVisibility(View.INVISIBLE);
            lv_paytm.setAnimation("process_success.json");
            lv_paytm.playAnimation();
            lv_paytm.loop(false);

            tv_paytmStatus.setText(resMessage);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setRechargeStatus(orderId);
                    Log.i("orderId", "orderId: " + orderId);
                }
            }, 3000);

        }
        else{
            setPaytmData(orderId);
            Log.i("orderId", "orderId: " + orderId);
        }

    }
    private void setPaytmData(String orderId){
        DocumentReference docRef = db.collection("USERS").document(mAuth.getUid())
                .collection("Transactions").document(orderId);

        Log.i("orderId", "InSetPaytmData: " + orderId);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Order order = documentSnapshot.toObject(Order.class);
                    String paytmStatus = order.getPaytmStatus();
                    if(paytmStatus.equals("1")){
                        PaytmTransactionData paytmData = order.getPaytm();
                        String resCode = paytmData.getRESPCODE();

                        if(resCode.equals("01")){
                            iv_paytm.setVisibility(View.INVISIBLE);
                            lv_paytm.setAnimation("process_success.json");
                            lv_paytm.playAnimation();
                            lv_paytm.loop(false);
                            tv_paytmStatus.setText(paytmData.getRESPMSG());
                            setRechargeStatus(orderId);
                        }
                        else{
                            iv_paytm.setVisibility(View.INVISIBLE);
                            iv_operator.setVisibility(View.INVISIBLE);

                            lv_paytm.setAnimation("process_pending.json");
                            lv_recharge.setAnimation("process_pending.json");

                            lv_paytm.playAnimation();
                            lv_recharge.playAnimation();

                            lv_paytm.loop(false);
                            lv_recharge.loop(false);
                        }
                    }
                }
                else {
                    if(paytmFlag == 1){
                        paytmFlag = 0;
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setPaytmData(orderId);
                            }
                        }, 1000);
                    }
                    else {
                        iv_paytm.setVisibility(View.INVISIBLE);
                        iv_operator.setVisibility(View.INVISIBLE);

                        lv_paytm.setAnimation("process_pending.json");
                        lv_recharge.setAnimation("process_pending.json");

                        lv_paytm.playAnimation();
                        lv_recharge.playAnimation();

                        lv_paytm.loop(false);
                        lv_recharge.loop(false);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(paytmFlag == 1){
                    paytmFlag = 0;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setPaytmData(orderId);
                        }
                    }, 1000);
                }
                else {
                    iv_paytm.setVisibility(View.INVISIBLE);
                    iv_operator.setVisibility(View.INVISIBLE);

                    lv_paytm.setAnimation("process_pending.json");
                    lv_recharge.setAnimation("process_pending.json");

                    lv_paytm.playAnimation();
                    lv_recharge.playAnimation();

                    lv_paytm.loop(false);
                    lv_recharge.loop(false);
                }
            }
        });
    }
    private void setRechargeStatus(String orderId){
        DocumentReference docRef = db.collection("USERS").document(mAuth.getUid())
                .collection("Transactions").document(orderId);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Order order = documentSnapshot.toObject(Order.class);
                    String paytmStatus = order.getPaytmStatus();
                    Log.i("Error", "paytmStatus: "+ paytmStatus );

                    if(paytmStatus.equals("1")){
                        String pay2allStatus = order.getPay2allStatus();
                        Log.i("Error", "pay2allStatus: "+ pay2allStatus );

                        if(pay2allStatus.equals("1")){
                            // Now get pay2all feilds and set Data
                            Pay2All_rechargeFirebase pay2all = order.getPay2all();
                            int statusId = pay2all.getStatus_id();

                            if(statusId == 1 || statusId == 0){
                                tv_rechargeStatus.setText("Recharge success");
                                lv_recharge.setAnimation("process_success.json");
                                lv_recharge.playAnimation();
                                lv_recharge.loop(false);
                                iv_operator.setVisibility(View.INVISIBLE);
                                sendNotification(order.getNumber(), orderId, "successful");

                            }
                            else if(statusId == 2){

                                lv_recharge.setAnimation("process_failed.json");
                                lv_recharge.playAnimation();
                                lv_recharge.loop(false);
                                iv_operator.setVisibility(View.INVISIBLE);
                                tv_rechargeStatus.setText("recharge is failed");

                                sendNotification(order.getNumber(), orderId, "failed");

                                String paytmRefundStatus = order.getPaytmRefundStatus();
                                if(paytmRefundStatus.equals("1")){
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            PaytmRefundData refundData = order.getPaytmRefund();
                                            String message = refundData.getResultMsg();
                                            tv_paymentStatus.setText(message);
                                        }
                                    }, 3500);
                                }
                                else{
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            setRefundStatus(orderId);
                                        }
                                    }, 3500);
                                }
                            }
                            else {
                                lv_recharge.setAnimation("process_pending.json");
                                lv_recharge.playAnimation();
                                lv_recharge.loop(false);
                                iv_operator.setVisibility(View.INVISIBLE);
                                tv_rechargeStatus.setText("recharge is pending");
                                sendNotification(order.getNumber(), orderId, "pending");
                            }
                        }
                        else {
                            Log.i("Error", " else else ");
                            lv_recharge.setAnimation("process_pending.json");
                            lv_recharge.playAnimation();
                            lv_recharge.loop(false);
                            iv_operator.setVisibility(View.INVISIBLE);
                            tv_rechargeStatus.setText("recharge is pending");
                        }

                    }
                    else {
                        if(flag == 1){
                            flag = 0;
                            setRechargeStatus(orderId);
                        }
                        else {
                            Log.i("Error", " else else ");
                            lv_recharge.setAnimation("process_pending.json");
                            lv_recharge.playAnimation();
                            lv_recharge.loop(false);
                            iv_operator.setVisibility(View.INVISIBLE);
                            tv_rechargeStatus.setText("recharge is pending");
                        }
                    }

                }
                else {
                    if(flag == 1){
                        flag = 0;
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setRechargeStatus(orderId);
                            }
                        }, 1000);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Error", "error: " + e.getMessage());
                if(flag == 1){
                    flag = 0;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setRechargeStatus(orderId);
                        }
                    }, 1000);
                }
                else {
                    lv_recharge.setAnimation("process_pending.json");
                    lv_recharge.playAnimation();
                    lv_recharge.loop(false);
                    iv_operator.setVisibility(View.INVISIBLE);
                    tv_rechargeStatus.setText("recharge is pending");
                }
            }
        });
    }
    private void setRefundStatus(String orderId){
        DocumentReference docRef = db.collection("USERS").document(mAuth.getUid())
                .collection("Transactions").document(orderId);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Order order = documentSnapshot.toObject(Order.class);
                    String refundStatus = order.getPaytmRefundStatus();

                    if(refundStatus.equals("1")){
                        PaytmRefundData refundData = order.getPaytmRefund();
                        tv_rechargeStatus.setText(refundData.getResultMsg());
                    }
                    else{
                        if(refundFlag == 1){
                            refundFlag = 0;
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    setRefundStatus(orderId);
                                }
                            }, 1500);
                        }
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(refundFlag == 1){
                    refundFlag = 0;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setRefundStatus(orderId);
                        }
                    }, 1500);
                }
            }
        });
    }


    private void sendNotification(String number, String orderId, String status){

        Context contextNotification = getApplicationContext();
        final String CHANNEL_ID = "15";
        final int notificationId = 123;
        String Title = "Recharge2me";
        String Subject = "prepaid recharge " + status;
        String ContentText = "your recharge was "+ status + " on +91 " + number;

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, NotificationTransactionDetails.class);
        intent.putExtra("OrderId", orderId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_transparent_background)
                .setColor(R.color.perple)
                .setContentTitle(Title)
                .setContentText(ContentText)
                .setFullScreenIntent(pendingIntent, true)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Recharge2me";
            String description = "Prepaid recharge";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = contextNotification.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());
    }

    private int removeRupeeSymbol(String amt){

        char[] chars = amt.toCharArray();
        amt = "";

        for(int i=1; i<chars.length; i++){
            amt += String.valueOf(chars[i]);
        }

        return Integer.parseInt(amt);
    }

    private String setTransactionAmount(String amount, String operator){

        String amt = amount;
        if(amt.equals("") || amt.equals(" ")){
            return "empty";
        }

        char[] chars = amt.toCharArray();
        String temp;
        amt = "";

        for(char c : chars){
            temp = String.valueOf(c);
            if(temp.contains("₹") || temp.contains(" ")){
            }
            else {
                amt += temp;
            }
        }

        int iAmt = Integer.parseInt(amt);
        float fAmount = (2*iAmt)/100;

        int a = (int) Math.floor((double) iAmt - fAmount);

        if(operator.toLowerCase().equals("airtel")){
            return amount;
        }

        return "₹" +String.valueOf(a);
    }
    private String getTxtAmount(){
        String amt = tv_amount.getText().toString().trim();
        if(amt.isEmpty() || amt.contains("x") || amt.equals("Amount")){
            customToast.showToast("Error! in Amount ");
            return "empty";
        }
        else{
            char[] charArray = amt.toCharArray();
            amt = "";
            for (int i = 1; i <charArray.length; i++){
                amt += charArray[i];
            }
            return amt;
        }

    }
    private String getRechargeAmount(){
        String amt = tv_rechargeAmount.getText().toString().trim();

        if(amt.equals("") || amt.equals(" ")){
            return "empty";
        }

        char[] chars = amt.toCharArray();
        String temp;
        amt = "";

        for(char c : chars){
            temp = String.valueOf(c);
            if(temp.contains("₹") || temp.contains(" ")){
            }
            else {
                amt += temp;
            }
        }
        Log.i("RechargeAmount", "amt: " + amt);
        return amt;
    }
    private void getRewards(){
        int paytmAmount = removeRupeeSymbol(tv_amount.getText().toString().trim());
        int recharageAmount = removeRupeeSymbol(tv_rechargeAmount.getText().toString().trim());

        int rewards = recharageAmount - paytmAmount;

        if(rewards != 0){

            DocumentReference docRef = db.collection("USERS").document(mAuth.getUid());

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User_googleAndOwn data = documentSnapshot.toObject(User_googleAndOwn.class);
                    CreateAccount_userDetails userDetails = data.getUser_details();
                    String fireRewards = userDetails.getRewards();

                    setRewards(Integer.parseInt(fireRewards), rewards);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
        else {
            Log.i("Amount", "Equals to 0" + rewards);
        }

    }
    private void setRewards(int fireRewards, int rewards){
        int totalRewards = fireRewards + rewards;
        Log.i("Amount", "fire: " + fireRewards + "rewards: " + rewards + "Total Rewards: " + totalRewards);

        DocumentReference docRef = db.collection("USERS").document(mAuth.getUid());

        docRef.update(
                "user_details.rewards", String.valueOf(totalRewards)
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("Amount", "Successfully updated");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Amount", "Error: " + e.getMessage());
            }
        });
    }
    private String getOrderId() {
        DateFormat dateFormat = new SimpleDateFormat("_ddMMyyyyHHmmss");
        Date date = new Date();

        String dateTime =  dateFormat.format(date);
        String uid = mAuth.getUid();

        return uid + dateTime;
    } // getOrderId
    private String getOpId(String key){
        Map<String, String> operators = new HashMap<>();
        operators.put("AIRTEL", "1");
        operators.put("VODAFONE", "2");
        operators.put("IDEA", "3");
        operators.put("BSNL", "8");
        operators.put("Jio", "88");
        return operators.get(key);
    }    // Get opId



}