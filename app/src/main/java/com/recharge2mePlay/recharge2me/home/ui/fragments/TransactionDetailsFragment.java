package com.recharge2mePlay.recharge2me.home.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
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

import com.recharge2mePlay.recharge2me.home.ui.activities.HomeActivity;
import recahrge.DataTypes.rechargeFirbase.Order;
import recahrge.DataTypes.rechargeFirbase.Pay2All_rechargeFirebase;
import recahrge.DataTypes.rechargeFirbase.Pay2All_status;
import recahrge.DataTypes.rechargeFirbase.Paytm_transactonStatus;


public class TransactionDetailsFragment extends Fragment {

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
    public TransactionDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transaction_details, container, false);

        // ImageView
        iv_cross = view.findViewById(R.id.ic_txnDetNot_cross);
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


        fromHome = TransactionDetailsFragmentArgs.fromBundle(getArguments()).getFromHome();
        fromTransaction = TransactionDetailsFragmentArgs.fromBundle(getArguments()).getFromTransactions();
        orderId = TransactionDetailsFragmentArgs.fromBundle(getArguments()).getOrderId();

        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoHomeUi();
            }
        });

        cL_contactR2m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText((HomeActivity) requireActivity(), "mail us for any Query!", Toast.LENGTH_SHORT).show();

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
                    Order order = documentSnapshot.toObject(Order.class);

                    setOrderData(order);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    private void setOrderData(Order order){

        String operator = order.getOperator();
        String amount = order.getAmount();
        String number = order.getNumber();
        String details = order.getDetails();

        Pay2All_rechargeFirebase recharge = order.getRecharge();
        Pay2All_status status = order.getStatus();

        Paytm_transactonStatus transactonStatus = order.getTransactonStatus();

        // first set recharge data
        setOperator(operator);
        setRechargeDetails(operator, amount, number, details);

        setTransactionStatus(transactonStatus, recharge, status); // step 2: set Transaction status


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
    private void setRechargeDetails(String operator, String amount, String number, String details){

        tv_txnAmt_1.setText(amount);
        tv_txnNumber.setText("+91 " + number);
        tv_txnDetails.setText(details);
        tv_txnAmt_2.setText(amount);
        tv_txnOrderId.setText(orderId);
    }


    private void setTransactionStatus(Paytm_transactonStatus transactonStatus, Pay2All_rechargeFirebase recharge,
                                      Pay2All_status status){

        String resultCode = transactonStatus.getResultCode();
        String resultStatus = transactonStatus.getResultStatus();
        String resultMessage = transactonStatus.getResultMsg();

        switch (resultCode){
            case "01":  iv_paymentSuccess.setImageResource(R.drawable.verified);
                iv_paymentAccepted.setImageResource(R.drawable.verified); // if only transaction is success then do/chek recharge
                setPay2allRechargeDetails(recharge, status); // step 3: set Recharge details
                break;
        }

        if(!resultMessage.equals("Txn Success")){
            tv_txnMessage.setVisibility(View.VISIBLE);
            tv_txnMessage.setText(resultMessage);
        }

    }


    private void setPay2allRechargeDetails(Pay2All_rechargeFirebase recharge, Pay2All_status status){

        String statusId = recharge.getStatus_id();
        String message = recharge.getMessage();
        String utr = recharge.getUtr();

        Log.i("statusidpay2all", statusId);

        if(statusId.equals("0") || statusId.equals("1")){ // success
            iv_rechargeSuccess.setImageResource(R.drawable.verified);
        }
        else if(statusId.equals("2")){// Failed
            iv_rechargeSuccess.setImageResource(R.drawable.verified_shadow);
            tv_txnMessage.setText(message);
        }
        else if(statusId.equals("3")){ // in Pending state
            iv_rechargeSuccess.setImageResource(R.drawable.verified_shadow);
            tv_txnMessage.setText(message);
//            setRechargeStatus_pay2all_status(status);
        }

        tv_txnUtr.setText(utr);
    }
    private void setRechargeStatus_pay2all_status(Pay2All_status status){

        String statusId = status.getStatus_id();

        if(statusId.equals("0") || statusId.equals("1")){// Success
            iv_rechargeSuccess.setImageResource(R.drawable.verified);
        }
        else if(statusId.equals("2")){ // failed
            tv_txnMessage.setText("Recharge Failed");
        }
        else if(statusId.equals("3")){ // pending
            tv_txnMessage.setText("Recharge is in pending state");
        }
        else if(statusId.equals("4")){ // refund
            tv_txnMessage.setText("Refund request raised");
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