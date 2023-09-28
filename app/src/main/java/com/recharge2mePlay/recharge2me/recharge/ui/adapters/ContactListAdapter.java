package com.recharge2mePlay.recharge2me.recharge.ui.adapters;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.recharge2mePlay.recharge2me.R;

import java.util.ArrayList;

import com.recharge2mePlay.recharge2me.recharge.models.ContactType;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.myViewHolder> {

    ArrayList<ContactType> contactList = new ArrayList<>();
    Context context;

    public ContactListAdapter(ArrayList<ContactType> contactList, Context context) {
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

        ContactType contact = contactList.get(position);

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

    public void setFilterList(ArrayList<ContactType> list){
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

        public contactRecyclerTouchListener(Context context, final RecyclerView recycleView, ContactListAdapter.onContactClick onContactClick){

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
