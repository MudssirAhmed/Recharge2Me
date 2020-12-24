package Ui_Front_and_Back_end;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.recharge2mePlay.recharge2me.MainActivity;
import com.recharge2mePlay.recharge2me.R;

import java.util.Map;

import LogInSignIn_Entry.DataTypes.CreateAccount_userDetails;
import LogInSignIn_Entry.DataTypes.User_googleAndOwn;
import Ui_Front_and_Back_end.Edit.Edit_profile;
import custom_Loading_Dialog.LoadingDialog;

import static android.content.ContentValues.TAG;

public class Ui_Profile extends Fragment {

    View view;

    TextView tv_profile_userName,
             tv_profile_userNumber,
             tv_profile_userEmail,
             tv_profile_userRewards;

    ImageView iv_profile_edt;

    LoadingDialog loadingDialog;

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    final int EDIT_PROFILE = 1;

    public Ui_Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ui__profile, container, false);

        // Inti TextView
        tv_profile_userName = view.findViewById(R.id.tv_profile_userName);
        tv_profile_userNumber = view.findViewById(R.id.tv_profile_userNumber);
        tv_profile_userEmail = view.findViewById(R.id.tv_profile_userEmail);
        tv_profile_userRewards = view.findViewById(R.id.tv_profile_userRewards);

        // Init ImageView
        iv_profile_edt = view.findViewById(R.id.iv_profile_edit);

        // Init loading Dialog
        loadingDialog = new LoadingDialog((Main_UserInterface) requireActivity());

        // Init firebase
        firebaseAuth = firebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Init onClickListners
        iv_profile_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyAnimation();
                gotoEditProfile();
            }
        });

        getProfileData();

        return view;
    } // End of onCreate method

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_PROFILE){
            getProfileData();
        }

    }

    // This function appied animation on edit button
    private void applyAnimation(){
        iv_profile_edt
                .animate()
                .alpha(0f)
                .setDuration(100);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_profile_edt
                        .animate()
                        .alpha(1f)
                        .setDuration(100);
            }
        }, 100);
    }

    // This functuion is responsible for transact user into EditProfile Activity
    private void gotoEditProfile(){

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(), Edit_profile.class);
                startActivityForResult(intent, EDIT_PROFILE);
            }
        }, 100);

    }

    // This function will get The profileData
    public void getProfileData(){

        loadingDialog.startLoading();
        DocumentReference docRef = db.collection("USERS").document(firebaseAuth.getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User_googleAndOwn data = documentSnapshot.toObject(User_googleAndOwn.class);
                CreateAccount_userDetails userDetails = data.getUser_details();

                tv_profile_userName.setText(userDetails.getName());
                tv_profile_userNumber.setText("+91 " + userDetails.getNumber());
                tv_profile_userEmail.setText(userDetails.getEmail());
                tv_profile_userRewards.setText("₹ " + userDetails.getRewards());

                loadingDialog.stopLoading();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingDialog.stopLoading();
                Toast.makeText((Main_UserInterface) requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}