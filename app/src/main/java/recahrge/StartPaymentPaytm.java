package recahrge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.recharge2mePlay.recharge2me.R;

import Global.custom_Loading_Dialog.CustomToast;

public class StartPaymentPaytm extends AppCompatActivity {

    TextView tv_number,
             tv_operator,
             tv_circle,
             tv_details,
             tv_amount;

    CustomToast customToast;

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

        //Customs
        customToast = new CustomToast(this);


        String number = getIntent().getExtras().getString("Number");
        String operator = getIntent().getExtras().getString("Operator");
        String circel = getIntent().getExtras().getString("circle");
        String details = getIntent().getExtras().getString("Details");
        String amount = getIntent().getExtras().getString("Amount");

        tv_number.setText("+91 " + number);
        tv_operator.setText(operator);
        tv_circle.setText(circel);
        tv_details.setText(details);
        tv_amount.setText(amount);
    }

    private int getAmount(String amount){

        if(amount.toLowerCase().contains("x")){
            customToast.showToast("Error! please try again later! ");

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 500);
        }

        String s = "";

        char[] chars = amount.toCharArray();
        for(int i=1; i<chars.length; i++){
            s += chars[i];
        }

       return Integer.parseInt(s);
    }
}