package com.recharge2mePlay.recharge2me.onboard.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.recharge2mePlay.recharge2me.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.recharge2mePlay.recharge2me.onboard.ui.activities.EntryActivity;

import com.recharge2mePlay.recharge2me.utils.dialogs.LoadingDialog;


public class LoginWithEmailFragment extends Fragment {

    TextView tvLogIn_SignIn,tv_Login_Reset,tv_LogIn_warning;
    TextView tv_LogIn_ForgotPassword;
    CheckBox cb_LogIn_showPasssword;

    View  view;
    Button btn_Login_Login;
    EditText et_LogIn_Email,et_LogIn_Pasword;

    // Loading Dialog:-
    LoadingDialog loadingDialog;

    // Firebase Fields:-
    private FirebaseAuth mAuth;

    public LoginWithEmailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login__enter_email_and_password, container, false);

        // Text-View
        tv_Login_Reset = view.findViewById(R.id.tv_LogIn_Reset);
        tv_LogIn_warning = view.findViewById(R.id.tv_LogIn_warning);
        tvLogIn_SignIn = view.findViewById(R.id.tvlogIn_SignIn);
        tv_LogIn_ForgotPassword = view.findViewById(R.id.tv_LogIn_ForgotPassword);
        cb_LogIn_showPasssword = view.findViewById(R.id.cb_logIn_showPassword);

        // Button
        btn_Login_Login = view.findViewById(R.id.btn_Login_Login);

        // Edit Text
        et_LogIn_Email = view.findViewById(R.id.et_LogIn_email);
        et_LogIn_Pasword = view.findViewById(R.id.et_LogIn_password);


        // First set Null in Email and Password fields
        et_LogIn_Email.setText(null);
        et_LogIn_Pasword.setText(null);


        loadingDialog = new LoadingDialog((EntryActivity) requireActivity());

        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // it is the textView(Button) in which when a user can tap on it.Then they go to create an account page.
        tvLogIn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSignPage();
            }
        });

        // This will reset Email and Password Fields
        tv_Login_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset_Fields();
            }
        });

        tv_LogIn_ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoForgotPassword();
            }
        });

        btn_Login_Login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if(et_LogIn_Email.getText().toString().isEmpty() || et_LogIn_Pasword.getText().toString().isEmpty()){
                    tv_LogIn_warning.setText("Please Enter All Fields!...");
                }
                else{
                    loadingDialog.startLoading();
                    Login_Existing_user();
                }
            }
        });

        cb_LogIn_showPasssword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_LogIn_showPasssword.isChecked()){
                    et_LogIn_Pasword.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else{
                    et_LogIn_Pasword.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        return view;
    }// End OnCreate()






    // This is Reset/Erase Email and Password fields
    private void Reset_Fields() {
        et_LogIn_Email.setText(null);
        et_LogIn_Pasword.setText(null);
    }


    // Login Existing User
    private void Login_Existing_user() {

        mAuth.signInWithEmailAndPassword(et_LogIn_Email.getText().toString(),et_LogIn_Pasword.getText().toString())
                .addOnCompleteListener((EntryActivity) requireActivity(), new OnCompleteListener<AuthResult>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText((EntryActivity) requireActivity(), "LogIn...", Toast.LENGTH_SHORT).show();
                            loadingDialog.stopLoading();
                            gotoUiPage();
                        } else {
                            Toast.makeText((EntryActivity) requireActivity(), "Error! try again...", Toast.LENGTH_SHORT).show();
                            tv_LogIn_warning.setText("Enter Carefully!...");
                            loadingDialog.stopLoading();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tv_LogIn_warning.setText(e.getMessage());
                    }
                });
    }



    private void gotoForgotPassword() {
        Navigation.findNavController(view).navigate(R.id.action_login_EnterEmailAndPassword_to_forgot_Password);
    }
    private void gotoUiPage() {
        Navigation.findNavController(view).navigate(R.id.action_login_EnterEmailAndPassword_to_main_UserInterface);
    }
    private void gotoSignPage() {
        Navigation.findNavController(view).navigate(R.id.action_login_EnterEmailAndPassword_to_signin_crearte_account);
    }
}