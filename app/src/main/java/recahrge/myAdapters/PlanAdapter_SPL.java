package recahrge.myAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recharge2me.R;

import java.util.List;

import recahrge.DataTypes.recType_Data;
import recahrge.DataTypes.recType_FTT;
import recahrge.DataTypes.recType_RMG;
import recahrge.DataTypes.recType_SPL;
import recahrge.DataTypes.recType_TUP;

public class PlanAdapter_SPL extends RecyclerView.Adapter<PlanAdapter_SPL.myViewHolder> {

    List<recType_SPL> spl;
    List <recType_Data> data;
    List<recType_FTT> ftt;
    List<recType_TUP> tup;
    List<recType_RMG> rmg;

    Context context;

    public PlanAdapter_SPL(List<recType_SPL> spl, List<recType_Data> data, List<recType_FTT> ftt, List<recType_TUP> tup, List<recType_RMG> rmg, Context context) {
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

        recType_SPL splData;
        recType_Data dataData;
        recType_FTT fttData;
        recType_TUP tupData;
        recType_RMG rmgData;


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
}
