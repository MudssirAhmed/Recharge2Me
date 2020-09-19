package LogInSignIn_Entry;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recharge2me.R;


public class login_EnterEmailAndPassword extends Fragment {

    TextView tvLogIn_SignIn;
    View  view;

    public login_EnterEmailAndPassword() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login__enter_email_and_password, container, false);

        tvLogIn_SignIn = view.findViewById(R.id.tvlogIn_SignIn);

        tvLogIn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSignPage();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.action_login_EnterEmailAndPassword_to_loginSignIn);
            }
        };



        return view;
    }

    private void gotoSignPage() {
        Navigation.findNavController(view).navigate(R.id.action_login_EnterEmailAndPassword_to_signin_crearte_account);
    }
}