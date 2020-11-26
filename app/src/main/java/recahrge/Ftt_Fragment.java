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

import java.util.List;

import recahrge.DataTypes.PlanData;
import recahrge.DataTypes.recType_FTT;
import recahrge.myAdapters.PlanAdapter_SPL;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import Retrofit.JsonConvertor;
import retrofit2.converter.gson.GsonConverterFactory;

public class Ftt_Fragment extends Fragment {

    View view;

    RecyclerView rv_planFtt;
    TextView tv_planFtt_WarningText;

    private Retrofit retrofit;
    JsonConvertor jsonConvertor;

    public Ftt_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.ftt_fragment, container, false);

        rv_planFtt = view.findViewById(R.id.rv_planFtt);
        tv_planFtt_WarningText = view.findViewById(R.id.tv_planFtt_WarningText);

        // Init Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl_rechApi))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonConvertor = retrofit.create(JsonConvertor.class);

        getPlansFTT();

        return view;
    }

    private void getPlansFTT(){

        Call<PlanData> call = jsonConvertor
                .getRechargePlan("json", getString(R.string.token),"FTT", "11", "10");

        call.enqueue(new Callback<PlanData>() {
            @Override
            public void onResponse(Call<PlanData> call, Response<PlanData> response) {

                if(!response.isSuccessful()){
                    tv_planFtt_WarningText.setText(response.code());
                    return;
                }

                PlanData planData = response.body();
                String resText = planData.getResText();

                PlanData.Data  data = planData.getData();

                List<recType_FTT> ftt = data.getFTT();

                if(ftt == null)
                    tv_planFtt_WarningText.setText(resText);
                else
                    setFttDataOnRecyclerView(ftt);
            }

            @Override
            public void onFailure(Call<PlanData> call, Throwable throwable) {
                tv_planFtt_WarningText.setText(throwable.getMessage());
            }
        });
    }

    private void setFttDataOnRecyclerView(List<recType_FTT> ftt){

        PlanAdapter_SPL planAdapter_spl = new
                PlanAdapter_SPL(null, null, ftt, null, null, (getRecahrgePlan) requireActivity());

        rv_planFtt.setAdapter(planAdapter_spl);
        rv_planFtt.setLayoutManager(new LinearLayoutManager((getRecahrgePlan) requireActivity()));

    }
}
