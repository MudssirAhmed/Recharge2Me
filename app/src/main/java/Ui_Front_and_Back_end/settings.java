package Ui_Front_and_Back_end;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.recharge2mePlay.recharge2me.R;

import Global.customAnimation.MyAnimation;
import Global.custom_Loading_Dialog.CustomToast;
import Global.custom_Loading_Dialog.LoadingDialog;
import LogInSignIn_Entry.DataTypes.CreateAccount_userDetails;
import LogInSignIn_Entry.DataTypes.User_googleAndOwn;
import Ui_Front_and_Back_end.Edit.Edit_profile;


public class settings extends Fragment {


    View view;

    TextView tv_settings_verify,
             tv_settings_edit,
             tv_settingsName,
             tv_settingsNumber,
             tv_settingsUid,
             tv_settingsEditNumber,
             tv_settingsEmail;

    // customs
    MyAnimation animation;
    CustomToast toast;
    LoadingDialog loadingDialog;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    public settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_settings, container, false);

        tv_settings_verify = view.findViewById(R.id.tv_settings_verify);
        tv_settings_edit = view.findViewById(R.id.tv_settings_edit);
        tv_settingsName = view.findViewById(R.id.tv_settingsName);
        tv_settingsNumber = view.findViewById(R.id.tv_settingsNumber);
        tv_settingsUid = view.findViewById(R.id.tv_settingsUid);
        tv_settingsEditNumber = view.findViewById(R.id.tv_settingsEditNumber);
        tv_settingsEmail = view.findViewById(R.id.tv_settingsEmail);

        // Init Customs
        animation = new MyAnimation();
        toast = new CustomToast(getActivity());
        loadingDialog = new LoadingDialog(getActivity());

        // Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // TODO set the dowload link
        tv_settings_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoEditUi();
            }
        });
        tv_settings_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation.onClickAnimation(tv_settings_verify);
            }
        });

        getUserDataFromFirebase();


        return view;
    }

    private void getUserDataFromFirebase(){

        try{
            loadingDialog.startLoading();
            DocumentReference docRef = db.collection("USERS").document(firebaseAuth.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    try {
                        User_googleAndOwn data = documentSnapshot.toObject(User_googleAndOwn.class);
                        CreateAccount_userDetails user = data.getUser_details();
                        setDataOnViews(user);
                    }
                    catch (Exception e){
                        loadingDialog.stopLoading();
                        toast.showToast("Error! " + e.getMessage());
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        catch (Exception e){
            loadingDialog.stopLoading();
            toast.showToast("Error! " + e.getMessage());
        }

    }

    private void setDataOnViews(CreateAccount_userDetails user){

        try {
            tv_settingsName.setText(user.getName());
            tv_settingsNumber.setText("+91 " + user.getNumber());
            tv_settingsUid.setText("user Id: " + firebaseAuth.getUid());
            tv_settingsEditNumber.setText("+91 " + user.getNumber());
            tv_settingsEmail.setText(user.getEmail());
            loadingDialog.stopLoading();
        }
        catch (Exception e){
            loadingDialog.stopLoading();
            toast.showToast("Error! " + e.getMessage());
        }

    }

    private void gotoEditUi(){
        animation.onClickAnimation(tv_settings_edit);

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(), Edit_profile.class);
                startActivity(intent);
            }
        }, 50);

    }
}