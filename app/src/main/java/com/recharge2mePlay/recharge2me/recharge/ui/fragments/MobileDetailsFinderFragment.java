package com.recharge2mePlay.recharge2me.recharge.ui.fragments;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.recharge2mePlay.recharge2me.R;
import com.recharge2mePlay.recharge2me.constants.AppConstants;
import com.recharge2mePlay.recharge2me.database.models.numberViewModel;
import com.recharge2mePlay.recharge2me.recharge.ui.activities.GetRechargePlanActivity;
import com.recharge2mePlay.recharge2me.recharge.ui.activities.RechargeUiActivity;
import com.recharge2mePlay.recharge2me.utils.AppBaseFragment;
import com.recharge2mePlay.recharge2me.utils.MyAnimation;
import com.recharge2mePlay.recharge2me.utils.dialogs.CustomToast;
import com.recharge2mePlay.recharge2me.utils.dialogs.LoadingDialog;
import com.recharge2mePlay.recharge2me.utils.dialogs.proceedDialog;

import java.util.HashMap;
import java.util.Map;


public class MobileDetailsFinderFragment extends AppBaseFragment {

    TextView tv_mF_planDetails,
             tv_mobileNumber,
             tv_socWarningText,
             tv_recahargeType,
             tv_browsePlans;

    Button  btn_circle,
            btn_operator,
            btn_recahargeAmount,
            btn_mobileDefinder_proceed;

    ImageView iv_rechargeOperator,
              iv_mobileDetails_back;

    // Integers:
    final int PAYTM_REQUEST_CODE = 121;
    Integer ActivityRequestCode = 2;
    final int GOTO_PLAN = 8477;
    int circleId;

    private com.recharge2mePlay.recharge2me.database.models.numberViewModel numberViewModel;

    // Animations
    Animation animation;
    MyAnimation myAnimation;

    // Loading Dialog
    LoadingDialog loadingDialog;
    proceedDialog proceedDialog;

    CustomToast customToast;

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences spProviders;
    SharedPreferences.Editor edit;

    View view;

    // DATA
    private String numberFromPrepaid, rechargeType, userCircle, userOperator;
    private String planDetails, planValidity, providerNameFromDB = "", providerIDFromDB = "";
    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";

