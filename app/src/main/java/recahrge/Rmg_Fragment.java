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

import org.w3c.dom.Text;

import java.util.List;

import recahrge.DataTypes.PlanData;
import recahrge.DataTypes.recType_RMG;
import recahrge.myAdapters.PlanAdapter_SPL;
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

    Retrofit retrofit;
    JsonConvertor jsonConvertor;

    public Rmg_Fragment() {
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
                .getRechargePlan("json", getString(R.string.token), "RMG", "11", "10");

        call.enqueue(new Callback<PlanData>() {
            @Override
            public void onResponse(Call<PlanData> call, Response<PlanData> response) {

                if(!response.isSuccessful()){
                    tv_planRmg_WarningText.setText(response.code());
                    return;
                }

                PlanData planData = response.body();
                String resText = planData.getResText();

                PlanData.Data data = planData.getData();

                List<recType_RMG> rmg = data.getRMG();

                // Testing
//                String s = "";
//                for(recType_RMG rmg1 : rmg){
//                    s += rmg1.getAmount();
//                }
//                tv_planRmg_WarningText.setText(s);

                if(rmg == null)
                    tv_planRmg_WarningText.setText(resText);
                else
                    setDataOnRecyclerView(rmg);

            }

            @Override
            public void onFailure(Call<PlanData> call, Throwable throwable) {
                tv_planRmg_WarningText.setText(throwable.getMessage());
            }
        });

    }// end of getRmgData

    private void setDataOnRecyclerView(List<recType_RMG> rmg){

        PlanAdapter_SPL planAdapter_spl = new
                PlanAdapter_SPL(null, null, null, null,rmg, (getRecahrgePlan) requireActivity());

        rv_Rmg.setAdapter(planAdapter_spl);
        rv_Rmg.setLayoutManager(new LinearLayoutManager((getRecahrgePlan) requireActivity()));

    }// end of setDatOnRecyclerView
}
