package recahrge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView tv_mobileNumber,
             tv_socWarningText,
             tv_recahargeType,
             tv_browsePlans;

    Button  btn_circle,
            btn_operator,
            btn_recahargeAmount;

    ImageView iv_rechargeOperator;

    Animation animation;


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
        tv_mobileNumber = view.findViewById(R.id.tv_mobileNumber);
        tv_socWarningText = view.findViewById(R.id.tv_socWarningText);
        tv_recahargeType = view.findViewById(R.id.tv_recahrgeType);
        tv_browsePlans = view.findViewById(R.id.tv_BrowsePlans);

        // Buttons
        btn_circle = view.findViewById(R.id.btn_rechargeCircle);
        btn_operator = view.findViewById(R.id.btn_operator);
        btn_recahargeAmount = view.findViewById(R.id.btn_rechargeAmount);

        // ImageView
        iv_rechargeOperator = view.findViewById(R.id.iv_proceedRechareOperator);

        // Init onClick Animation
        animation = AnimationUtils.loadAnimation((recahrge_ui) requireActivity(), R.anim.click);


        // Init Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.rechapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Init JsonConverter Interface
        jsonConvertor = retrofit.create(JsonConvertor.class);


        // Init onClick Listeners
        btn_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoCircleUi();
            }
        });
        btn_operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoOperatorUi();
            }
        });

        tv_browsePlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_browsePlans.startAnimation(animation);
                Intent intent = new Intent((recahrge_ui) requireActivity(), recahrge.getRecahrgePlan.class );
                startActivityForResult(intent, 8477);

            }
        });


        String number_fromPrePaid = MobileDetailsFinderArgs.fromBundle(getArguments()).getNumber();
        String type = MobileDetailsFinderArgs.fromBundle(getArguments()).getRecahrgeType();
        String userCircle = MobileDetailsFinderArgs.fromBundle(getArguments()).getUserCircle();
        String userNumber_fromCircle = MobileDetailsFinderArgs.fromBundle(getArguments()).getUserNumberFromCircle();


        String num = getNumber(number_fromPrePaid, userNumber_fromCircle);

        getMobileDetails(num, type, userCircle);
//        loadingDialog.stopLoading();

        return view;
    } // End of OnCreteView method;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 8477){
            if(resultCode == Activity.RESULT_OK){
                btn_recahargeAmount.setText(data.getStringExtra("result"));
            }
//            if(resultCode == Activity.RESULT_CANCELED){
//                btn_recahargeAmount.setText("none");
//            }
        }

    }

    // This function returns a number having 4 digits/length
    private String getRemaining(String str){

        char[] chars = str.toCharArray();

        String s ="";

        while(s.length() < 4){
            s += chars[s.length()];
        }

        return s;

    }// end of getRemaining method;

    // it will return userMobileDetails/ mobileDetailsFinder
    private void getMobileDetails(String number, String type, String userCircle){

        String remaning = getRemaining(number);

        Call<MobileDetailsFinder_Data> call = jsonConvertor.getMobileF("json", getString(R.string.token), remaning);

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

                tv_mobileNumber.setText(number);
                tv_recahargeType.setText(type);
                btn_circle.setText(getUserLocation(data, userCircle));
                btn_operator.setText(getUserOperator(data));
                loadingDialog.stopLoading();

                switch(data.getService()){
                    case "Idea" : iv_rechargeOperator.setImageResource(R.drawable.idea);
                                     break;
                    case "Reliance Jio" : iv_rechargeOperator.setImageResource(R.drawable.jio);
                                     break;
                    case "Airtel" : iv_rechargeOperator.setImageResource(R.drawable.airtel);
                                     break;
                    case "Bsnl" : iv_rechargeOperator.setImageResource(R.drawable.bsnl);
                                     break;
                    default:    iv_rechargeOperator.setImageResource(R.drawable.mtnl);
                                     break;
                }

            }

            @Override
            public void onFailure(Call<MobileDetailsFinder_Data> call, Throwable t) {
                tv_socWarningText.setText(t.getMessage());
                loadingDialog.stopLoading();

            }
        });

    }// End of getMobileDetails Method;
    // it Will return userLocation/circle
    public String getUserLocation(MobileDetailsFinder_Data.mobileData data, String fromCircle){

        if(fromCircle.equals("Your Circle"))
            return data.getLocation();
        else{
            if(fromCircle.equals("Madhya Pradesh Chhattisgarh")) {
                btn_circle.setTextSize(14);
            }
            return fromCircle;
        }

    }// end of getUserLocation method;
    // it will return userOperator/service
    public String getUserOperator(MobileDetailsFinder_Data.mobileData data){

        String operator = MobileDetailsFinderArgs.fromBundle(getArguments()).getOperator();

        if(operator.equals("Your Operator"))
            return data.getService();
        else
            return operator;
    }// end of getUserOperator method;

    // getNumber from prePaid UI or recharge_circle UI
    public String getNumber(String fromPrePaid, String fromCircle){

        if(fromCircle.equals("from_prePaid"))
            return fromPrePaid;
        else
            return fromCircle;

    }


    // Functions for Goto another fragments
    private void gotoOperatorUi(){

        btn_operator.startAnimation(animation);

        MobileDetailsFinderDirections.ActionMobileDetailsFinderToRechargeSelectOperator
                        action = MobileDetailsFinderDirections.actionMobileDetailsFinderToRechargeSelectOperator();

        action.setUserNoForOp(tv_mobileNumber.getText().toString().trim());

        Navigation.findNavController(view).navigate(action);
    }
    private void gotoCircleUi(){

        btn_circle.startAnimation(animation);

        MobileDetailsFinderDirections.ActionMobileDetailsFinderToRechargeCircle
                        action = MobileDetailsFinderDirections.actionMobileDetailsFinderToRechargeCircle(tv_mobileNumber.getText().toString().trim());

        Navigation.findNavController(view).navigate(action);

    }
}