package Ui_Front_and_Back_end;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recharge2mePlay.recharge2me.R;


public class Ui_Transactions extends Fragment {

    public Ui_Transactions() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ui__transactions, container, false);
    }
}