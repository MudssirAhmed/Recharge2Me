package LogInSignIn_Entry;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recharge2me.R;

public class signin_crearte_account extends Fragment {

    View view;
    TextView tvSignIn_LogIn;
    public signin_crearte_account() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_signin_crearte_account, container, false);

        tvSignIn_LogIn = view.findViewById(R.id.tvSignIn_LogIn);

        tvSignIn_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLoginPage();
            }
        });

        return view;
    }

    private void gotoLoginPage() {
        Navigation.findNavController(view).navigate(R.id.action_signin_crearte_account_to_login_EnterEmailAndPassword);
    }
}