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

import com.recharge2mePlay.recharge2me.utils.dialogs.CustomToast;
import recahrge.DataTypes.planDataTypes.PlanData;
import recahrge.DataTypes.planDataTypes.recType_SPL;
import recahrge.myAdapters.PlanAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.recharge2mePlay.recharge2me.webservices.JsonConvertor;

public class Spl_Fragment extends Fragment {

    View v;
    RecyclerView rv_Plan;
    PlanAdapter planAdapter_;
    TextView tv_spl_warning;

    getRecahrgePlan activity;

    CustomToast toast;

    Retrofit retrofit;
    JsonConvertor jsonConvertor;

    public Spl_Fragment() {
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

        v = inflater.inflate(R.layout.spl_fragment, container, false);


        rv_Plan = v.findViewById(R.id.rv_Plan_SPL);
        tv_spl_warning = v.findViewById(R.id.tv_spl_warning);
        toast = new CustomToast(getActivity());

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


        Call<PlanData> call = jsonConvertor.getRechargePlan(
                "json", getString(R.string.token), "SPL", activity.getCircleId(), activity.getOpCode());

        call.enqueue(new Callback<PlanData>() {
            @Override
            public void onResponse(Call<PlanData> call, Response<PlanData> response) {
                if(!response.isSuccessful()){
                    tv_spl_warning.setText(response.code());
                    activity.hideProgressBar();
                    return;
                }
                try {
                    PlanData planData1 = response.body();
                    String resText = planData1.getResText();

                    PlanData.Data data = planData1.getData();

                    List<recType_SPL> spl = data.getSPL();

                    if(spl == null) {
                        tv_spl_warning.setText(resText);
                        activity.hideProgressBar();
                    }
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
                catch (Exception e){

                }


            }

            @Override
            public void onFailure(Call<PlanData> call, Throwable t) {
                tv_spl_warning.setText(t.getMessage());
                activity.hideProgressBar();
            }
        });

    }

    public void setRecyclerView(List<recType_SPL> spls){

        try {
            planAdapter_ = new PlanAdapter(spls,null, null, null, null, (getRecahrgePlan) requireActivity());

            rv_Plan.setAdapter(planAdapter_);
            rv_Plan.setLayoutManager(new LinearLayoutManager((getRecahrgePlan) requireActivity()));
            activity.hideProgressBar();
        }
        catch (Exception e){
        }


    }

}
