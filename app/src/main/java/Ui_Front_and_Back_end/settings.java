package Ui_Front_and_Back_end;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.recharge2mePlay.recharge2me.R;

import org.w3c.dom.Document;

import Global.customAnimation.MyAnimation;
import Global.custom_Loading_Dialog.CustomToast;
import Global.custom_Loading_Dialog.LoadingDialog;
import Global.custom_Loading_Dialog.proceedDialog;
import LogInSignIn_Entry.DataTypes.CreateAccount_userDetails;
import LogInSignIn_Entry.DataTypes.User_googleAndOwn;
import LogInSignIn_Entry.EntryActivity;
import Ui_Front_and_Back_end.Edit.Edit_profile;
import Ui_Front_and_Back_end.firebase.DeleteUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;
import Retrofit.JsonConvertor;


public class settings extends Fragment {


    View view;

    TextView tv_settings_verify,
             tv_settings_edit,
             tv_settingsName,
             tv_settingsNumber,
             tv_settingsUid,
             tv_settingsEditNumber,
             tv_settingsEmail;

    ImageView iv_settingsVerify,
              iv_settingCross;

    Button btn_deleteMyAccount;

    NestedScrollView ns_settings;

    ConstraintLayout cL_tellAFreind,
                     cL_settingsRaiseTicket,
                     cL_settingsFeedback;

    // customs
    MyAnimation animation;
    CustomToast toast;
    LoadingDialog loadingDialog;

    // Int
    int navFlag = 1;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    FirebaseUser user;


    public settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_settings, container, false);

        //Nested ScrollView ns_settings
        ns_settings = view.findViewById(R.id.ns_settings);

        tv_settings_verify = view.findViewById(R.id.tv_settings_verify);
        tv_settings_edit = view.findViewById(R.id.tv_settings_edit);
        tv_settingsName = view.findViewById(R.id.tv_settingsName);
        tv_settingsNumber = view.findViewById(R.id.tv_settingsNumber);
        tv_settingsUid = view.findViewById(R.id.tv_settingsUid);
        tv_settingsEditNumber = view.findViewById(R.id.tv_settingsEditNumber);
        tv_settingsEmail = view.findViewById(R.id.tv_settingsEmail);

        //ImageView
        iv_settingsVerify = view.findViewById(R.id.iv_settingsVerify);
        iv_settingCross = view.findViewById(R.id.iv_settings_cross);

        // ConstraintLayout
        cL_tellAFreind = view.findViewById(R.id.cL_tellAFreind);
        cL_settingsFeedback = view.findViewById(R.id.cL_settingFeedback);
        cL_settingsRaiseTicket = view.findViewById(R.id.cL_settingRaiseTicket);

        //Buttons
        btn_deleteMyAccount = view.findViewById(R.id.btn_settings_deleteMyAccount);

        // Init Customs
        animation = new MyAnimation();
        toast = new CustomToast(getActivity());
        loadingDialog = new LoadingDialog(getActivity());

        // Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = firebaseAuth.getCurrentUser();

        iv_settingCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoProfileUi(settingsArgs.fromBundle(getArguments()).getFromProfile());
            }
        });

        ns_settings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {

                if(e.getAction() == MotionEvent.ACTION_UP){
                    animateNavDrawer();
                }
                else if(e.getAction() == MotionEvent.ACTION_DOWN){
                    animateNavDrawer();
                }
                    return false;
            }
        });
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
                sendVerificationEmail();
            }
        });



        cL_settingsFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation.onClickAnimation(cL_settingsFeedback);
                giveFeedBack();
            }
        });
        cL_settingsRaiseTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation.onClickAnimation(cL_settingsRaiseTicket);
                raiseTicket();
            }
        });
        cL_tellAFreind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation.onClickAnimation(cL_tellAFreind);
                Log.d("clicked", "click");
                cL_tellAFreind.setEnabled(false);
                cL_tellAFreind.setClickable(false);
                getLinkFromFirebase();
            }
        });

        btn_deleteMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForDeleteAccount();
            }
        });


        getUserDataFromFirebase();
        checkVerifiedOrNot();

        return view;
    }

    private void checkForDeleteAccount(){
        proceedDialog proceedDialog = new proceedDialog(getActivity());
        Dialog dialog = proceedDialog.showProceedDialog();
        CheckBox cb_deleteMyAccount = dialog.findViewById(R.id.cb_reaunthicate_check);
        dialog.setCancelable(true);
        dialog.findViewById(R.id.btn_dialog_deleteAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextInputLayout til_email = dialog.findViewById(R.id.et_reaunthicate_emailFeild);
                TextInputLayout til_password = dialog.findViewById(R.id.et_reaunthicate_password);
                TextView tv_error = dialog.findViewById(R.id.tv_reaunthicate_error);
                String email = til_email.getEditText().getText().toString();
                String password = til_password.getEditText().getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    toast.showToast("please enter all feilds carefully!");
                }
                else {
                    if(cb_deleteMyAccount.isChecked()){
                        dialog.dismiss();
                        loadingDialog.startLoading();
                        reAunthiancateUser(email, password, tv_error);
                    }
                    else {
                        toast.showToast("please check the box");
                    }
                }

            }
        });
    }

    private void reAunthiancateUser(String email, String password, TextView tv_error){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);

        user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                deleteData(user.getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("user",e.getMessage());
                tv_error.setText(e.getMessage());
                loadingDialog.stopLoading();
            }
        });

    }

    private void deleteData(String uid){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://recharge2me.herokuapp.com/firebase/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonConvertor  jsonConvertor = retrofit.create(JsonConvertor.class);

        Call<DeleteUser> call = jsonConvertor.deleteUser(uid);

        call.enqueue(new Callback<DeleteUser>() {
            @Override
            public void onResponse(Call<DeleteUser> call, Response<DeleteUser> response) {

                if(!response.isSuccessful()){
                    toast.showToast("Error! please try again");
                    loadingDialog.stopLoading();
                    return;
                }

                DeleteUser data = response.body();
                String msg = data.getMsg();

                if(msg.equals("Successfully Deleted")){
                    Log.i("DeleteUser", data.getMsg());
                    deleteAccount();
                }
                else {
                    toast.showToast(msg);
                    loadingDialog.stopLoading();
                }

            }

            @Override
            public void onFailure(Call<DeleteUser> call, Throwable t) {
                toast.showToast("Error! " + t.getMessage());
                loadingDialog.stopLoading();
            }
        });
    }

    private  void deleteAccount(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                loadingDialog.stopLoading();
                Toast.makeText(getActivity(), "Account Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), EntryActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                toast.showToast("Please try again");
                loadingDialog.stopLoading();
            }
        });
    }

    private void gotoProfileUi(String check){
        if(check.equals("Profile"))
            Navigation.findNavController(view).navigate(R.id.action_settings_to_ui_Profile);
        else
            Navigation.findNavController(view).navigate(R.id.action_settings_to_ui_Home);
    }

    private void animateNavDrawer(){

        NavigationView nav_drawer = getActivity().findViewById(R.id.nav_drawer);
        int visi = nav_drawer.getVisibility();

        if(visi == 0){
            if(navFlag == 1){
                navFlag = 0;
                MyAnimation animation = new MyAnimation();
                animation.navDrawerAnimation(nav_drawer);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        navFlag = 1;
                    }
                }, 200);

            }
        }

    }

