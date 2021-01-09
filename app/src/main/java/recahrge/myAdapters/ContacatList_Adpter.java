package recahrge.myAdapters;

import android.content.Context;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.recharge2mePlay.recharge2me.R;

import java.util.ArrayList;
import java.util.List;

import recahrge.DataTypes.contactList_dataType.contact_type;
import recahrge.contactList.contactList_activity;

public class ContacatList_Adpter extends RecyclerView.Adapter<ContacatList_Adpter.myViewHolder> {

    ArrayList<contact_type> contactList = new ArrayList<>();
    Context context;

    public ContacatList_Adpter(ArrayList<contact_type> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.contact_card, parent, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        contact_type contact = contactList.get(position);

        holder.tv_contactList_number.setText(contact.getNumber());
        holder.tv_contactList_name.setText(contact.getName());


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView tv_contactList_name;
        TextView tv_contactList_number;
        ImageView iv_contactList_photo;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_contactList_name = itemView.findViewById(R.id.tv_contactList_name);
            tv_contactList_number = itemView.findViewById(R.id.tv_contactList_number);
            iv_contactList_photo = itemView.findViewById(R.id.iv_contactList_photo);

        }
    }

    public void setFilterList(ArrayList<contact_type> list){
        contactList = list;
        notifyDataSetChanged();
    }

    // onClickListener
    public interface onContactClick{
        public void onContactCardClick(View view, String number);
    }

    public static class contactRecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private onContactClick onClick;
        private GestureDetector gestureDetector;

        public contactRecyclerTouchListener(Context context, final RecyclerView recycleView, ContacatList_Adpter.onContactClick onContactClick){

            this.onClick = onContactClick;

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
                TextView tv_number  = view.findViewById(R.id.tv_contactList_number);

                if(view != null && onClick != null && gestureDetector.onTouchEvent(e))
                    onClick.onContactCardClick(view, tv_number.getText().toString().trim());
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
