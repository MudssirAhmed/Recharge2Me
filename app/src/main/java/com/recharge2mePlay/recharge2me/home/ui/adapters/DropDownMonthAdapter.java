package com.recharge2mePlay.recharge2me.home.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.recharge2mePlay.recharge2me.R;

import java.util.List;

public class DropDownMonthAdapter extends BaseAdapter {

    Context context;
    List<String> month;

    public DropDownMonthAdapter(Context context, List<String> month){
        this.context = context;
        this.month = month;
    }

    @Override
    public int getCount() {
        return month.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.month_drop_down, null, false);
        TextView tv_month = view.findViewById(R.id.tv_monthDrop_month);

        if(month.get(i).equals(" ")){
            tv_month.setVisibility(View.GONE);
        }
        tv_month.setText(month.get(i));


        return view;
    }
}
