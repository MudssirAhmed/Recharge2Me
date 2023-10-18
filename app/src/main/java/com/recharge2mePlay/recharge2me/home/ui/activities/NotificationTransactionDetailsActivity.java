package com.recharge2mePlay.recharge2me.home.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.recharge2mePlay.recharge2me.R;

public class NotificationTransactionDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_transaction_details);


        String orderId = getIntent().getExtras().getString("OrderId");
        Toast.makeText(this, "orderId: " + orderId, Toast.LENGTH_SHORT).show();
    }


}