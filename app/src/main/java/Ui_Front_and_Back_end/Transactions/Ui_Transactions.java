package Ui_Front_and_Back_end.Transactions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import Global.custom_Loading_Dialog.CustomToast;
import Ui_Front_and_Back_end.Adapters.DropDown_month;
import Ui_Front_and_Back_end.Adapters.TransactionAdapter;
import Ui_Front_and_Back_end.Main_UserInterface;
import recahrge.DataTypes.rechargeFirbase.Order;


public class Ui_Transactions extends Fragment{

    RecyclerView rv_uiTransactions;
    TransactionAdapter transactionAdapter;
    Spinner spinner_months;

    EditText et_transaction_searchNumber;

    ImageView iv_noTransactions;

    ProgressBar pbTransaction;

    // containers
    List<Order> orders;
    int touchFlag = 1;

    View view;

    // Custom
    CustomToast toast;

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

        // Custom
        toast = new CustomToast((Main_UserInterface) requireActivity());

        // ImageView
        iv_noTransactions = view.findViewById(R.id.iv_setting_noTransaction);

        // EditText
        et_transaction_searchNumber = view.findViewById(R.id.et_transaction_searchNumber);

        //ProgressBar
        pbTransaction = view.findViewById(R.id.pb_transactions);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // When text is changed on numberEditText
        et_transaction_searchNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setFilteredTransactionList_number(editable.toString());
            }
        });

        spinner_months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                TextView tv_spinner = view.findViewById(R.id.tv_monthDrop_month);

                if(tv_spinner.getText().toString().equals(" ")){
                    spinner_months.setBackground(getResources().getDrawable((R.drawable.placeholder_month)));
                }
                else {
                    spinner_months.setBackground(getResources().getDrawable((R.drawable.transaction_filter_month)));
                    String month = tv_spinner.getText().toString().trim();
                    setFilteredTransactionList_month(month.substring(0,3));
                    Toast.makeText((Main_UserInterface) requireActivity(), month.substring(0,3), Toast.LENGTH_SHORT).show();
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

        setDataOnDropDown();
        getRechargeData();

        return view;
    }

    private void getRechargeData(){


        Query query = db.collection("USERS").document(mAuth.getUid()).collection("Transactions")
                .orderBy("orderId", Query.Direction.DESCENDING).limit(25);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(queryDocumentSnapshots.isEmpty()){
                    iv_noTransactions.setVisibility(View.VISIBLE);
                    pbTransaction.setVisibility(View.GONE);
                    return;
                }

                try {
                    List<Order> list = new ArrayList<>();
                    for(QueryDocumentSnapshot q: queryDocumentSnapshots){
                        Order order = q.toObject(Order.class);
                        list.add(order);
                    }
                    pbTransaction.setVisibility(View.GONE);
                    setDataOnRecyclerViewe(list);
                }
                catch (Exception e){
                    toast.showToast("Error! " + e.getMessage());
                }
            }
        });
    }
    private void setDataOnRecyclerViewe(List<Order> list){

        orders = list;

        transactionAdapter = new TransactionAdapter((Main_UserInterface)requireActivity(), list, getActivity(), view, "Transactions");
        rv_uiTransactions.setAdapter(transactionAdapter);
        rv_uiTransactions.setLayoutManager(new LinearLayoutManager((Main_UserInterface) requireActivity()));

    }

    // Filter transactions list
    private void setFilteredTransactionList_number(String text){
        List<Order> filteredList = new ArrayList<>();

        for(Order order: orders){
            String number = order.getNumber();
            if(number.toLowerCase().contains(text)){
                filteredList.add(order);
            }
        }

        if(filteredList.isEmpty()){
            iv_noTransactions.setVisibility(View.VISIBLE);
            rv_uiTransactions.setVisibility(View.GONE);
        }
        else {
            iv_noTransactions.setVisibility(View.GONE);
            rv_uiTransactions.setVisibility(View.VISIBLE);
            transactionAdapter.setFilteredList(filteredList);
        }
    }
    private void setFilteredTransactionList_month(String text){
        List<Order> filteredList = new ArrayList<>();

        for(Order order: orders){
//            String date = order.getDate();
//            if(date.toLowerCase().contains(text.toLowerCase())){
//                filteredList.add(order);
//            }
        }

        if(filteredList.isEmpty()){
            iv_noTransactions.setVisibility(View.VISIBLE);
            rv_uiTransactions.setVisibility(View.GONE);
        }
        else {
            iv_noTransactions.setVisibility(View.GONE);
            rv_uiTransactions.setVisibility(View.VISIBLE);
            transactionAdapter.setFilteredList(filteredList);
        }

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