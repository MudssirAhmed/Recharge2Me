package Ui_Front_and_Back_end.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.recharge2mePlay.recharge2me.R;

import java.util.List;

import Ui_Front_and_Back_end.Edit.Edit_profile;
import Ui_Front_and_Back_end.Main_UserInterface;
import Ui_Front_and_Back_end.TransactionDetails;
import Ui_Front_and_Back_end.Ui_HomeDirections;
import Ui_Front_and_Back_end.Ui_TransactionsDirections;
import recahrge.DataTypes.Paye2All.Pay2All_recharge;
import recahrge.DataTypes.rechargeFirbase.Order;
import recahrge.DataTypes.rechargeFirbase.Pay2All_rechargeFirebase;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.myViewHolder> {

    private List<Order> list;
    private Context context;
    private Activity activity;
    private View view;
    private String check;
    int touchFlag = 1;


    public TransactionAdapter(Context context, List<Order> list, Activity activity, View view, String check){
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.view = view;
        this.check = check;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.transactions_card, parent, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        TextView tv_number = holder.itemView.findViewById(R.id.tv_trsCard_number);
        TextView tv_Amount = holder.itemView.findViewById(R.id.tv_trsCard_amount);
        TextView tv_Date = holder.itemView.findViewById(R.id.tv_trsCard_date);

        Order order = list.get(position);
        Pay2All_rechargeFirebase recharge = order.getRecharge();

        tv_number.setText(recharge.getNumber());
        tv_Amount.setText(recharge.getAmount());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigateToTransactionDetailsFragment(order.getOrderId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    private void closeNavDrawer(){

        NavigationView nav_drawer = activity.findViewById(R.id.nav_drawer);

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

    private void NavigateToTransactionDetailsFragment(String orderId){

        if(check.equals("Transactions")){

            Ui_TransactionsDirections.ActionUiTransactionsToTransactionDetails action =
                    Ui_TransactionsDirections.actionUiTransactionsToTransactionDetails();
            action.setOrderId(orderId);
            action.setFromTransactions("Transactions");

            Navigation.findNavController(view).navigate(action);
        }
        else if(check.equals("Home")){
            Ui_HomeDirections.ActionUiHomeToTransactionDetails action =
                    Ui_HomeDirections.actionUiHomeToTransactionDetails();
            action.setFromHome("Home");
            Navigation.findNavController(view).navigate(action);
        }

    }
}
