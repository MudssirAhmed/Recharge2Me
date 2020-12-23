package com.recharge2mePlay.recharge2me;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import custom_Loading_Dialog.CustomToast;
import custom_Loading_Dialog.EntryDialog;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    CustomToast toast;
    EntryDialog entryDialog;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        toast = new CustomToast(this);
        entryDialog = new EntryDialog(this);
        progressBar = findViewById(R.id.pb_entryProgress);


        DocumentReference docRef = db.collection("Screen Dialog").document("Entry Screen");

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                EntryMessage message = documentSnapshot.toObject(EntryMessage.class);

                String messageText = message.getMessage();
                String ActionText = message.getAction();

                if(messageText.equals(ActionText)){
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
                else{
                    entryDialog.showDialog("Due to some reason R2M can't \n Working \n we're still handling it \n pleasse check after some time \n");
                    progressBar.setVisibility(View.GONE);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                toast.showToast(e.getMessage());
            }
        });


    }
}