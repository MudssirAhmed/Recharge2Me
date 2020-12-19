package recahrge.plans;

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

import recahrge.DataTypes.planDataTypes.PlanData;
import recahrge.DataTypes.planDataTypes.recType_TUP;
import recahrge.myAdapters.PlanAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import Retrofit.JsonConvertor;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tup_Fragment extends Fragment {

    View view;

    TextView tv_planTup_warningText;
    RecyclerView rv_planTup;

    getRecahrgePlan activity;

    Retrofit retrofit;
    JsonConvertor jsonConvertor;

    public Tup_Fragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (getRecahrgePlan) getActivity();
        activity.showProgressBar();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tup_fragment, container, false);

        tv_planTup_warningText = view.findViewById(R.id.tv_planTup_WarningText);
        rv_planTup = view.findViewById(R.id.rv_planTup);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl_rechApi))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonConvertor = retrofit.create(JsonConvertor.class);

        getPlanTup();

        return view;
    }// End of onCreteView;

    private void getPlanTup(){


        Call<PlanData> call = jsonConvertor
                .getRechargePlan("json", getString(R.string.token), "TUP", activity.getCircleId(), activity.getOpCode());

        call.enqueue(new Callback<PlanData>() {
            @Override
            public void onResponse(Call<PlanData> call, Response<PlanData> response) {
                if(!response.isSuccessful()){
                    tv_planTup_warningText.setText(response.code());
                    activity.hideProgressBar();
                    return;
                }
                PlanData planData = response.body();
                String resText = planData.getResText();

                PlanData.Data data = planData.getData();

                List<recType_TUP> tup = data.getTUP();


                if (tup == null) {
                    tv_planTup_warningText.setText(resText);
                    activity.hideProgressBar();
                }
                else
                    setDataOnRecyclerView(tup);

                rv_planTup.addOnItemTouchListener(new PlanAdapter.planRecyclerTouchListener((getRecahrgePlan) requireActivity(),
                        rv_planTup, new PlanAdapter.planClickListner() {
                    @Override
                    public void onPlanClick(View view, int position, View btn) {
                        Context context;
                        Animation animation = AnimationUtils.loadAnimation((getRecahrgePlan) requireActivity(), R.anim.click );
                        view.startAnimation(animation);

                        recType_TUP sendTup = tup.get(position);

                        getRecahrgePlan activity = (getRecahrgePlan) getActivity();
                        activity.getRecahrgePlan(sendTup.getAmount(), sendTup.getValidity(), sendTup.getDetail());
                        activity.sendPlanData();

                    }
                }));

            }

            @Override
            public void onFailure(Call<PlanData> call, Throwable throwable) {
                tv_planTup_warningText.setText(throwable.getMessage());
                activity.hideProgressBar();
            }
        });

    }// End of getPlanTup

    private void setDataOnRecyclerView(List<recType_TUP> tup){

        PlanAdapter planAdapter_ = new
                PlanAdapter(null, null, null, tup, null, (getRecahrgePlan) requireActivity());

        rv_planTup.setAdapter(planAdapter_);
        rv_planTup.setLayoutManager(new LinearLayoutManager((getRecahrgePlan) requireActivity()));
        activity.hideProgressBar();

    }// End of setDataOnRecyclerView
}
