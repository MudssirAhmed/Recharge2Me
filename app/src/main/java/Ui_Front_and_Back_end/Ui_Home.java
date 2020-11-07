package Ui_Front_and_Back_end;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recharge2me.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import LogInSignIn_Entry.EntryActivity;
import LogInSignIn_Entry.LoginSignIn;

public class Ui_Home extends Fragment {

    View view;
    TextView tv_Home_Transacyion;
    ImageView iv_prePaid, iv_postPaid;

    public Ui_Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_ui__home, container, false);

        tv_Home_Transacyion = view.findViewById(R.id.tv_Home_Transactions);

        iv_postPaid = view.findViewById(R.id.iv_postPaid);
        iv_prePaid = view.findViewById(R.id.iv_prePaid);

        tv_Home_Transacyion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutFromGoogle();
            }
        });

        iv_prePaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepaid();
            }
        });

        iv_postPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postpaid();
            }
        });

        return view;
    }

    public void signOutFromGoogle()
    {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient((Main_UserInterface) requireActivity(), gso);

        // This code clears which account is connected to the app. To sign in again, the user must choose their account again.
        mGoogleSignInClient.signOut()
                .addOnCompleteListener((Main_UserInterface) requireActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // It will go back on LogIn-SignIn Page.
                        Intent intent = new Intent((Main_UserInterface) requireActivity(), EntryActivity.class);
                        startActivity(intent);

                        Toast.makeText((Main_UserInterface) requireActivity(), "You are Logged Out...", Toast.LENGTH_SHORT).show();
                    }
                });


    } // End of signOutFromGoogle method;

    private void prepaid(){
        Navigation.findNavController(view).navigate(R.id.action_ui_Home_to_recahrge_ui);

    }// End of prePaid method;

    private void postpaid(){
        Toast.makeText((Main_UserInterface) requireActivity(), "Postpaid", Toast.LENGTH_SHORT).show();
    } // End of postpaid method;
}