package com.recharge2mePlay.recharge2me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.recharge2mePlay.recharge2me.onboard.ui.activities.EntryActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            Intent intent =  new Intent(this , EntryActivity.class);
            startActivity(intent);
            finish();
        }, 200);
    }
}