package recahrge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recharge2me.R;

import java.util.ArrayList;
import java.util.List;

import recahrge.DataTypes.PlanData;
import recahrge.DataTypes.recType_Data;
import recahrge.DataTypes.recType_SPL;
import recahrge.myAdapters.PlanAdapter_DATA;
import recahrge.myAdapters.PlanAdapter_SPL;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import Retrofit.JsonConvertor;
import retrofit2.converter.gson.GsonConverterFactory;


public class Data_Fragment extends Fragment {

    View v;
    RecyclerView rv_planData;
    PlanAdapter_DATA planAdapter_Data;
    PlanAdapter_SPL planAdapter_spl;
    TextView tv_planData_Warning;

    private Retrofit retrofit;
    JsonConvertor  jsonConvertor;


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
                .baseUrl("http://api.rechapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonConvertor = retrofit.create(JsonConvertor.class);

        getDataPlanDetails();

        return v;
    }

    private void getDataPlanDetails(){

        Call<PlanData> call = jsonConvertor
                    .getRechargePlan("json", getString(R.string.token), "DATA", "11", "10");

        call.enqueue(new Callback<PlanData>() {
            @Override
            public void onResponse(Call<PlanData> call, Response<PlanData> response) {

                if(!response.isSuccessful()){
                    tv_planData_Warning.setText(response.code());
                    return;
                }

                PlanData planData = response.body();

                PlanData.Data data = planData.getData();

                List<recType_Data> recType_data = data.getDATA();

                setOnRecyclerView(recType_data);
            }

            @Override
            public void onFailure(Call<PlanData> call, Throwable throwable) {
                tv_planData_Warning.setText(throwable.getMessage());
            }
        });

    } // End of getDataPlanDetails method;

    private void setOnRecyclerView(List<recType_Data> data){

        List<recType_SPL> spls = new ArrayList<>();

        for(recType_Data dats : data){

            recType_SPL spl = new recType_SPL(dats.getAmount(), dats.getDetail(), dats.getValidity(), dats.getTalktime());

            spls.add(spl);

        }

        planAdapter_spl = new PlanAdapter_SPL(spls, (getRecahrgePlan) requireActivity());
        planAdapter_Data = new PlanAdapter_DATA(data, (getRecahrgePlan) requireActivity());
        rv_planData.setAdapter(planAdapter_Data);
        rv_planData.setLayoutManager(new LinearLayoutManager((getRecahrgePlan) requireActivity()));

    }// End of setOnRecyclerView method;
}
