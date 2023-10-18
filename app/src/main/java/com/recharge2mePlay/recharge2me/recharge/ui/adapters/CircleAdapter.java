package com.recharge2mePlay.recharge2me.recharge.ui.adapters;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.recharge2mePlay.recharge2me.R;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.myViewHolder> {

    Context context;
    String circle[];

    public CircleAdapter(Context context, String[] circle) {
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
        ConstraintLayout constraintLayout;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_circleName = itemView.findViewById(R.id.tv_circleCardView);
            constraintLayout = itemView.findViewById(R.id.circleCard);

        }
    }



    // custom Interface
    // Click Listner
    public interface ClickListener{
        public void onClick(View view, int position);
//        public void onLongPress(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, ClickListener clicklistener){

            this.clicklistener = clicklistener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                // This is for longPress event
//                @Override
//                public void onLongPress(MotionEvent e) {
//
//                    View child = recycleView.findChildViewUnder(e.getX(),e.getY());
//
//                    if(child != null &&  clicklistener != null){
//
//                        clicklistener.onLongPress(child, recycleView.getChildAdapterPosition(child));
//
//                    }
//
//                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


} // End of Adapter Class
