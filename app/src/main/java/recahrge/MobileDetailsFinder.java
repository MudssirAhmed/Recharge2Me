package recahrge;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recharge2me.R;

import Retrofit.JsonConvertor;
import custom_Loading_Dialog.LoadingDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;
import Retrofit.MobileDetailsFinder_Data;


public class MobileDetailsFinder extends Fragment {

    TextView tv_Cicle,
            tv_Operator,
            tv_rechargePlan,
            tv_mobileNumber,
            tv_socWarningText,
            tv_recahargeType;


    // Loading Dialog
    LoadingDialog loadingDialog;

    // Retrofit & JsonConverter
    private JsonConvertor jsonConvertor;
//    private final String base_Url = "http://api.rechapi.com/";



    View view;

    public MobileDetailsFinder() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Init Loading Dialog
        loadingDialog = new LoadingDialog((recahrge_ui) requireActivity());
        loadingDialog.startLoading();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mobile_details_finder, container, false);

        // TextView
        tv_Cicle = view.findViewById(R.id.tv_Circle);
        tv_Operator = view.findViewById(R.id.tv_operator);
        tv_rechargePlan = view.findViewById(R.id.tv_rechargePlan);
        tv_mobileNumber = view.findViewById(R.id.tv_mobileNumber);
        tv_socWarningText = view.findViewById(R.id.tv_socWarningText);
        tv_recahargeType = view.findViewById(R.id.tv_recahrgeType);


        // Init Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.rechapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Init JsonConverter Interface
        jsonConvertor = retrofit.create(JsonConvertor.class);


        String number = MobileDetailsFinderArgs.fromBundle(getArguments()).getNumber();
        String type = MobileDetailsFinderArgs.fromBundle(getArguments()).getRecahrgeType();
        getMobileDetails(number, type);

        return view;
    }

    private String getRmaning(String str){

        char[] chars = str.toCharArray();

        String s ="";

        while(s.length() < 4){
            s += chars[s.length()];
        }

        return s;

    }

    private void getMobileDetails(String number, String type){

        String remaning = getRmaning(number);

        Call<MobileDetailsFinder_Data> call = jsonConvertor.getMobileF("json", "rQYwTkpTDVkurPtyGQc7oD7CUaoGbA",remaning);

        call.enqueue(new Callback<MobileDetailsFinder_Data>() {
            @Override
            public void onResponse(Call<MobileDetailsFinder_Data> call, Response<MobileDetailsFinder_Data> response) {
                if (!response.isSuccessful()){
                    tv_socWarningText.setText("Code: " + response.code());
                    loadingDialog.stopLoading();
                    return;
                }

                MobileDetailsFinder_Data mobileDetailsFinder_data = response.body();

                MobileDetailsFinder_Data.mobileData data = mobileDetailsFinder_data.getData();

                String content = "";
                content += "service: " + data.getService() + "\n";
                content += "Location: " + data.getLocation() + "\n";
                content += "Circele Id: " + data.getCircleId() + "\n";
                content += "Operator Id: " + data.getOpId() + "\n";
                content += "resText: " + data.getResText();

                tv_mobileNumber.setText(number);
                tv_recahargeType.setText(type);
                tv_Cicle.setText(data.getLocation());
                tv_Operator.setText(data.getService());
                loadingDialog.stopLoading();

                tv_Cicle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Navigation.findNavController(view).navigate(R.id.action_mobileDetailsFinder_to_recharge_circle);
                    }
                });

            }

            @Override
            public void onFailure(Call<MobileDetailsFinder_Data> call, Throwable t) {
                tv_socWarningText.setText(t.getMessage());
                loadingDialog.stopLoading();

            }
        });

    }// End of getMobileDetails Method;
}