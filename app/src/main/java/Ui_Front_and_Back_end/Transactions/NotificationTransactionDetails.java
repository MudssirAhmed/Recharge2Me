package Ui_Front_and_Back_end.Transactions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.recharge2mePlay.recharge2me.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import Global.custom_Loading_Dialog.LoadingDialog;
import Ui_Front_and_Back_end.Main_UserInterface;
import recahrge.DataTypes.rechargeFirbase.Order;
import recahrge.DataTypes.rechargeFirbase.Pay2All.Pay2All_rechargeFirebase;
import recahrge.DataTypes.rechargeFirbase.Paytm.PaytmRefundData;
import recahrge.DataTypes.rechargeFirbase.Paytm.PaytmTransactionData;
import recahrge.paytm.PaytmToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import Retrofit.JsonConvertor;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationTransactionDetails extends AppCompatActivity {

    ProgressBar pb_process;

    ImageView iv_serverDown,
              iv_cross,
              iv_operator,
              iv_paymentSuccess,
              iv_paymentAccepted,
              iv_rechargeSuccess;

    TextView tv_amount_1,
             tv_number,
             tv_operator,
             tv_rechargeDetails,
             tv_amount_2,
             tv_status,
             tv_orderId,
             tv_utr;

    ConstraintLayout cL_txnNot,
                     cL_RaiseTickert;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_transaction_details);

        //ProgressBar
        pb_process = findViewById(R.id.pb_txnDetNot_progressbar);

        //ImageView
        iv_serverDown = findViewById(R.id.iv_txnDetNot_serverDown);
        iv_cross = findViewById(R.id.iv_txnDetNot_cross);
        iv_operator = findViewById(R.id.iv_txnDetNot_operator);
        iv_paymentSuccess = findViewById(R.id.iv_txnDetNot_paymentSuccess);
        iv_paymentAccepted = findViewById(R.id.iv_txnDetNot_paymentAccepted);
        iv_rechargeSuccess  = findViewById(R.id.iv_txnDetNot_rechargeSuccess);

        // TextView
        tv_amount_1 = findViewById(R.id.tv_txnDetNot_amount_1);
        tv_number = findViewById(R.id.tv_txnDetNot_number);
        tv_operator = findViewById(R.id.tv_txnDetNot_operator);
        tv_rechargeDetails = findViewById(R.id.tv_txnDetNot_rechargeDetails);
        tv_amount_2  = findViewById(R.id.tv_txnDetNot_amount_2);
        tv_status = findViewById(R.id.tv_txnDetNot_txnMessage);
        tv_orderId = findViewById(R.id.tv_txnDetNot_orderId);
        tv_utr = findViewById(R.id.tv_txnDetNot_utr);

        // ConstrainLoyout
        cL_txnNot = findViewById(R.id.cL_txnDetNot);
        cL_RaiseTickert = findViewById(R.id.cL_txnDetNot_ContactR2m);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String orderId = getIntent().getExtras().getString("OrderId");

        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMainUi();
            }
        });

        getDataFromFirebase(orderId);
    }

    private void gotoMainUi(){
        Intent intent = new Intent(this, Main_UserInterface.class);
        startActivity(intent);
        overridePendingTransition(R.anim.to_left_pop, R.anim.from_right_pop);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gotoMainUi();
    }

    private void getDataFromFirebase(String orderId){
        DocumentReference docRef = db.collection("USERS").document(mAuth.getUid())
                .collection("Transactions").document(orderId);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Order order = documentSnapshot.toObject(Order.class);
                    setDataOnViews(order);
                    pb_process.setVisibility(View.GONE);
                    iv_serverDown.setVisibility(View.GONE);
                    cL_txnNot.setVisibility(View.VISIBLE);
                }
                else {
                    pb_process.setVisibility(View.GONE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pb_process.setVisibility(View.GONE);
            }
        });
    }
    private void setDataOnViews(Order order){
        String operator = order.getOperator();
        String amount_1 = order.getRecAmt();
        String amount_2 = order.getPaytm().getTXNAMOUNT();
        String number = order.getNumber();
        String details = order.getRecDet();
        String orderId = order.getOrderId();

        setOperator(operator);
        setRechargeDetails(operator, amount_1, amount_2, number, details, orderId);
        setTransactionDetails(order);

    }
    private void setOperator(String operator){

        tv_operator.setText(operator + " Prepaid");

        switch(operator){
            case "Idea" : iv_operator.setImageResource(R.drawable.idea_transactions); break;
            case "IDEA" : iv_operator.setImageResource(R.drawable.idea_transactions); break;
            case "Vodafone" : iv_operator.setImageResource(R.drawable.idea_transactions); break;
            case "VODAFONE" : iv_operator.setImageResource(R.drawable.idea_transactions); break;
            case "Reliance Jio" : iv_operator.setImageResource(R.drawable.jio_transactions); break;
            case "Jio" : iv_operator.setImageResource(R.drawable.jio_transactions); break;
            case "Airtel" : iv_operator.setImageResource(R.drawable.airtel_transactions); break;
            case "AIRTEL" : iv_operator.setImageResource(R.drawable.airtel_transactions); break;
            case "Bsnl" : iv_operator.setImageResource(R.drawable.bsnl_transactions); break;
            case "BSNL" : iv_operator.setImageResource(R.drawable.bsnl_transactions); break;
        }

    }
    private void setRechargeDetails(String operator, String amount_1, String amount_2, String number, String details, String orderId){

        tv_amount_1.setText("₹ " + amount_1);
        tv_number.setText("+91 " + number);
        tv_rechargeDetails.setText(details);
        tv_amount_2.setText("₹ " + amount_2);
        tv_orderId.setText(orderId);
    }
    private void setTransactionDetails(Order order){

        String paytmStatus = order.getPaytmStatus();

        if(paytmStatus.equals("1")){
            PaytmTransactionData paytmData = order.getPaytm();
            String resCode = paytmData.getRESPCODE();
            if(resCode.equals("01")){
                iv_paymentSuccess.setImageResource(R.drawable.verified);
                iv_paymentAccepted.setImageResource(R.drawable.verified);
                String pay2allStatus = order.getPay2allStatus();
                if(pay2allStatus.equals("1")){
                    Pay2All_rechargeFirebase pay2all = order.getPay2all();
                    int statusId = pay2all.getStatus_id();
                    String message = pay2all.getMessage();
                    String utr = pay2all.getUtr();

                    if(statusId == 0 || statusId == 1){ // success
                        iv_rechargeSuccess.setImageResource(R.drawable.verified);
                        tv_utr.setText(pay2all.getUtr());
                    }
                    else if(statusId == 2){// Failed
                        iv_rechargeSuccess.setImageResource(R.drawable.verified_shadow);
                        tv_status.setText(message);
                    }
                    else if(statusId == 3){ // in Pending state
                        iv_rechargeSuccess.setImageResource(R.drawable.verified_shadow);
                        tv_status.setText(message);
                    }

                    String PaytmrefundStatus = order.getPaytmRefundStatus();
                    if(PaytmrefundStatus.equals("1")){
                        PaytmRefundData refundData = order.getPaytmRefund();
                        String refundStatus = refundData.getRefundStatus();

                        if(refundStatus.equals("1")){
                            String resultmsg = refundData.getResultMsg();
                            String ExpectedDate = refundData.getExpectedDate();

                            tv_status.setText(resultmsg + "\nExpected Date: " + ExpectedDate);

                        }
                        else {
                            tv_status.setText(refundData.getResultMsg());
                        }
                    }
                }
            }
            else {
                tv_status.setText(paytmData.getRESPMSG());
            }
        }

    }


}