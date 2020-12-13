package recahrge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.recharge2mePlay.recharge2me.R;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import java.util.HashMap;
import java.util.Map;

import Retrofit.JsonConvertor;
import custom_Loading_Dialog.LoadingDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import Retrofit.MobileDetailsFinder_Data;


public class MobileDetailsFinder extends Fragment {

    TextView tv_mF_planDetails,
             tv_mobileNumber,
             tv_socWarningText,
             tv_recahargeType,
             tv_browsePlans;

    Button  btn_circle,
            btn_operator,
            btn_recahargeAmount,
            btn_mobileDefinder_proceed;

    ImageView iv_rechargeOperator;

    Animation animation;

    int circleId;

    // Loading Dialog
    LoadingDialog loadingDialog;

    // Retrofit & JsonConverter
    private JsonConvertor jsonConvertor;
//    private final String base_Url = "http://api.rechapi.com/";


    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;


    Checkout checkout;

    View view;

    public MobileDetailsFinder() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Init Loading Dialog
        loadingDialog = new LoadingDialog((recharge_ui) requireActivity());
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
        tv_mF_planDetails = view.findViewById(R.id.tv_mf_planDetails);


        // Buttons
        btn_circle = view.findViewById(R.id.btn_rechargeCircle);
        btn_operator = view.findViewById(R.id.btn_operator);
        btn_recahargeAmount = view.findViewById(R.id.btn_rechargeAmount);
        btn_mobileDefinder_proceed = view.findViewById(R.id.btn_mobileFinder_Proceed);

        // ImageView
        iv_rechargeOperator = view.findViewById(R.id.iv_proceedRechareOperator);

        // Init onClick Animation
        animation = AnimationUtils.loadAnimation((recharge_ui) requireActivity(), R.anim.click);


        // Init Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.rechapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Init JsonConverter Interface
        jsonConvertor = retrofit.create(JsonConvertor.class);


        // Init onClick Listeners
        // This is for chose Circl :-
        btn_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoCircleUi();
            }
        });
        // This is for chose operator :-
        btn_operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoOperatorUi();
            }
        });
        // This is the proceed button :-
        btn_mobileDefinder_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getPayments();
                setDataofUser();
            }
        });
        // This is for BrowsePlan :-
        tv_browsePlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoRecargePlanUi();
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
                btn_recahargeAmount.setText(data.getStringExtra("Amount"));

                String details = "Validity: " + data.getStringExtra("Validity");

                tv_mF_planDetails.setText(details + "\n" +
                        data.getStringExtra("Details"));
            }
//            if(resultCode == Activity.RESULT_CANCELED){
//                btn_recahargeAmount.setText("Amount Error");
//            }
        }

        if (requestCode == 99) {

      /*This callback indicates only about completion of UI flow.
            Inform your server to make the transaction
            status call to get the status. Update your app with the
            success/failure status.*/

            Toast.makeText((recharge_ui) requireActivity(), "Cone Back", Toast.LENGTH_SHORT).show();

        }

    }






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

                // shared Prefrences;
