package com.recharge2mePlay.recharge2me;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =  new Intent(MainActivity.this , LogInSignIn_Entry.EntryActivity.class);
                startActivity(intent);
                finish();
            }
        }, 100);
    }
}