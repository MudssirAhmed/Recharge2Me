package LogInSignIn_Entry;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recharge2me.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import custom_Loading_Dialog.LoadingDialog;


public class login_EnterEmailAndPassword extends Fragment {

    TextView tvLogIn_SignIn;
    View  view;
    Button btn_Login_Login;
    EditText et_LogIn_Email,et_LogIn_Pasword;

    // Loading Dialog:-
    LoadingDialog loadingDialog;

    // Firebase Fields:-
    private FirebaseAuth mAuth;

    public login_EnterEmailAndPassword() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login__enter_email_and_password, container, false);

        tvLogIn_SignIn = view.findViewById(R.id.tvlogIn_SignIn);
        btn_Login_Login = view.findViewById(R.id.btn_Login_Login);
        et_LogIn_Email = view.findViewById(R.id.et_LogIn_email);
        et_LogIn_Pasword = view.findViewById(R.id.et_LogIn_password);


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

        btn_Login_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDialog.startLoading();
                Login_Existing_user();


            }
        });



        return view;
    }

    private void Login_Existing_user() {

        mAuth.signInWithEmailAndPassword(et_LogIn_Email.getText().toString(),et_LogIn_Pasword.getText().toString())
                .addOnCompleteListener((EntryActivity) requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText((EntryActivity) requireActivity(), "LogIn...", Toast.LENGTH_SHORT).show();
                            loadingDialog.stopLoading();
                            gotoUiPage();
                        } else {
                            Toast.makeText((EntryActivity) requireActivity(), "Error! try again...", Toast.LENGTH_SHORT).show();
                            loadingDialog.stopLoading();
                        }
                    }
                });
    }

    private void gotoUiPage() {
        Navigation.findNavController(view).navigate(R.id.action_login_EnterEmailAndPassword_to_main_UserInterface);
    }

    private void gotoSignPage() {
        Navigation.findNavController(view).navigate(R.id.action_login_EnterEmailAndPassword_to_signin_crearte_account);
    }
}