//                Context context;
//                SharedPreferences sharedPreferences;
//                SharedPreferences.Editor edit;

                context = (recharge_ui) requireActivity();
                sharedPreferences = context.getSharedPreferences("MobileDetails", Context.MODE_PRIVATE);
                edit = sharedPreferences.edit();

                edit.putString("Location", data.getLocation());
                edit.putString("Operator", data.getService());
                edit.apply();

                switch(data.getService()){
                    case "Idea" : iv_rechargeOperator.setImageResource(R.drawable.idea);
                        break;
                    case "Vodafone" : iv_rechargeOperator.setImageResource(R.drawable.idea);
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

                tv_mobileNumber.setText(number);
                tv_recahargeType.setText(type);
                btn_circle.setText(getUserLocation(data, userCircle));
                btn_operator.setText(getUserOperator(data));

//                edit.putBoolean("checkCircle", false);
//                edit.apply();

                circleId = data.getCircleId();

                loadingDialog.stopLoading();
            }

            @Override
            public void onFailure(Call<MobileDetailsFinder_Data> call, Throwable t) {
                tv_socWarningText.setText(t.getMessage());
                loadingDialog.stopLoading();

            }
        });

    }// End of getMobileDetails Method;

    // Get Data methods
    // This function returns a number having 4 digits/length
    private String getRemaining(String str){

        char[] chars = str.toCharArray();

        String s ="";

        while(s.length() < 4){
            s += chars[s.length()];
        }

        return s;

    }// end of getRemaining method;
    // it Will return userLocation/circle
    public String getUserLocation(MobileDetailsFinder_Data.mobileData data, String fromCircle){

        if(fromCircle.equals("Your Circle")) {
            return data.getLocation();
        }
        else{
            if(fromCircle.equals("Madhya Pradesh Chhattisgarh")) {
                btn_circle.setTextSize(14);
            }
            edit.putString("selectLocation", fromCircle);
            edit.putBoolean("checkCircle", true);
            edit.apply();
            return fromCircle;
        }

    }// end of getUserLocation method;
    // it will return userOperator/service
    public String getUserOperator(MobileDetailsFinder_Data.mobileData data){

        String operator = MobileDetailsFinderArgs.fromBundle(getArguments()).getOperator();

        if(operator.equals("Your Operator"))
            return data.getService();
        else{
//            tv_recahargeType.setText(operatorData(operator));
            edit.putString("fOperator", operator);
            edit.apply();
            if (check()){
                btn_circle.setText(sharedPreferences.getString("selectLocation", ""));
            }
            switch(operator){
                case "Idea" : iv_rechargeOperator.setImageResource(R.drawable.idea);
                    break;
                case "Vodafone" : iv_rechargeOperator.setImageResource(R.drawable.idea);
                    break;
                case "Vodafone Idea" : iv_rechargeOperator.setImageResource(R.drawable.idea);
                    break;
                case "Reliance Jio" : iv_rechargeOperator.setImageResource(R.drawable.jio);
                    break;
                case "Airtel" : iv_rechargeOperator.setImageResource(R.drawable.airtel);
                    break;
                case "Bsnl" : iv_rechargeOperator.setImageResource(R.drawable.bsnl);
                    break;
                case "BSNL" : iv_rechargeOperator.setImageResource(R.drawable.bsnl);
                    break;
                default:    iv_rechargeOperator.setImageResource(R.drawable.mtnl);
                    break;
            }
            return operator;
        }
    }// end of getUserOperator method;
    private boolean check(){
        return sharedPreferences.getBoolean("checkCircle", false);
    }
    // getNumber from prePaid UI or recharge_circle UI
    public String getNumber(String fromPrePaid, String fromCircle){

        if(fromCircle.equals("from_prePaid"))
            return fromPrePaid;
        else
            return fromCircle;

    }



    private void setDataofUser(){

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
    private void gotoRecargePlanUi(){

        tv_browsePlans.startAnimation(animation);

        Intent intent = new Intent((recharge_ui) requireActivity(), recahrge.getRecahrgePlan.class );

        intent.putExtra("op", btn_operator.getText().toString());
        intent.putExtra("circle", btn_circle.getText().toString());
        intent.putExtra("number", tv_mobileNumber.getText().toString());
//        intent.putExtra("circleId", String.valueOf(circleId));

        String opCode = operatorData(btn_operator.getText().toString());
        intent.putExtra("opCode", opCode);

        String circleID = circleData(btn_circle.getText().toString());
        intent.putExtra("circleId", circleID);

        startActivityForResult(intent, 8477);

    }// This fun. help to going to getRecahrgePlan(Activity).
    private void getPayments(){

    }









    // operatod Data/codes
    public String operatorData(String key){

        Map<String, String> op = new HashMap<>();

        op.put("Airtel", "1");
        op.put("Vodafone Idea", "10");
        op.put("Idea", "10");
        op.put("Reliance Jio", "93");
        op.put("BSNL", "4");
        op.put("MTNL Delhi NCR", "17");
        op.put("MTNL Mumbai", "19");

        op.put("Vodafone Postpaid", "30");
        op.put("Airtel Postpaid", "31");
        op.put("Bsnl Postpaid", "32");
        op.put("Idea Postpaid", "33");
        op.put("Reliance Jio Postpaid", "34");


//        MTNL DL Topup	17
//        MTNL DL Special	18
//        MTNL Mumbai	19
//        MTNL Mumbai Special	20

        return op.get(key);
    }
    // circle Data/codes
    public String circleData(String key){

        Map<String, String> cd = new HashMap<>();

//        RechApi Data
        cd.put("Delhi NCR", "1");
        cd.put("Mumbai", "2");
        cd.put("Kolkata", "3");
        cd.put("Maharashtra", "4");
        cd.put("Andhra Pradesh", "5");
        cd.put("Tamil Nadu", "6");
        cd.put("Karnataka", "7");
        cd.put("Gujarat", "8");
        cd.put("UP East", "9");
        cd.put("Madhya Pradesh", "10");
        cd.put("Madhya Pradesh Chhattisgarh", "10");
        cd.put("UP West Uttrakhand", "11");
        cd.put("UP West", "11");
        cd.put("West Bengal", "12");
        cd.put("Rajasthan", "13");
        cd.put("Kerala", "14");
        cd.put("Punjab", "15");
        cd.put("Haryana", "16");
        cd.put("Bihar & Jharkhand", "17");
        cd.put("Orissa", "18");
        cd.put("Assam", "19");
        cd.put("North East", "20");
        cd.put("Himachal Pradesh", "21");
        cd.put("Jammu & Kashmir", "22");
        cd.put("Chennai", "23");


        // Pay2all Data:-
//        cd.put("Punjab", "1");
//        cd.put("West Bengal", "2");
//        cd.put("Mumbai", "3");
//        cd.put("Maharashtra", "4");
//        cd.put("Delhi NCR", "5");
//        cd.put("Kolkata", "6");
//        cd.put("Chennai", "7");
//        cd.put("Tamil Nadu", "8");
//        cd.put("Karnataka", "9");
//        cd.put("UP East", "10");
//        cd.put("UP West Uttrakhand", "11");
//        cd.put("Gujarat", "12");
//        cd.put("Kerala", "14");
//        cd.put("Madhya Pradesh Chhattisgarh", "16");
//        cd.put("Rajasthan", "18");
//        cd.put("Haryana", "20");
//        cd.put("Himachal Pradesh", "21");
//        cd.put("Bihar & Jharkhand", "22");
//        cd.put("Orissa", "23");
//        cd.put("Jammu & Kashmir", "25");
//        cd.put("North East", "26");
//        cd.put("Assam", "34");
//        cd.put("Andhra Pradesh", "35");


        return cd.get(key);
    }
}