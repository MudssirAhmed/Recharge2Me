package com.recharge2mePlay.recharge2me.recharge.ui.adapters;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.recharge2mePlay.recharge2me.R;

import java.util.ArrayList;

import com.recharge2mePlay.recharge2me.recharge.models.MobileRechargePlan;
import com.recharge2mePlay.recharge2me.recharge.models.MobileRechargePlanCategory;
import com.recharge2mePlay.recharge2me.recharge.models.RecTypeData;
import com.recharge2mePlay.recharge2me.recharge.models.RecTypeFTT;
import com.recharge2mePlay.recharge2me.recharge.models.RecTypeRMG;
import com.recharge2mePlay.recharge2me.recharge.models.RecTypeSPL;
import com.recharge2mePlay.recharge2me.recharge.models.RecTypeTUP;
import com.recharge2mePlay.recharge2me.utils.CardCallbacks;

public class MobileRechargeCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final ArrayList<MobileRechargePlanCategory> rechargeCategories;
    private final CardCallbacks cardCallbacks;

    public MobileRechargeCategoryAdapter(
            Context context,
            ArrayList<MobileRechargePlanCategory> rechargeCategories,
            CardCallbacks cardCallbacks
    ) {
        this.context = context;
        this.rechargeCategories = rechargeCategories;
        this.cardCallbacks = cardCallbacks;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.recharge_category_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CategoryViewHolder) holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return rechargeCategories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rvList;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            rvList = itemView.findViewById(R.id.rvList);
        }

        public void bind(int position) {
            MobileRechargePlanCategory category = rechargeCategories.get(position);

            MobileRechargePlansAdapter adapter = new MobileRechargePlansAdapter(position, category.getItems());
            rvList.setAdapter(adapter);
            rvList.setLayoutManager(new LinearLayoutManager(context));
        }

        private class MobileRechargePlansAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

            private final int categoryPosition;
            private final ArrayList<MobileRechargePlan> rechargePlans;

            public MobileRechargePlansAdapter(int categoryPosition, ArrayList<MobileRechargePlan> rechargePlans) {
                this.categoryPosition = categoryPosition;
                this.rechargePlans = rechargePlans;
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new RechargePlanViewHolder(LayoutInflater.from(context).inflate(R.layout.plan_card, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ((RechargePlanViewHolder) holder).bind(position);
            }

            @Override
            public int getItemCount() {
                return rechargePlans.size();
            }

            private class RechargePlanViewHolder extends RecyclerView.ViewHolder {

                private final TextView tv_planDetails;
                private final TextView tv_planAmount;
                private final TextView tv_panValidity;
                private final TextView tvPlanTalkTime;

                public RechargePlanViewHolder(@NonNull View itemView) {
                    super(itemView);
                    tv_planAmount = itemView.findViewById(R.id.tv_planAmount);
                    tv_planDetails = itemView.findViewById(R.id.tv_planDetails);
                    tv_panValidity = itemView.findViewById(R.id.tv_planValidity);
                    tvPlanTalkTime = itemView.findViewById(R.id.tvPlanTalkTime);
                }

                public void bind(int position) {
                    MobileRechargePlan rechargePlan = rechargePlans.get(position);

                    tv_planAmount.setText("₹" + rechargePlan.getAmount());
                    tv_panValidity.setText("Validity: " + rechargePlan.getValidity());
                    tvPlanTalkTime.setText("Talktime: " + rechargePlan.getValidity());
                    tv_planDetails.setText(rechargePlan.getBenefit());

                    itemView.setOnClickListener(v -> {
                        cardCallbacks.onCardItemClick(categoryPosition, position);
                    });
                }
            }
        }
    }
}
