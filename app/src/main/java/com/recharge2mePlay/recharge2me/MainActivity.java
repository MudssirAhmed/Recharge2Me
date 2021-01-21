package com.recharge2mePlay.recharge2me;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Global.custom_Loading_Dialog.CustomToast;
import Global.custom_Loading_Dialog.EntryDialog;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    CustomToast toast;
    EntryDialog entryDialog;
    ProgressBar progressBar;

    // SharedPreferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        toast = new CustomToast(this);
        entryDialog = new EntryDialog(this);
        progressBar = findViewById(R.id.pb_entryProgress);

        sharedPreferences = getApplicationContext().getSharedPreferences("Providers", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("ProvidersData", "Get");
        editor.apply();

        checkStatus();

    }

    private void checkStatus(){

        DocumentReference docRef = db.collection("ScreenDialog").document("EntryScreen");

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                EntryMessage message = documentSnapshot.toObject(EntryMessage.class);

                String messageText = message.getMessage();
                String ActionText = message.getAction();
                String link = message.getLink();
                String version = message.getVersion();
                String check = message.getCheck();

                Log.i("Version", link + "   " + version);

                if(check.equals(ActionText)){
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent =  new Intent(MainActivity.this , LogInSignIn_Entry.EntryActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 70);
                }
                else if(check.equals("link")){
                    update(version, messageText);
                }
                else{
                    entryDialog.showDialog(messageText);
                    progressBar.setVisibility(View.GONE);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Main", e.getMessage());
                toast.showToast(e.getMessage());
                finish();
            }
        });
    }
    private void update(String version, String messageText){
        try {
            Context context = getApplicationContext();
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String apkVersion = pInfo.versionName;

            if(apkVersion.equals(version)){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent =  new Intent(MainActivity.this , LogInSignIn_Entry.EntryActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 70);
            }
            else {
                progressBar.setVisibility(View.GONE);
                EntryDialog entry = new EntryDialog(MainActivity.this);
                Dialog dialog = entry.update(messageText);

                dialog.findViewById(R.id.btn_enteryMessage_update).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                });
            }

        } catch ( PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}