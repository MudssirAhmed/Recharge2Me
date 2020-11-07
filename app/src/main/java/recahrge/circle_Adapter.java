package recahrge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recharge2me.R;

public class circle_Adapter extends RecyclerView.Adapter<circle_Adapter.myViewHolder> {

    Context context;
    String circle[];

    public circle_Adapter(Context context, String[] circle) {
        this.context = context;
        this.circle = circle;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.circle_card,parent, false);

        return new myViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.tv_circleName.setText(circle[position]);

    }

    @Override
    public int getItemCount() {
        return circle.length;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView tv_circleName;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_circleName = itemView.findViewById(R.id.tv_circleCardView);

        }
    }

}
