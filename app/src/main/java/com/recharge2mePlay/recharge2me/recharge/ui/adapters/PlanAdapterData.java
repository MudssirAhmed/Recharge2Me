package com.recharge2mePlay.recharge2me.recharge.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.recharge2mePlay.recharge2me.R;

import java.util.List;

import com.recharge2mePlay.recharge2me.recharge.models.RecTypeData;

public class PlanAdapterData extends RecyclerView.Adapter<PlanAdapterData.myViewHolder> {

    List<RecTypeData> data;
    Context context;

    public PlanAdapterData(List<RecTypeData> data, Context context){
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.plan_card, viewGroup, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int i) {


        String amount = "";
        String validity = "";
        String details = "";

        RecTypeData data1 = data.get(i);

        holder.tv_planAmount.setText("₹" + data1.getAmount());
        holder.tv_panValidity.setText("Validity: " + data1.getValidity());
        holder.tv_planDetails.setText(data1.getDetail());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView tv_planDetails;
        TextView tv_planAmount;
        TextView tv_panValidity;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_planAmount = itemView.findViewById(R.id.tv_planAmount);
            tv_planDetails = itemView.findViewById(R.id.tv_planDetails);
            tv_panValidity = itemView.findViewById(R.id.tv_planValidity);

        }
    }
}
