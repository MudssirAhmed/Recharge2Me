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
import recahrge.DataTypes.recType_TUP;
import recahrge.myAdapters.PlanAdapter_SPL;
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

    Retrofit retrofit;
    JsonConvertor jsonConvertor;

    public Tup_Fragment() {
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
                .getRechargePlan("json", getString(R.string.token), "TUP", "11", "10");

        call.enqueue(new Callback<PlanData>() {
            @Override
            public void onResponse(Call<PlanData> call, Response<PlanData> response) {
                if(!response.isSuccessful()){
                    tv_planTup_warningText.setText(response.code());
                    return;
                }
                PlanData planData = response.body();
                String resText = planData.getResText();

                PlanData.Data data = planData.getData();

                List<recType_TUP> tup = data.getTUP();

                // Testing
//                String s = "";
//                for (recType_TUP type_tup : tup){
//                    s += type_tup.getAmount() + "\n";
//                }
//                tv_planTup_warningText.setText(s);

                if (tup == null)
                    tv_planTup_warningText.setText(resText);
                else
                    setDataOnRecyclerView(tup);

            }

            @Override
            public void onFailure(Call<PlanData> call, Throwable throwable) {
                tv_planTup_warningText.setText(throwable.getMessage());
            }
        });

    }// End of getPlanTup

    private void setDataOnRecyclerView(List<recType_TUP> tup){

        PlanAdapter_SPL planAdapter_spl = new
                PlanAdapter_SPL(null, null, null, tup, null, (getRecahrgePlan) requireActivity());

        rv_planTup.setAdapter(planAdapter_spl);
        rv_planTup.setLayoutManager(new LinearLayoutManager((getRecahrgePlan) requireActivity()));

    }// End of setDataOnRecyclerView
}
