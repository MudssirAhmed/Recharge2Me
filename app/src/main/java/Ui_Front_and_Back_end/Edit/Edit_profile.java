package Ui_Front_and_Back_end.Edit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.recharge2mePlay.recharge2me.R;

import Global.Validation.Validate;
import LogInSignIn_Entry.DataTypes.CreateAccount_userDetails;
import LogInSignIn_Entry.DataTypes.Google_User_Details;
import LogInSignIn_Entry.DataTypes.User_googleAndOwn;
import Global.custom_Loading_Dialog.CustomToast;
import Global.custom_Loading_Dialog.LoadingDialog;

public class Edit_profile extends AppCompatActivity {

    EditText et_edit_name,
             et_edit_number;

    Button btn_edit_save;

    private String Rewards;
    private Google_User_Details googleUser;
    private Validate validate;

    CustomToast toast;
    LoadingDialog loadingDialog;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        et_edit_name = findViewById(R.id.et_edit_name);
        et_edit_number = findViewById(R.id.et_edit_number);

        btn_edit_save = findViewById(R.id.btn_edit_save);

        toast = new CustomToast(this);
        loadingDialog = new LoadingDialog(this);
        validate = new Validate(this);

        // StartLoadingDialog
        loadingDialog.startLoading();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        btn_edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate.checkName(et_edit_name.getText().toString())){
                    if(validate.checkNumber(et_edit_number.getText().toString().trim())){
                        try {
                            saveDataInFirebase(googleUser);
                            loadingDialog.startLoading();
                        }
                        catch (Exception e){
                            Log.d("Exc", e.getMessage());
                            loadingDialog.stopLoading();
                        }
                    }
                }
            }
        });

        getDataFromFirebase();

    }

    private void getDataFromFirebase(){

        try {
            DocumentReference docRef = db.collection("USERS").document(mAuth.getUid());

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User_googleAndOwn data = documentSnapshot.toObject(User_googleAndOwn.class);
                    googleUser = data.getGoogle();
                    CreateAccount_userDetails user = data.getUser_details();

                    et_edit_name.setText(user.getName());
                    et_edit_number.setText(user.getNumber());
                    Rewards = user.getRewards();

                    Log.d("Google", googleUser.getGoogle_Profile() + googleUser.getGoogle_UID());
                    loadingDialog.stopLoading();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    toast.showToast("Error! " + e.getMessage());
                    loadingDialog.stopLoading();
                    finish();
                }
            });
        }
        catch (Exception e){
            toast.showToast("Error! " + e.getMessage());
        }

    }
    private void saveDataInFirebase(Google_User_Details googleUser){

        try {
            // Changing Fields:-
            String name = et_edit_name.getText().toString().trim();
            String number = et_edit_number.getText().toString().trim();
            //Google Fileds
            String googleProfile = googleUser.getGoogle_Profile();
            String googleUid = googleUser.getGoogle_UID();
            // Firebase Email
            String email = firebaseUser.getEmail();


            CreateAccount_userDetails user = new CreateAccount_userDetails(name, email, Rewards, number);
            Google_User_Details googleuser = new Google_User_Details(googleUid, googleProfile);
            User_googleAndOwn GAO = new User_googleAndOwn(googleuser,user, mAuth.getUid());
            DocumentReference docRef = db.collection("USERS").document(mAuth.getUid());

            docRef.set(GAO)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Edit_profile.this, "Updated successfully ", Toast.LENGTH_SHORT).show();
                            loadingDialog.stopLoading();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Save", e.getMessage());
                            loadingDialog.stopLoading();
                            toast.showToast("Error! " + e.getMessage());
                        }
                    });

        }
        catch (Exception e){
            toast.showToast("Error! " + e.getMessage());
            loadingDialog.stopLoading();
        }
    }

}