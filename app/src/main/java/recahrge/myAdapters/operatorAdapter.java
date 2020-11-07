package recahrge.myAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recharge2me.R;

public class operatorAdapter extends RecyclerView.Adapter<operatorAdapter.myViewHolder> {

    String operator[];
    int opImages[];
    Context context;

    public operatorAdapter(String[] operator, int[] opImages, Context context) {
        this.operator = operator;
        this.opImages = opImages;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.operator_card,parent,false);

        return new myViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.tv_operatorName.setText(operator[position]);
        holder.iv_operatorImage.setImageResource(opImages[position]);

    }

    @Override
    public int getItemCount() {
        return operator.length;
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView tv_operatorName;
        ImageView iv_operatorImage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_operatorName = itemView.findViewById(R.id.tv_operatorName);
            iv_operatorImage = itemView.findViewById(R.id.iv_operatorImage);

        }
    }
}
