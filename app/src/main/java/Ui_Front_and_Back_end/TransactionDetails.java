package Ui_Front_and_Back_end;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.recharge2mePlay.recharge2me.R;


public class TransactionDetails extends Fragment {

    View view;

    ImageView iv_cross;
    String fromHome, fromTransaction;
    public TransactionDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transaction_details, container, false);

        // ImageView
        iv_cross = view.findViewById(R.id.ic_transactionDet_cross);

        fromHome = TransactionDetailsArgs.fromBundle(getArguments()).getFromHome();
        fromTransaction = TransactionDetailsArgs.fromBundle(getArguments()).getFromTransactions();

        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoHomeUi();
            }
        });

        return view;
    }
    private void gotoHomeUi(){
        if(fromHome.equals("Home")){
            Navigation.findNavController(view).navigate(R.id.action_transactionDetails_to_ui_Home);
        }
        else if(fromTransaction.equals("Transactions")){
            Navigation.findNavController(view).navigate(R.id.action_transactionDetails_to_ui_Transactions);
        }
    }
}