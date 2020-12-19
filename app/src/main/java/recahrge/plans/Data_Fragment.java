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
import recahrge.DataTypes.planDataTypes.recType_Data;
import recahrge.myAdapters.PlanAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import Retrofit.JsonConvertor;
import retrofit2.converter.gson.GsonConverterFactory;


public class Data_Fragment extends Fragment {

    View v;
    RecyclerView rv_planData;

    PlanAdapter planAdapter_;
    TextView tv_planData_Warning;

    getRecahrgePlan activity;

    private Retrofit retrofit;
    JsonConvertor  jsonConvertor;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (getRecahrgePlan) getActivity();
        activity.showProgressBar();
    }

    public Data_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.data_fragment, container, false);

        rv_planData = v.findViewById(R.id.rv_PlanData);
        tv_planData_Warning = v.findViewById(R.id.tv_planData_Warning);


        // Init Retroifit
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl_rechApi))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonConvertor = retrofit.create(JsonConvertor.class);

        getDataPlanDetails();

        return v;
    }

    private void getDataPlanDetails(){


        Call<PlanData> call = jsonConvertor
                    .getRechargePlan("json", getString(R.string.token), "DATA", activity.getCircleId(), activity.getOpCode());

        call.enqueue(new Callback<PlanData>() {
            @Override
            public void onResponse(Call<PlanData> call, Response<PlanData> response) {

                if(!response.isSuccessful()){
                    tv_planData_Warning.setText(response.code());
                    activity.hideProgressBar();
                    return;
                }

                PlanData planData = response.body();
                String resText = planData.getResText();

                PlanData.Data data = planData.getData();

                List<recType_Data> recType_data = data.getDATA();

//                tv_planData_Warning.setText(resText);

                if (recType_data == null) {
                    tv_planData_Warning.setText(resText);
                    activity.hideProgressBar();
                }
                else
                    setOnRecyclerView(recType_data);
                
                rv_planData.addOnItemTouchListener(new PlanAdapter.planRecyclerTouchListener((getRecahrgePlan) requireActivity(), 
                        rv_planData, new PlanAdapter.planClickListner() {
                    @Override
                    public void onPlanClick(View view, int position, View btn) {
                        Context context;
                        Animation animation = AnimationUtils.loadAnimation((getRecahrgePlan) requireActivity(), R.anim.click );
                        view.startAnimation(animation);

                        recType_Data sendData = recType_data.get(position);

                        getRecahrgePlan activity = (getRecahrgePlan) getActivity();
                        activity.getRecahrgePlan(sendData.getAmount(), sendData.getValidity(), sendData.getDetail());
                        activity.sendPlanData();

                    }
                }));
            }

            @Override
            public void onFailure(Call<PlanData> call, Throwable throwable) {
                tv_planData_Warning.setText(throwable.getMessage());
                activity.hideProgressBar();
            }
        });

    } // End of getDataPlanDetails method;

    private void setOnRecyclerView(List<recType_Data> data){
        
        planAdapter_ = new
                PlanAdapter(null, data, null, null, null,  (getRecahrgePlan) requireActivity());

        rv_planData.setAdapter(planAdapter_);
        rv_planData.setLayoutManager(new LinearLayoutManager((getRecahrgePlan) requireActivity()));
        activity.hideProgressBar();


    }// End of setOnRecyclerView method;
}
