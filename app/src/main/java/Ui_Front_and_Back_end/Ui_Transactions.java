package Ui_Front_and_Back_end;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.recharge2mePlay.recharge2me.R;

import java.util.ArrayList;
import java.util.List;

import Ui_Front_and_Back_end.Adapters.TransactionAdapter;


public class Ui_Transactions extends Fragment {

    RecyclerView rv_uiTransactions;
    TransactionAdapter transactionAdapter;

    View view;

    public Ui_Transactions() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_ui__transactions, container, false);


        List<String> list = new ArrayList<>();
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");

        rv_uiTransactions = view.findViewById(R.id.rv_uiTransaction);
        transactionAdapter = new TransactionAdapter((Main_UserInterface)requireActivity(), list, getActivity());
        rv_uiTransactions.setAdapter(transactionAdapter);
        rv_uiTransactions.setLayoutManager(new LinearLayoutManager((Main_UserInterface) requireActivity()));

        return view;
    }


}