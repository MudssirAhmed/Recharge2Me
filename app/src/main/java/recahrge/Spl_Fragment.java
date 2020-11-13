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
import androidx.work.Data;

import com.example.recharge2me.R;

import java.util.List;

import recahrge.DataTypes.PlanData;
import recahrge.DataTypes.recType_SPL;
import recahrge.myAdapters.planAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import Retrofit.JsonConvertor;

public class Spl_Fragment extends Fragment {

    View v;
    RecyclerView rv_Plan;
    planAdapter planAdapter;
    TextView tv_spl_warning;

    Retrofit retrofit;
    JsonConvertor jsonConvertor;

    public Spl_Fragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.spl_fragment, container, false);

        rv_Plan = v.findViewById(R.id.rv_Plan);
        tv_spl_warning = v.findViewById(R.id.tv_spl_warning);


        // Init Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.rechapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Init jsonConverter
        jsonConvertor = retrofit.create(JsonConvertor.class);

        getRecahrgePlanDetails();

        return v;

    }

    public void getRecahrgePlanDetails(){

        Call<PlanData> call = jsonConvertor.getRechargePlan(
                "json", getString(R.string.token), "SPL", "11", "10");

        call.enqueue(new Callback<PlanData>() {
            @Override
            public void onResponse(Call<PlanData> call, Response<PlanData> response) {
                if(!response.isSuccessful()){
                    tv_spl_warning.setText(response.code());
                    return;
                }

                PlanData planData1 = response.body();

                PlanData.Data data = planData1.getData();

                List<recType_SPL> spl = data.getSPL();

                setRecyclerView(spl);

            }

            @Override
            public void onFailure(Call<PlanData> call, Throwable t) {

                tv_spl_warning.setText(t.getMessage());
            }
        });



    }

    public void setRecyclerView(List<recType_SPL> spls){

        planAdapter = new planAdapter(spls, (getRecahrgePlan) requireActivity());

        rv_Plan.setAdapter(planAdapter);
        rv_Plan.setLayoutManager(new LinearLayoutManager((getRecahrgePlan) requireActivity()));

    }

}
