package com.recharge2mePlay.recharge2me.recharge.ui.adapters;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.recharge2mePlay.recharge2me.R;

import java.util.List;

import com.recharge2mePlay.recharge2me.recharge.models.RecTypeData;
import com.recharge2mePlay.recharge2me.recharge.models.RecTypeFTT;
import com.recharge2mePlay.recharge2me.recharge.models.RecTypeRMG;
import com.recharge2mePlay.recharge2me.recharge.models.RecTypeSPL;
import com.recharge2mePlay.recharge2me.recharge.models.RecTypeTUP;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.myViewHolder> {

    List<RecTypeSPL> spl;
    List <RecTypeData> data;
    List<RecTypeFTT> ftt;
    List<RecTypeTUP> tup;
    List<RecTypeRMG> rmg;

    Context context;

    public PlanAdapter(List<RecTypeSPL> spl, List<RecTypeData> data, List<RecTypeFTT> ftt,
                       List<RecTypeTUP> tup, List<RecTypeRMG> rmg, Context context) {
        this.spl = spl;
        this.data = data;
        this.ftt = ftt;
        this.tup = tup;
        this.rmg = rmg;

        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.plan_card, parent, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

//        recType_SPL spl1 = spl[position];
//        holder.tv_planDetails.setText(spl);

        String amount = "";
        String validity = "";
        String details = "";

        RecTypeSPL splData;
        RecTypeData dataData;
        RecTypeFTT fttData;
        RecTypeTUP tupData;
        RecTypeRMG rmgData;


        if(spl != null){
            splData = spl.get(position);

            holder.tv_planAmount.setText("₹" + splData.getAmount());
            holder.tv_panValidity.setText("Validity: " + splData.getValidity());
            holder.tv_planDetails.setText(splData.getDetail());
        }
        else if(data != null){
            dataData = data.get(position);

            holder.tv_planAmount.setText("₹" + dataData.getAmount());
            holder.tv_panValidity.setText("Validity: " + dataData.getValidity());
            holder.tv_planDetails.setText(dataData.getDetail());
        }
        else if(ftt != null){
            fttData = ftt.get(position);

            holder.tv_planAmount.setText("₹" + fttData.getAmount());
            holder.tv_panValidity.setText("Validity: " + fttData.getValidity());
            holder.tv_planDetails.setText(fttData.getDetail());
        }
        else if(tup != null){
            tupData = tup.get(position);

            holder.tv_planAmount.setText("₹" + tupData.getAmount());
            holder.tv_panValidity.setText("Validity: " + tupData.getValidity());
            holder.tv_planDetails.setText(tupData.getDetail());
        }
        else if(rmg != null){
            rmgData = rmg.get(position);

            holder.tv_planAmount.setText("₹" + rmgData.getAmount());
            holder.tv_panValidity.setText("Validity: " + rmgData.getValidity());
            holder.tv_planDetails.setText(rmgData.getDetail());
        }
    }

    @Override
    public int getItemCount() {

        if(spl != null)
            return spl.size();
        else if(data != null)
            return data.size();
        else if(ftt != null)
            return ftt.size();
        else if(tup != null)
            return tup.size();
        else if(rmg != null)
            return rmg.size();
        else
            return 0;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

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

    // click Listner
    public interface planClickListner{
        public void onPlanClick(View view, int position, View btn);
    }

    public static class planRecyclerTouchListener implements RecyclerView.OnItemTouchListener{

            private planClickListner planClickListner;
            private GestureDetector gestureDetector;

            public planRecyclerTouchListener(Context context, final RecyclerView recycleView, planClickListner planClickListner){

                this.planClickListner = planClickListner;

                gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });

            }

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                try{
                    View view = rv.findChildViewUnder(e.getX(), e.getY());
                    View btn_select = view.findViewById(R.id.btn_planSelect);
                    if(view != null && planClickListner != null && gestureDetector.onTouchEvent(e))
                        planClickListner.onPlanClick(view, rv.getChildAdapterPosition(view), btn_select);
                }
                catch (Exception exception){
                    System.out.println(exception.getMessage());
                }

                return false;

            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
    }
}
