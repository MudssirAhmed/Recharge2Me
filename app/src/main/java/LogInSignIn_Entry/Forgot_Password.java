package LogInSignIn_Entry;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recharge2me.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.local.QueryResult;

import java.util.Arrays;
import java.util.List;

import custom_Loading_Dialog.LoadingDialog;

public class Forgot_Password extends Fragment {

    View view;
    EditText et_forgot_email;
    Button btn_forgot_sendLink;
    TextView tv_forgot_warningText;

    // Loading Dialog:-
    LoadingDialog loadingDialog;

    // Firebase Fields:-
    FirebaseFirestore db;
    CollectionReference user;
    FirebaseAuth mAuth;

    public Forgot_Password() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forgot__password, container, false);

        // register fields:-
        et_forgot_email = view.findViewById(R.id.et_forgot_email);
        btn_forgot_sendLink = view.findViewById(R.id.btn_forgot_sendLink);
        tv_forgot_warningText = view.findViewById(R.id.tv_forgot_warningText);


        // register Loading Dialog
        loadingDialog = new LoadingDialog((EntryActivity) requireActivity());

        // register firebase filds:-
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = db.collection("USERS");
        final Query query = user.whereEqualTo("userDetails","userDetails");




        btn_forgot_sendLink.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if(et_forgot_email.getText().toString().isEmpty()){
                    tv_forgot_warningText.setText("Please Enter all fields carefully!...");
                }
                else {
                    tv_forgot_warningText.setText(null);
                    tv_forgot_warningText.setTextColor(getResources().getColor(R.color.Warning_text));
                    fetchSignInMethodForEmail(et_forgot_email.getText().toString().trim());
                }
            }
        });


        return view;
    }


    private void fetchSignInMethodForEmail(final String email){
        loadingDialog.startLoading();

        // [START auth_differentiate_link]
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        try {
                            if (task.isSuccessful()) {

                                SignInMethodQueryResult result = task.getResult();
                                List<String> signInMethods = result.getSignInMethods();

                                if (signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
                                    // User can sign in with email/password
                                    sendPasswordResetEmail(email);
                                    return;
                                }
                                else if(signInMethods.contains(GoogleAuthProvider.GOOGLE_SIGN_IN_METHOD)){
                                    Toast.makeText((EntryActivity) requireActivity(), "You Login as Google!...", Toast.LENGTH_SHORT).show();
                                    tv_forgot_warningText.setText("Please goto LogIn page and try goole login...");
                                    loadingDialog.stopLoading();
                                    return;
                                }
                            }

                            tv_forgot_warningText.setText("Please provide valid Email or create account.");
                            Toast.makeText((EntryActivity) requireActivity(),
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            loadingDialog.stopLoading();
                        }
                        catch (Exception e){
                            tv_forgot_warningText.setText("Please provide valid Email or create account.");
                            Toast.makeText((EntryActivity) requireActivity(), "Error! try again...", Toast.LENGTH_SHORT).show();
                            loadingDialog.stopLoading();
                        }
                    }
                });// [END auth_differentiate_link]
    }

    private void sendPasswordResetEmail(String email){

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText((EntryActivity) requireActivity(), "send!..", Toast.LENGTH_SHORT).show();
                            tv_forgot_warningText.setTextColor(Color.parseColor("#00FD88"));
                            tv_forgot_warningText.setText("Please Check the register Email!...");
                            loadingDialog.stopLoading();
                            return;
                        }
                        Toast.makeText((EntryActivity) requireActivity(), "Error! Please try Again...", Toast.LENGTH_SHORT).show();
                        tv_forgot_warningText.setText("Error! Please Try Again...");

                    }
                });

    }
}