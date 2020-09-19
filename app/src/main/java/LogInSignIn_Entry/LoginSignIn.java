package LogInSignIn_Entry;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.recharge2me.R;

public class LoginSignIn extends Fragment {

    Button btnLogIn,btnSignIn,btnGoogle,btnFacebook;
    View view;


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
        btnFacebook = view.findViewById(R.id.btnFacebook);


        // Functions :-
        setGoogleTextColor();

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



        return view;
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