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

import recahrge.DataTypes.recType_SPL;

public class PlanAdapter_SPL extends RecyclerView.Adapter<PlanAdapter_SPL.myViewHolder> {

    List<recType_SPL> spl;
    Context context;

    public PlanAdapter_SPL(List<recType_SPL> spl, Context context) {
        this.spl = spl;
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

        recType_SPL data = spl.get(position);

        holder.tv_planAmount.setText("₹" + data.getAmount());
        holder.tv_panValidity.setText("Validity: " + data.getValidity());
        holder.tv_planDetails.setText(data.getDetail());


    }

    @Override
    public int getItemCount() {
        return spl.size();
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
