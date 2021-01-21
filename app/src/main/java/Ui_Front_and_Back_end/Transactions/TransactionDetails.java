package Ui_Front_and_Back_end.Transactions;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.recharge2mePlay.recharge2me.R;

import Ui_Front_and_Back_end.Main_UserInterface;
import recahrge.DataTypes.rechargeFirbase.Order;
import recahrge.DataTypes.rechargeFirbase.Pay2All.Pay2All_rechargeFirebase;
import recahrge.DataTypes.rechargeFirbase.Paytm.PaytmRefundData;
import recahrge.DataTypes.rechargeFirbase.Paytm.PaytmTransactionData;


public class TransactionDetails extends Fragment {

    View view;

    ImageView iv_cross,
              iv_rechargeOperator,
              iv_paymentSuccess,
              iv_paymentAccepted,
              iv_rechargeSuccess,
              iv_serverDown;

    TextView tv_txnAmt_1,
             tv_txnAmt_2,
             tv_txnDetails,
             tv_txnNumber,
             tv_txnOperator,
             tv_txnOrderId,
             tv_txnUtr,
             tv_txnMessage;

    ProgressBar pb_load;

    ConstraintLayout cL_mainLayout,
                     cL_contactR2m;

    String fromHome, fromTransaction, orderId;
    public TransactionDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transaction_details, container, false);

        // ImageView
        iv_cross = view.findViewById(R.id.iv_txnDetNot_cross);
        iv_rechargeOperator = view.findViewById(R.id.iv_txnDetNot_operator);
        iv_paymentSuccess = view.findViewById(R.id.iv_txnDetNot_paymentSuccess);
        iv_paymentAccepted = view.findViewById(R.id.iv_txnDetNot_paymentAccepted);
        iv_rechargeSuccess = view.findViewById(R.id.iv_txnDetNot_rechargeSuccess);
        iv_serverDown = view.findViewById(R.id.iv_txnDetNot_serverDown);

        // TextView
        tv_txnAmt_1 = view.findViewById(R.id.tv_txnDetNot_amount_1);
        tv_txnNumber = view.findViewById(R.id.tv_txnDetNot_number);
        tv_txnOperator = view.findViewById(R.id.tv_txnDetNot_operator);
        tv_txnAmt_2 = view.findViewById(R.id.tv_txnDetNot_amount_2);
        tv_txnDetails = view.findViewById(R.id.tv_txnDetNot_rechargeDetails);
        tv_txnOrderId = view.findViewById(R.id.tv_txnDetNot_orderId);
        tv_txnUtr = view.findViewById(R.id.tv_txnDetNot_utr);
        tv_txnMessage = view.findViewById(R.id.tv_txnDetNot_txnMessage);

        // ConstraintLayout
        cL_mainLayout = view.findViewById(R.id.cL_txnDetNot);
        cL_contactR2m = view.findViewById(R.id.cL_txnDetNot_ContactR2m);

        // progressBar
        pb_load = view.findViewById(R.id.pb_txnDetNot_progressbar);


        fromHome = TransactionDetailsArgs.fromBundle(getArguments()).getFromHome();
        fromTransaction = TransactionDetailsArgs.fromBundle(getArguments()).getFromTransactions();
        orderId = TransactionDetailsArgs.fromBundle(getArguments()).getOrderId();

        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoHomeUi();
            }
        });
        cL_contactR2m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText((Main_UserInterface) requireActivity(), "mail us for any Query!", Toast.LENGTH_SHORT).show();

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "recharge2me.help@gmail.com", null));

                getActivity().startActivity(Intent.createChooser(emailIntent, null));
            }
        });

        getOrderData();

        return view;
    }

    private void getOrderData(){
        if(!orderId.isEmpty()){
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("USERS").document(mAuth.getUid())
                    .collection("Transactions").document(orderId);

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        Order order = documentSnapshot.toObject(Order.class);
                        setOrderData(order);
                    }
                    else {
                        pb_load.setVisibility(View.GONE);
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pb_load.setVisibility(View.GONE);
                }
            });
        }
    }

    private void setOrderData(Order order){

        String operator = order.getOperator();
        String amount_1 = order.getRecAmt();
        String amount_2 = order.getPaytm().getTXNAMOUNT();
        String number = order.getNumber();
        String details = order.getRecDet();

        // first set recharge data
        setOperator(operator);
        setRechargeDetails(operator, amount_1, amount_2, number, details);
        setTransactionDetails(order);

        pb_load.setVisibility(View.GONE);
        iv_serverDown.setVisibility(View.GONE);
        cL_mainLayout.setVisibility(View.VISIBLE);

    }
    private void setOperator(String operator){

        tv_txnOperator.setText(operator + " Prepaid");

        switch(operator){
            case "Idea" : iv_rechargeOperator.setImageResource(R.drawable.idea_transactions); break;
            case "IDEA" : iv_rechargeOperator.setImageResource(R.drawable.idea_transactions); break;
            case "Vodafone" : iv_rechargeOperator.setImageResource(R.drawable.idea_transactions); break;
            case "VODAFONE" : iv_rechargeOperator.setImageResource(R.drawable.idea_transactions); break;
            case "Reliance Jio" : iv_rechargeOperator.setImageResource(R.drawable.jio_transactions); break;
            case "Jio" : iv_rechargeOperator.setImageResource(R.drawable.jio_transactions); break;
            case "Airtel" : iv_rechargeOperator.setImageResource(R.drawable.airtel_transactions); break;
            case "AIRTEL" : iv_rechargeOperator.setImageResource(R.drawable.airtel_transactions); break;
            case "Bsnl" : iv_rechargeOperator.setImageResource(R.drawable.bsnl_transactions); break;
            case "BSNL" : iv_rechargeOperator.setImageResource(R.drawable.bsnl_transactions); break;
        }

    }
    private void setRechargeDetails(String operator, String amount_1, String amount_2, String number, String details){

        tv_txnAmt_1.setText("₹ " + amount_1);
        tv_txnNumber.setText("+91 " + number);
        tv_txnDetails.setText(details);
        tv_txnAmt_2.setText("₹ " + amount_2);
        tv_txnOrderId.setText(orderId);
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
                        tv_txnUtr.setText(pay2all.getUtr());
                    }
                    else if(statusId == 2){// Failed
                        iv_rechargeSuccess.setImageResource(R.drawable.verified_shadow);
                        tv_txnMessage.setText(message);
                    }
                    else if(statusId == 3){ // in Pending state
                        iv_rechargeSuccess.setImageResource(R.drawable.verified_shadow);
                        tv_txnMessage.setText(message);
                    }

                    String PaytmrefundStatus = order.getPaytmRefundStatus();
                    if(PaytmrefundStatus.equals("1")){
                        PaytmRefundData refundData = order.getPaytmRefund();
                        String refundStatus = refundData.getRefundStatus();

                        if(refundStatus.equals("1")){
                            String resultmsg = refundData.getResultMsg();
                            String ExpectedDate = refundData.getExpectedDate();

                            tv_txnMessage.setText(resultmsg + "\nExpected Date: " + ExpectedDate);

                        }
                        else {
                            tv_txnMessage.setText(refundData.getResultMsg());
                        }
                    }
                }
            }
            else {
                tv_txnMessage.setText(paytmData.getRESPMSG());
            }
        }

    }



    private void gotoHomeUi(){
        if(fromHome.equals("Home")){
            Navigation.findNavController(view).navigate(R.id.action_transactionDetails_to_ui_Home);
        }
        else if(fromTransaction.equals("Transactions")){
            Navigation.findNavController(view).navigate(R.id.action_transactionDetails_to_ui_Transactions);
        }
    }
}