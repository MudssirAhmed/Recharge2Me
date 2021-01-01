package recahrge.plans;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.recharge2mePlay.recharge2me.R;

import java.util.List;

import Global.custom_Loading_Dialog.CustomToast;
import recahrge.DataTypes.planDataTypes.PlanData;
import recahrge.DataTypes.planDataTypes.recType_FTT;
import recahrge.myAdapters.PlanAdapter;
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

    getRecahrgePlan activity;

    JsonConvertor jsonConvertor;

    CustomToast toast;

    public Ftt_Fragment() {
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

        view = inflater.inflate(R.layout.ftt_fragment, container, false);

        rv_planFtt = view.findViewById(R.id.rv_planFtt);
        tv_planFtt_WarningText = view.findViewById(R.id.tv_planFtt_WarningText);

        toast = new CustomToast(getActivity());

        // Init Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl_rechApi))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonConvertor = retrofit.create(JsonConvertor.class);

        getPlansFTT();

        return view;
    }

    private void getPlansFTT(){


        Call<PlanData> call = jsonConvertor
                .getRechargePlan("json", getString(R.string.token),"FTT", activity.getCircleId(), activity.getOpCode());

        call.enqueue(new Callback<PlanData>() {
            @Override
            public void onResponse(Call<PlanData> call, Response<PlanData> response) {

                if(!response.isSuccessful()){
                    tv_planFtt_WarningText.setText(response.code());
                    activity.hideProgressBar();
                    return;
                }
                try {
                    PlanData planData = response.body();
                    String resText = planData.getResText();

                    PlanData.Data  data = planData.getData();

                    List<recType_FTT> ftt = data.getFTT();

                    if(ftt == null) {
                        tv_planFtt_WarningText.setText(resText);
                        activity.hideProgressBar();
                    }
                    else
                        setFttDataOnRecyclerView(ftt);

                    rv_planFtt.addOnItemTouchListener(new PlanAdapter.planRecyclerTouchListener((getRecahrgePlan) requireActivity(),
                            rv_planFtt, new PlanAdapter.planClickListner() {
                        @Override
                        public void onPlanClick(View view, int position, View btn) {
                            Context context;
                            Animation animation = AnimationUtils.loadAnimation((getRecahrgePlan) requireActivity(), R.anim.click );
                            view.startAnimation(animation);

                            recType_FTT sendFtt = ftt.get(position);

                            getRecahrgePlan activity = (getRecahrgePlan) getActivity();
                            activity.getRecahrgePlan(sendFtt.getAmount(), sendFtt.getValidity(), sendFtt.getDetail());
                            activity.sendPlanData();

                        }
                    }));
                }
                catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<PlanData> call, Throwable throwable) {
                tv_planFtt_WarningText.setText(throwable.getMessage());
                activity.hideProgressBar();
            }
        });
    }

    private void setFttDataOnRecyclerView(List<recType_FTT> ftt){

        try {
            PlanAdapter planAdapter_ = new
                    PlanAdapter(null, null, ftt, null, null, (getRecahrgePlan) requireActivity());

            rv_planFtt.setAdapter(planAdapter_);
            rv_planFtt.setLayoutManager(new LinearLayoutManager((getRecahrgePlan) requireActivity()));
            activity.hideProgressBar();
        }
        catch (Exception e) {
            toast.showToast("Error!");
        }
    }
}
