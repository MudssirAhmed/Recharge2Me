package recahrge.myAdapters;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
        ConstraintLayout constraintLayout;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_operatorName = itemView.findViewById(R.id.tv_operatorName);
            iv_operatorImage = itemView.findViewById(R.id.iv_operatorImage);
            constraintLayout = itemView.findViewById(R.id.operatorCard);

        }
    }

    public interface ClickListener{
        public void onClick(View view, int position);
    }

    public static class RecyclerViewListner implements RecyclerView.OnItemTouchListener{

        private ClickListener clickListener;
        private GestureDetector gestureDetector;

        public RecyclerViewListner(Context context, RecyclerView recyclerView, ClickListener clickListener){
            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                        @Override public boolean onSingleTapUp(  MotionEvent e){
                            return true;
                        }
                });
        }


        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            View view = rv.findChildViewUnder(e.getX(), e.getY());

            if(view != null && clickListener != null && gestureDetector.onTouchEvent(e))
                clickListener.onClick(view, rv.getChildAdapterPosition(view));

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


