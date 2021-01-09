package Ui_Front_and_Back_end.Transactions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.recharge2mePlay.recharge2me.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import Global.custom_Loading_Dialog.LoadingDialog;
import recahrge.paytm.PaytmToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import Retrofit.JsonConvertor;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationTransactionDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_transaction_details);


        String orderId = getIntent().getExtras().getString("OrderId");
        Toast.makeText(this, "orderId: " + orderId, Toast.LENGTH_SHORT).show();
    }


}