// Set Data on Views
    // it can get the user data from firebase and send the users object to setDataOnViews method
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


// Edit number
    // it can Navigate user to EditUi
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

// Verify Emial
    // send verification email
    public void sendVerificationEmail(){
        Toast.makeText((Main_UserInterface) requireActivity(), "we send a verification link on your registeres mail", Toast.LENGTH_SHORT).show();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("sendEmail", "Email sent." + task.getResult());
                        }
                    }
                });
    }
    // check verifiedOrNot
    public void checkVerifiedOrNot(){
        firebaseAuth.getInstance().getCurrentUser().reload();

        boolean verify = user.isEmailVerified();
        if(verify == true){
            tv_settings_verify.setVisibility(View.GONE);
            iv_settingsVerify.setVisibility(View.VISIBLE);
        }
    }
// give Feedback
    private void giveFeedBack(){
        Toast.makeText((Main_UserInterface) requireActivity(), "Give your Valuable Feedback", Toast.LENGTH_SHORT).show();

        final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
// Rase a ticket
    private void raiseTicket(){

        Toast.makeText((Main_UserInterface) requireActivity(), "mail us for any Query!", Toast.LENGTH_SHORT).show();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "recharge2me.help@gmail.com", null));
        getActivity().startActivity(Intent.createChooser(emailIntent, null));

    }

// Tell a freind
    // It can fetch the link from firebase if any error occured then hardcoded link is pass through intent
    private void getLinkFromFirebase(){
        try {
            DocumentReference docRef = db.collection("Screen Dialog").document("playStore");

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    try {
                        String link = documentSnapshot.getString("Link");
                        onShareClicked(link);
                    }
                    catch (Exception e){
                        Log.d("exp", e.getMessage());
                        onShareClicked("null");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    onShareClicked("null");
                }
            });
        }
        catch (Exception e){
            onShareClicked("null");
        }
    } // Tell a freind
    // play store link
    private void onShareClicked(String link) {

        cL_tellAFreind.setEnabled(true);
        cL_tellAFreind.setClickable(true);


        if(link.equals("null")){
            link = "https://play.google.com/store/apps/details?id=com.recharge2mePlay.recharge2me";
        }

        Uri uri = Uri.parse(link);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link.toString());
        intent.putExtra(Intent.EXTRA_TITLE, "Recharge2me");

        startActivity(Intent.createChooser(intent, "Share Link"));
    }

}