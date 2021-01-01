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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.recharge2mePlay.recharge2me.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Ui_Front_and_Back_end.Adapters.DropDown_month;
import Ui_Front_and_Back_end.Adapters.TransactionAdapter;
import recahrge.DataTypes.rechargeFirbase.Order;


public class Ui_Transactions extends Fragment{

    RecyclerView rv_uiTransactions;
    TransactionAdapter transactionAdapter;
    Spinner spinner_months;

    int touchFlag = 1;

    View view;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    public Ui_Transactions() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_ui__transactions, container, false);


        rv_uiTransactions = view.findViewById(R.id.rv_uiTransaction);
        spinner_months = view.findViewById(R.id.spinner_months);
        spinner_months.setPrompt("Select Month");

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        spinner_months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                TextView tv_spinner = view.findViewById(R.id.tv_monthDrop_month);
                if(tv_spinner.getText().toString().equals(" ")){
                    spinner_months.setBackground(getResources().getDrawable((R.drawable.placeholder_month)));
                }
                else {
                    spinner_months.setBackground(getResources().getDrawable((R.drawable.transaction_filter_month)));
                    Toast.makeText((Main_UserInterface) requireActivity(), tv_spinner.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rv_uiTransactions.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    animateNavDrawer();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    animateNavDrawer();
                }
                return false;
            }
        });

//        setDataOnDropDown();
//        setDataOnRecyclerViewe();
        getRechargeData();

        return view;
    }

    private void getRechargeData(){
        Log.i("uid:",mAuth.getUid() + "uid");
        CollectionReference colRef = db.collection("USERS").document(mAuth.getUid()).collection("Transactions");

        colRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Order> list = new ArrayList<>();
                for(QueryDocumentSnapshot q: queryDocumentSnapshots){
                    Order order = q.toObject(Order.class);
                    list.add(order);

//                    Log.i("Documnet", "Data: " + q.toObject(Order.class));
                }

                setDataOnRecyclerViewe(list);
            }
        });
    }

    private void setDataOnDropDown(){
        List<String> month = new ArrayList<>();
        month.add(" ");
        month.add("January");
        month.add("February");
        month.add("March");
        month.add("April");
        month.add("May");
        month.add("June");
        month.add("July");
        month.add("August");
        month.add("September");
        month.add("Octuber");
        month.add("November");
        month.add("December");

        DropDown_month adapter = new DropDown_month((Main_UserInterface) requireActivity(), month);
        spinner_months.setAdapter(adapter);
    }
    public void setDataOnRecyclerViewe(List<Order> list){

        transactionAdapter = new TransactionAdapter((Main_UserInterface)requireActivity(), list, getActivity(), view, "Transactions");
        rv_uiTransactions.setAdapter(transactionAdapter);
        rv_uiTransactions.setLayoutManager(new LinearLayoutManager((Main_UserInterface) requireActivity()));
    }
    private void animateNavDrawer(){
        NavigationView nav_drawer = getActivity().findViewById(R.id.nav_drawer);

        int a = nav_drawer.getVisibility();
        if(a == 0){
            if(touchFlag == 1){
                touchFlag = 0;

                nav_drawer.animate()
                        .alpha(0f)
                        .setDuration(200L)
                        .translationXBy(-100f)
                        .setListener(null);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nav_drawer.setVisibility(View.INVISIBLE);
                        touchFlag = 1;
                    }
                }, 200);
            }
        }
    }

}