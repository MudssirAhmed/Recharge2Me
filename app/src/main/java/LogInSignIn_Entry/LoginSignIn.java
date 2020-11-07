package LogInSignIn_Entry;

import android.content.Intent;
import android.graphics.Color;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.recharge2me.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import custom_Loading_Dialog.LoadingDialog;


public class LoginSignIn extends Fragment {

    Button btnLogIn,btnSignIn,btnGoogle;
    View view;
    LoadingDialog loadingDialog;

    // This is a request_code for google signIn(onActivityResult) method
    final int RC_SIGN_IN = 1;
    String userName,userEmail,userId;
    Uri userGooglePhotoUri;

    // Firebase Fields:-
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    public LoginSignIn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_login_sign_in, container, false);

        btnGoogle = view.findViewById(R.id.btnGoogle);
        btnLogIn = view.findViewById(R.id.btnLogIn);
        btnSignIn = view.findViewById(R.id.btnSignIn);

        // This is Custom Loading Dialog
         loadingDialog = new LoadingDialog((EntryActivity) requireActivity());

        //firebase Fields:-
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        // Functions :-
        setGoogleTextColor();


        // Listeners:-
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginPage(); // This is a  fun. which is use to go to LoginPage.
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignPage();
            }
        });

        // This is an onClick listner on btnGoogle Button
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginPage_FromGooleSignIn();
            }
        });


        return view;
    }



    // These all Five fun. are for Google SignIn :-
    private void goToLoginPage_FromGooleSignIn() {

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient((EntryActivity) requireActivity(), gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            loadingDialog.startLoading();
            FirebaseGoogleAuth(account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText((EntryActivity) requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void FirebaseGoogleAuth(GoogleSignInAccount account) {
        // Signed in successfully, show authenticated UI.
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((EntryActivity) requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            addSignInDataInFireStore();


                        } else {
                            Toast.makeText((EntryActivity) requireActivity(), "error! try Again...", Toast.LENGTH_SHORT).show();
                            loadingDialog.stopLoading();
                        }
                    }
                });
    }
    private void addSignInDataInFireStore(){



        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            userName = acct.getDisplayName();
            userEmail = acct.getEmail();
            userId = acct.getId();
            userGooglePhotoUri = acct.getPhotoUrl();
        }


        CreateAccount_userDetails userDetails = new CreateAccount_userDetails(userName,userEmail);
        Google_User_Details googleDetails = new Google_User_Details(userId,userGooglePhotoUri.toString());

        Map<String,Object> data = new HashMap<>();
        data.put("userDetails",userDetails);
        data.put("Google",googleDetails);


        db.collection("USERS")
                .document(mAuth.getUid())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText((EntryActivity) requireActivity(), "LogIn...", Toast.LENGTH_SHORT).show();
                        loadingDialog.stopLoading();
                        Navigation.findNavController(view).navigate(R.id.action_loginSignIn_to_main_UserInterface);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText((EntryActivity) requireActivity(), "Error!..." + e.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingDialog.stopLoading();
                    }
                });



    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if(!user.equals(null)){
            Navigation.findNavController(view).navigate(R.id.action_loginSignIn_to_main_UserInterface);
        }
    }

    private void goToSignPage() {
        Navigation.findNavController(view).navigate(R.id.action_loginSignIn_to_signin_crearte_account);
    }
    // This is a  fun. which is use to go to LoginPage, setOnClickLister to btnLogIn.
    private void goToLoginPage() {
        Navigation.findNavController(view).navigate(R.id.action_loginSignIn_to_login_EnterEmailAndPassword);
    }
    // This is Google font color function :-
    private void setGoogleTextColor() {

        String text = "<font color=#4285F4>G</font><font color=#DB4437>o</font><font color=#F4B400>o</font><font color=#4285F4>g</font><font color=#0F9D58>l</font><font color=#DB4437>e</font>";
        btnGoogle.setText(Html.fromHtml(text));
    }


}