    public MobileDetailsFinderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        Application application = getActivity().getApplication();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mobile_details_finder, container, false);

        // Init Loading Dialog
        loadingDialog = new LoadingDialog((RechargeUiActivity) requireActivity());

        tv_mobileNumber = view.findViewById(R.id.tv_mobileNumber);
        tv_socWarningText = view.findViewById(R.id.tv_socWarningText);
        tv_recahargeType = view.findViewById(R.id.tv_recahrgeType);
        tv_browsePlans = view.findViewById(R.id.tv_BrowsePlans);
        tv_mF_planDetails = view.findViewById(R.id.tv_mf_planDetails);
        btn_circle = view.findViewById(R.id.btn_rechargeCircle);
        btn_operator = view.findViewById(R.id.btn_operator);
        btn_recahargeAmount = view.findViewById(R.id.btn_rechargeAmount);
        btn_mobileDefinder_proceed = view.findViewById(R.id.btn_mobileFinder_Proceed);
        iv_rechargeOperator = view.findViewById(R.id.iv_proceedRechareOperator);
        iv_mobileDetails_back = view.findViewById(R.id.iv_mobileDetails_back);

        animation = AnimationUtils.loadAnimation((RechargeUiActivity) requireActivity(), R.anim.click);
        myAnimation = new MyAnimation();
        customToast = new CustomToast((RechargeUiActivity) requireActivity());
        numberViewModel = new numberViewModel(application);
        context = requireContext();

        return view;
    }

    @Override
    public void onViewCreated(
            @NonNull final View view,
            @Nullable final Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        numberFromPrepaid = MobileDetailsFinderFragmentArgs.fromBundle(getArguments()).getNumber();
        rechargeType = MobileDetailsFinderFragmentArgs.fromBundle(getArguments()).getRecahrgeType();
        userCircle = MobileDetailsFinderFragmentArgs.fromBundle(getArguments()).getUserCircle();

        setData();
        setEventListeners();

    }

    private void setData() {
        tv_mobileNumber.setText(numberFromPrepaid);
        tv_recahargeType.setText(rechargeType);

        Navigation.findNavController(view).getCurrentBackStackEntry().getSavedStateHandle()
                .getLiveData(AppConstants.SELECTED_CIRCLE)
                .observe(getViewLifecycleOwner(), new Observer<Object>() {
                    @Override
                        public void onChanged(Object o) {
                            userCircle = o.toString();
                            btn_circle.setText(userCircle);
                        }
                    });

        Navigation.findNavController(view).getCurrentBackStackEntry().getSavedStateHandle()
                .getLiveData(AppConstants.SELECTED_OPERATOR)
                .observe(getViewLifecycleOwner(), new Observer<Object>() {
                    @Override
                    public void onChanged(Object o) {
                        setOperatorTextAndImage(o.toString());
                    }
                });
    }

    private void setEventListeners() {
        iv_mobileDetails_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAnimation.onClickAnimation(iv_mobileDetails_back);
                Navigation.findNavController(view).popBackStack();
            }
        });
        btn_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_circle.startAnimation(animation);

                MobileDetailsFinderFragmentDirections.ActionMobileDetailsFinderToRechargeCircle
                        action = MobileDetailsFinderFragmentDirections.actionMobileDetailsFinderToRechargeCircle(tv_mobileNumber.getText().toString().trim());

                Navigation.findNavController(view).navigate(action);
            }
        });
        btn_operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_operator.startAnimation(animation);

                MobileDetailsFinderFragmentDirections.ActionMobileDetailsFinderToRechargeSelectOperator
                        action = MobileDetailsFinderFragmentDirections.actionMobileDetailsFinderToRechargeSelectOperator();

                action.setUserNoForOp(tv_mobileNumber.getText().toString().trim());

                Navigation.findNavController(view).navigate(action);
            }
        });
        tv_browsePlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(false)) {
                    String opCode = operatorData(btn_operator.getText().toString());
                    String circleID = circleData(btn_circle.getText().toString());

                    tv_browsePlans.startAnimation(animation);

                    Intent intent = new Intent((RechargeUiActivity) requireActivity(), GetRechargePlanActivity.class );
                    intent.putExtra("op", btn_operator.getText().toString());
                    intent.putExtra("circle", btn_circle.getText().toString());
                    intent.putExtra("number", tv_mobileNumber.getText().toString());
                    intent.putExtra("opCode", opCode);
                    intent.putExtra("circleId", circleID);
                    startActivityForResult(intent, GOTO_PLAN);
                }
            }
        });
        btn_mobileDefinder_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                Intent intent = new Intent(getActivity(), StartPaymentPaytmActivity.class);
                intent.putExtra("Number", tv_mobileNumber.getText().toString().trim());
                intent.putExtra("Operator", btn_operator.getText().toString().trim());
                intent.putExtra("circle", btn_circle.getText().toString().trim());
                intent.putExtra("Details", tv_mF_planDetails.getText().toString().trim());
                intent.putExtra("Amount", btn_recahargeAmount.getText().toString().trim());
                startActivity(intent);*/
                if(validate(true)) {
                    Toast.makeText(context, "Have to integrate Payment Gateway", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validate(boolean checkPlan) {
        if(isStringInValid(userCircle)) {
            Toast.makeText(context, "Please select your Circle", Toast.LENGTH_SHORT).show();
            return false;
        } else if(isStringInValid(userOperator)) {
            Toast.makeText(context, "Please select your Operator", Toast.LENGTH_SHORT).show();
            return false;
        } else if(checkPlan) {
            if(isStringInValid(planDetails)) {
                Toast.makeText(context, "Please select recharge plan", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void setOperatorTextAndImage(String operator){
        switch (operator){
            case "Reliance Jio": userOperator = "Jio"; break;
            case "Idea": userOperator = "IDEA"; break;
            case "Airtel": userOperator = "AIRTEL"; break;
            case "Vodafone": userOperator = "VODAFONE"; break;
            case "Bsnl": userOperator = "BSNL"; break;
            case "BSNL": userOperator = "BSNL"; break;
            default: userOperator = null;
        }
        btn_operator.setText(userOperator);

        try {
            switch(operator){
                case "Idea" : iv_rechargeOperator.setImageResource(R.drawable.idea); break;
                case "IDEA" : iv_rechargeOperator.setImageResource(R.drawable.idea); break;
                case "Vodafone" : iv_rechargeOperator.setImageResource(R.drawable.idea); break;
                case "VODAFONE" : iv_rechargeOperator.setImageResource(R.drawable.idea); break;
                case "Reliance Jio" : iv_rechargeOperator.setImageResource(R.drawable.jio); break;
                case "Jio" : iv_rechargeOperator.setImageResource(R.drawable.jio); break;
                case "Airtel" : iv_rechargeOperator.setImageResource(R.drawable.airtel); break;
                case "AIRTEL" : iv_rechargeOperator.setImageResource(R.drawable.airtel); break;
                case "Bsnl" : iv_rechargeOperator.setImageResource(R.drawable.bsnl); break;
                case "BSNL" : iv_rechargeOperator.setImageResource(R.drawable.bsnl); break;
            }
        }
        catch (Exception e){
            customToast.showToast("Unexpected Error!");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // when user come from planfragment
        if(requestCode == GOTO_PLAN) {
            if(resultCode == Activity.RESULT_OK){
                btn_recahargeAmount.setText("₹"+data.getStringExtra("Amount"));
                planDetails = data.getStringExtra("Details");
                planValidity = "Validity: " + data.getStringExtra("Validity");

                tv_mF_planDetails.setText(planValidity + "\n" + planDetails);
            }
        }
        if (requestCode == 99) {
            Toast.makeText((RechargeUiActivity) requireActivity(), "Come Back", Toast.LENGTH_SHORT).show();
        }
    }

    private String operatorData(String key) {

        Map<String, String> op = new HashMap<>();

        op.put("AIRTEL", "AT");
        op.put("Vodafone Idea", "VI");
        op.put("IDEA", "VI");
        op.put("Jio", "RJ");
        op.put("BSNL", "BS");
        op.put("Bsnl", "BS");

        return op.get(key);
    }
    private String circleData(String key) {

        Map<String, String> cd = new HashMap<>();

//        RechApi Data
        cd.put("Delhi NCR", "DL");
        cd.put("Mumbai", "MU");
        cd.put("Kolkata", "KO");
        cd.put("Maharashtra", "MH");
        cd.put("Andhra Pradesh", "AP");
        cd.put("Tamil Nadu", "TN");
        cd.put("Karnataka", "KA");
        cd.put("Gujarat", "GJ");
        cd.put("UP East", "UE");
        cd.put("Madhya Pradesh", "MP");
        cd.put("Madhya Pradesh Chhattisgarh", "MP");
        cd.put("UP West Uttrakhand", "UW");
        cd.put("UP West", "UW");
        cd.put("West Bengal", "WB");
        cd.put("Rajasthan", "RJ");
        cd.put("Kerala", "KL");
        cd.put("Punjab", "PB");
        cd.put("Haryana", "HR");
        cd.put("Bihar & Jharkhand", "BR");
        cd.put("Orissa", "OR");
        cd.put("Assam", "AS");
        cd.put("North East", "NE");
        cd.put("Himachal Pradesh", "HP");
        cd.put("Jammu & Kashmir", "JK");
        cd.put("Chennai", "CH");

        return cd.get(key);
    }
}