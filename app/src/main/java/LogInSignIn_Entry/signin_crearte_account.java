package LogInSignIn_Entry;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import custom_Loading_Dialog.LoadingDialog;

public class signin_crearte_account extends Fragment {

    View view;
    TextView tvSignIn_LogIn,tv_createAccount_exception,tv_CreateAccount_Reset;
    Button btn_Signin_CreateAccount;
    EditText et_createAccount_Name,et_createAccount_Email,et_createAccount_Password;
    CheckBox cb_createAcc_showPassword;

    // Loading Dialog
    LoadingDialog loadingDialog;

    //Firebase Fields:-
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public signin_crearte_account() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_signin_crearte_account, container, false);

        tvSignIn_LogIn = view.findViewById(R.id.tvSignIn_LogIn);
        tv_CreateAccount_Reset = view.findViewById(R.id.tv_CreateAccount_Reset);
        btn_Signin_CreateAccount = view.findViewById(R.id.btn_Signin_CreateAccount);
        cb_createAcc_showPassword = view.findViewById(R.id.cb_crearreAcc_showPassword);

       //Loading Dialog
        loadingDialog = new LoadingDialog((EntryActivity) requireActivity());

        // SignIn Details:-
        et_createAccount_Email = view.findViewById(R.id.et_LogIn_email);
        et_createAccount_Name = view.findViewById(R.id.et_createAccount_Name);
        et_createAccount_Password = view.findViewById(R.id.et_LogIn_password);
        tv_createAccount_exception = view.findViewById(R.id.tv_createAccount_excaption);



        //Firebase declaration:-
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();



        // It is an TextView by which when user tap on it then they go to LogIn page.
        tvSignIn_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLoginPage();
            }
        });

        btn_Signin_CreateAccount.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(et_createAccount_Name.getText().toString().isEmpty() || et_createAccount_Email.getText().toString().isEmpty()
                        || et_createAccount_Password.getText().toString().isEmpty()){
                    tv_createAccount_exception.setText("Please Enter All Fields!...");
                }else{
                    loadingDialog.startLoading();
                    createAccount();
                }
            }
        });

        tv_CreateAccount_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset_fields();
            }


        });



        cb_createAcc_showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_createAcc_showPassword.isChecked()){
                    et_createAccount_Password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else {
                    et_createAccount_Password.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        return view;
    } // End of onCreate()


    // This Function Reset Name, Email and Password fields:-
    private void Reset_fields() {
        et_createAccount_Name.setText(null);
        et_createAccount_Email.setText(null);
        et_createAccount_Password.setText(null);
    }




    // It is For Create a new Account.
    private void createAccount() {

        final String Email = et_createAccount_Email.getText().toString();
        final String Name = et_createAccount_Name.getText().toString();
        final String Password = et_createAccount_Password.getText().toString();


        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener((EntryActivity) requireActivity(), new OnCompleteListener<AuthResult>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            CreateAccount_userDetails userDetails =
                                    new CreateAccount_userDetails(Name,Email);

                            // update Google info as null bco'z user can't signIn with Google
                            Google_User_Details google = new Google_User_Details("UID", "PROFILE");


                            Map<String,Object> data = new HashMap<>();
                            data.put("user_details", userDetails);
                            data.put("Google", google);

                            // This method can Add userData in firstore.
                            db.collection("USERS")
                                    .document(mAuth.getUid())
                                    .set(data)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText((EntryActivity) requireActivity(), "LogIn...", Toast.LENGTH_SHORT).show();
                                            loadingDialog.stopLoading();
                                            Navigation.findNavController(view).navigate(R.id.action_signin_crearte_account_to_main_UserInterface);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            loadingDialog.stopLoading();
                                            Toast.makeText((EntryActivity) requireActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });


                        } else {
                            // If sign in fails, display a message to the user.
                            loadingDialog.stopLoading();
                            tv_createAccount_exception.setText("Enter Carefully!");
                        }
                        // ...
                    }
                });
    }




    // This will go to login Page from create Acc. page.
    private void gotoLoginPage() {
        Navigation.findNavController(view).navigate(R.id.action_signin_crearte_account_to_login_EnterEmailAndPassword);

    }
}