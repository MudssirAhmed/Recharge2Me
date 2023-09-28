package com.recharge2mePlay.recharge2me.recharge.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.recharge2mePlay.recharge2me.R;

import java.util.List;

import com.recharge2mePlay.recharge2me.recharge.models.PlanData;
import com.recharge2mePlay.recharge2me.recharge.models.RecTypeRMG;
import com.recharge2mePlay.recharge2me.recharge.ui.activities.GetRechargePlanActivity;
import com.recharge2mePlay.recharge2me.recharge.ui.adapters.PlanAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.recharge2mePlay.recharge2me.webservices.JsonConvertor;
import retrofit2.converter.gson.GsonConverterFactory;

public class RmgFragment extends Fragment  {

    View view;

    TextView tv_planRmg_WarningText;
    RecyclerView rv_Rmg;

    GetRechargePlanActivity activity;

    Retrofit retrofit;
    JsonConvertor jsonConvertor;

    public RmgFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (GetRechargePlanActivity) getActivity();
        activity.showProgressBar();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.rmg_fragment, container, false);

        tv_planRmg_WarningText = view.findViewById(R.id.tv_planRmg_warningText);
        rv_Rmg = view.findViewById(R.id.rv_planRmg);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl_rechApi))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonConvertor = retrofit.create(JsonConvertor.class);

        getRmgData();

        return view;
    }// end of onCreateView

    private void getRmgData(){


        Call<PlanData> call = jsonConvertor
                .getRechargePlan("json", getString(R.string.token), "RMG", activity.getCircleId(), activity.getOpCode());

        call.enqueue(new Callback<PlanData>() {
            @Override
            public void onResponse(Call<PlanData> call, Response<PlanData> response) {

                if(!response.isSuccessful()){
                    tv_planRmg_WarningText.setText(response.code());
                    activity.hideProgressBar();
                    return;
                }

                try {
                    PlanData planData = response.body();
                    String resText = planData.getResText();

                    PlanData.Data data = planData.getData();

                    List<RecTypeRMG> rmg = data.getRMG();


                    if(rmg == null) {
                        tv_planRmg_WarningText.setText(resText);
                        activity.hideProgressBar();
                    }
                    else
                        setDataOnRecyclerView(rmg);

                    rv_Rmg.addOnItemTouchListener(new PlanAdapter.planRecyclerTouchListener((GetRechargePlanActivity) requireActivity(),
                            rv_Rmg, new PlanAdapter.planClickListner() {
                        @Override
                        public void onPlanClick(View view, int position, View btn) {
                            Context context;
                            Animation animation = AnimationUtils.loadAnimation((GetRechargePlanActivity) requireActivity(), R.anim.click );
                            view.startAnimation(animation);

                            RecTypeRMG sendRmg = rmg.get(position);

                            GetRechargePlanActivity activity = (GetRechargePlanActivity) getActivity();
                            activity.getRecahrgePlan(sendRmg.getAmount(), sendRmg.getValidity(), sendRmg.getDetail());
                            activity.sendPlanData();

                        }
                    }));
                }
                catch (Exception e){

                }


            }

            @Override
            public void onFailure(Call<PlanData> call, Throwable throwable) {
                tv_planRmg_WarningText.setText(throwable.getMessage());
                activity.hideProgressBar();
            }
        });

    }// end of getRmgData

    private void setDataOnRecyclerView(List<RecTypeRMG> rmg){

        try{
            PlanAdapter planAdapter_ = new
                    PlanAdapter(null, null, null, null,rmg, (GetRechargePlanActivity) requireActivity());


            rv_Rmg.setAdapter(planAdapter_);
            rv_Rmg.setLayoutManager(new LinearLayoutManager((GetRechargePlanActivity) requireActivity()));
            activity.hideProgressBar();
        }
        catch (Exception e){

        }

    }// end of setDatOnRecyclerView
}
