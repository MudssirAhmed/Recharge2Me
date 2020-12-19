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
import recahrge.DataTypes.planDataTypes.recType_RMG;
import recahrge.myAdapters.PlanAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import Retrofit.JsonConvertor;
import retrofit2.converter.gson.GsonConverterFactory;

public class Rmg_Fragment extends Fragment  {

    View view;

    TextView tv_planRmg_WarningText;
    RecyclerView rv_Rmg;

    getRecahrgePlan activity;

    Retrofit retrofit;
    JsonConvertor jsonConvertor;

    public Rmg_Fragment() {
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

                PlanData planData = response.body();
                String resText = planData.getResText();

                PlanData.Data data = planData.getData();

                List<recType_RMG> rmg = data.getRMG();


                if(rmg == null) {
                    tv_planRmg_WarningText.setText(resText);
                    activity.hideProgressBar();
                }
                else
                    setDataOnRecyclerView(rmg);

                rv_Rmg.addOnItemTouchListener(new PlanAdapter.planRecyclerTouchListener((getRecahrgePlan) requireActivity(),
                        rv_Rmg, new PlanAdapter.planClickListner() {
                    @Override
                    public void onPlanClick(View view, int position, View btn) {
                        Context context;
                        Animation animation = AnimationUtils.loadAnimation((getRecahrgePlan) requireActivity(), R.anim.click );
                        view.startAnimation(animation);

                        recType_RMG sendRmg = rmg.get(position);

                        getRecahrgePlan activity = (getRecahrgePlan) getActivity();
                        activity.getRecahrgePlan(sendRmg.getAmount(), sendRmg.getValidity(), sendRmg.getDetail());
                        activity.sendPlanData();

                    }
                }));

            }

            @Override
            public void onFailure(Call<PlanData> call, Throwable throwable) {
                tv_planRmg_WarningText.setText(throwable.getMessage());
                activity.hideProgressBar();
            }
        });

    }// end of getRmgData

    private void setDataOnRecyclerView(List<recType_RMG> rmg){

        PlanAdapter planAdapter_ = new
                PlanAdapter(null, null, null, null,rmg, (getRecahrgePlan) requireActivity());

        rv_Rmg.setAdapter(planAdapter_);
        rv_Rmg.setLayoutManager(new LinearLayoutManager((getRecahrgePlan) requireActivity()));
        activity.hideProgressBar();

    }// end of setDatOnRecyclerView
}
