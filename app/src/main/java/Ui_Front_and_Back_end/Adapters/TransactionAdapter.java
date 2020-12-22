package Ui_Front_and_Back_end.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.recharge2mePlay.recharge2me.R;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.myViewHolder> {

    private List<String> list;
    private Context context;
    private Activity activity;
    int touchFlag = 1;


    public TransactionAdapter(Context context, List<String> list, Activity activity){
        this.context = context;
        this.list = list;
        this.activity = activity;
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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavigationView nav_drawer = activity.findViewById(R.id.nav_drawer);
                    int a = nav_drawer.getVisibility();
                    if(a == 0)
                        closeNavDrawer();
                }
            });
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
}
