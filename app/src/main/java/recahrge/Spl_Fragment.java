package recahrge;

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

import com.example.recharge2me.R;

import java.util.List;

import recahrge.DataTypes.PlanData;
import recahrge.DataTypes.recType_SPL;
import recahrge.myAdapters.PlanAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import Retrofit.JsonConvertor;

public class Spl_Fragment extends Fragment {

    View v;
    RecyclerView rv_Plan;
    PlanAdapter planAdapter_;
    TextView tv_spl_warning;

    Retrofit retrofit;
    JsonConvertor jsonConvertor;

    public Spl_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.spl_fragment, container, false);

        rv_Plan = v.findViewById(R.id.rv_Plan_SPL);
        tv_spl_warning = v.findViewById(R.id.tv_spl_warning);


        // Init Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl_rechApi))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Init jsonConverter
        jsonConvertor = retrofit.create(JsonConvertor.class);



        getRecahrgePlanDetails();

        return v;

    }

    public void getRecahrgePlanDetails(){

        getRecahrgePlan activity = (getRecahrgePlan) getActivity();
//        tv_spl_warning.setText("abc: "+activity.getCircleId());

        Call<PlanData> call = jsonConvertor.getRechargePlan(
                "json", getString(R.string.token), "SPL", activity.getCircleId(), activity.getOpCode());

        call.enqueue(new Callback<PlanData>() {
            @Override
            public void onResponse(Call<PlanData> call, Response<PlanData> response) {
                if(!response.isSuccessful()){
                    tv_spl_warning.setText(response.code());
                    return;
                }

                PlanData planData1 = response.body();
                String resText = planData1.getResText();

                PlanData.Data data = planData1.getData();

                List<recType_SPL> spl = data.getSPL();

                if(spl == null)
                    tv_spl_warning.setText(resText);
                else
                    setRecyclerView(spl);

                rv_Plan.addOnItemTouchListener(new PlanAdapter.planRecyclerTouchListener((getRecahrgePlan) requireActivity(),
                        rv_Plan, new PlanAdapter.planClickListner() {
                    @Override
                    public void onPlanClick(View view, int position, View v) {

                        Animation animation = AnimationUtils.loadAnimation((getRecahrgePlan) requireActivity(), R.anim.click);
                        view.startAnimation(animation);

                        getRecahrgePlan activity = (getRecahrgePlan) getActivity();
                        recType_SPL sendSpl = spl.get(position);
                        activity.getRecahrgePlan(sendSpl.getAmount(), sendSpl.getValidity(), sendSpl.getDetail());

                        activity.sendPlanData();
                    }
                }));

            }

            @Override
            public void onFailure(Call<PlanData> call, Throwable t) {

                tv_spl_warning.setText(t.getMessage());
            }
        });

    }

    public void setRecyclerView(List<recType_SPL> spls){

        planAdapter_ = new PlanAdapter(spls,null, null, null, null, (getRecahrgePlan) requireActivity());

        rv_Plan.setAdapter(planAdapter_);
        rv_Plan.setLayoutManager(new LinearLayoutManager((getRecahrgePlan) requireActivity()));

    }

}
