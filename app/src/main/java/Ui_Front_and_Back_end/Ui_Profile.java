package Ui_Front_and_Back_end;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.recharge2mePlay.recharge2me.R;

import java.util.HashMap;

import Global.customAnimation.MyAnimation;
import LogInSignIn_Entry.DataTypes.CreateAccount_userDetails;
import LogInSignIn_Entry.DataTypes.User_googleAndOwn;
import Ui_Front_and_Back_end.Edit.Edit_profile;
import Global.custom_Loading_Dialog.LoadingDialog;

public class Ui_Profile extends Fragment {

    View view;

    TextView tv_profile_userName,
             tv_profile_userNumber,
             tv_profile_userEmail,
             tv_profile_userRewards;

    ImageView iv_profile_edt;

    NestedScrollView sv_profile;

    ConstraintLayout cL_profileSettings;

    // customs
    LoadingDialog loadingDialog;
    MyAnimation animation;

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    final int EDIT_PROFILE = 1;
    int touchFlag = 1;


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

        // NestedScroolView
        sv_profile = view.findViewById(R.id.sv_profile);

        // Constraintlayout
        cL_profileSettings = view.findViewById(R.id.cL_profile_settings);

        // custom Animation
        animation = new MyAnimation();

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

        sv_profile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    animateNavDrawer();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    animateNavDrawer();
                }

                return false;
            }
        });

        cL_profileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSettingUi();
            }
        });

        getProfileData();

        return view;
    } // End of onCreate method

    private void animateNavDrawer(){
        NavigationView nav_drawer = getActivity().findViewById(R.id.nav_drawer);

        int a = nav_drawer.getVisibility();
        if(a == 0){
            if(touchFlag == 1){
                touchFlag = 0;

                nav_drawer.animate()
                        .alpha(0f)
                        .setDuration(200L)
                        .translationXBy(-100f)
                        .setListener(null);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nav_drawer.setVisibility(View.INVISIBLE);
                        touchFlag = 1;
                    }
                }, 200);
            }
        }
    }

    private void gotoSettingUi(){
        animation.onClickAnimation(cL_profileSettings);

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Ui_ProfileDirections.ActionUiProfileToSettings action =
                        Ui_ProfileDirections.actionUiProfileToSettings();
                action.setFromProfile("Profile");

                Navigation.findNavController(view).navigate(action);
            }
        }, 80);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_PROFILE){
            getProfileData();
        }

    }

    // This function appied animation on edit button
    private void applyAnimation(){
        animation.onClickAnimation(iv_profile_edt);
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
        }, 125);

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