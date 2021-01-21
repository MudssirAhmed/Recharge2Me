package recahrge;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.recharge2mePlay.recharge2me.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Global.customAnimation.MyAnimation;
import LogInSignIn_Entry.DataTypes.User_googleAndOwn;
import Retrofit.JsonConvertor;
import Global.custom_Loading_Dialog.CustomToast;
import Global.custom_Loading_Dialog.LoadingDialog;
import Global.custom_Loading_Dialog.proceedDialog;
import Ui_Front_and_Back_end.Main_UserInterface;
import Ui_Front_and_Back_end.Transactions.NotificationTransactionDetails;
import local_Databasse.numberData.Database_numberJava;
import local_Databasse.entity_numberDetails;
import local_Databasse.numberData.numberViewModel;
import recahrge.DataTypes.Paye2All.Pay2All_authToken;
import recahrge.DataTypes.Paye2All.Pay2All_recharge;
import recahrge.DataTypes.rechargeFirbase.Paytm.PaytmTransactionData;
import recahrge.paytm.PaytmToken;
import recahrge.paytm.PaytmTransactionStatus;
import recahrge.plans.getRecahrgePlan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import Retrofit.MobileDetailsFinder_Data;

import static com.google.android.gms.tasks.Tasks.await;


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

    ImageView iv_rechargeOperator,
              iv_mobileDetails_back;

    // Strings:
    String Details_dialoge = ""; // This is for Details shown in Alert Dialog
    String Validity_dialoge = ""; // This is for Validity shown in Alert Dialog
    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"; // This is for GooglePay Payments
    String providerNameFromDB = "";
    String providerIDFromDB = "";

    // Integers:
    final int PAYTM_REQUEST_CODE = 121;
    Integer ActivityRequestCode = 2;
    final int GOTO_PLAN = 8477;
    int circleId;

    private local_Databasse.numberData.numberViewModel numberViewModel;

// customes
    // Animations
    Animation animation;
    MyAnimation myAnimation;

    // Loading Dialog
    LoadingDialog loadingDialog;
    proceedDialog proceedDialog;

    //firebase
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    private JsonConvertor jsonConvertor;

    CustomToast customToast;

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences spProviders;
    SharedPreferences.Editor edit;

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
        iv_mobileDetails_back = view.findViewById(R.id.iv_mobileDetails_back);

        // Init onClick Animation
        animation = AnimationUtils.loadAnimation((recharge_ui) requireActivity(), R.anim.click);
        myAnimation = new MyAnimation();

        // Init custom Taost
        customToast = new CustomToast((recharge_ui) requireActivity());

        // Init Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Init numberVaiewModel for Database
        Application application = getActivity().getApplication();
        numberViewModel = new numberViewModel(application);


        // Init Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.rechapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Init JsonConverter Interface
        jsonConvertor = retrofit.create(JsonConvertor.class);


    // Init onClick Listeners
        // backPressed
        iv_mobileDetails_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPrePaidUi();
            }
        });
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
        // This is for BrowsePlan :-
        tv_browsePlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoRecargePlanUi();
            }
        });
        // This is the proceed button :-
        btn_mobileDefinder_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 findDataInDataBase(tv_mobileNumber.getText().toString());

//                Intent intent = new Intent(getActivity(), StartPaymentPaytm.class);
//                intent.putExtra("Number", tv_mobileNumber.getText().toString().trim());
//                intent.putExtra("Operator", btn_operator.getText().toString().trim());
//                intent.putExtra("circle", btn_circle.getText().toString().trim());
//                intent.putExtra("Details", tv_mF_planDetails.getText().toString().trim());
//                intent.putExtra("Amount", btn_recahargeAmount.getText().toString().trim());
//                startActivity(intent);

                 if(btn_recahargeAmount.getText().toString().equals("Amount") || btn_recahargeAmount.getText().toString().isEmpty())
                     customToast.showToast("Please select plan First!");
                 else {
                     if(isNetworkAvailable()){

                         Intent intent = new Intent(getActivity(), StartPaymentPaytm.class);

                         intent.putExtra("Number", tv_mobileNumber.getText().toString().trim());
                         intent.putExtra("Operator", btn_operator.getText().toString().trim());
                         intent.putExtra("circle", btn_circle.getText().toString().trim());
                         intent.putExtra("Details", tv_mF_planDetails.getText().toString().trim());
                         intent.putExtra("Amount", btn_recahargeAmount.getText().toString().trim());

                         startActivity(intent);
                     }
                     else {
                         customToast.showToast("Please Check Your Internet Connection!...");
                     }
                 }

            }
        });


        String number_fromPrePaid = MobileDetailsFinderArgs.fromBundle(getArguments()).getNumber();
        String type = MobileDetailsFinderArgs.fromBundle(getArguments()).getRecahrgeType();
        String userCircle = MobileDetailsFinderArgs.fromBundle(getArguments()).getUserCircle();
        String userNumber_fromCircle = MobileDetailsFinderArgs.fromBundle(getArguments()).getUserNumberFromCircle();


        String num = getNumber(number_fromPrePaid, userNumber_fromCircle);

        if(isNullOrNot(number_fromPrePaid, userNumber_fromCircle)){
            entity_numberDetails numberDetails = MobileDetailsFinderArgs.fromBundle(getArguments()).getNumberData();

            tv_mobileNumber.setText(numberDetails.getNumber());
            btn_operator.setText(numberDetails.getOperator());
            btn_circle.setText(numberDetails.getCircle());
            setImageViewOnOperatorImageView(numberDetails.getOperator());

            loadingDialog.stopLoading();
        }
        else {
            try {
                getMobileDetails(num, type, userCircle);
            }
            catch (Exception e){
                customToast.showToast("Unexpected Error!");
            }
        }

        return view;
    } // End of OnCreteView method;


    private void setOperatorText(String operator){
        switch (operator){
            case "Reliance Jio": btn_operator.setText("Jio");break;
            case "Idea": btn_operator.setText("IDEA");break;
            case "Airtel": btn_operator.setText("AIRTEL");break;
            case "Vodafone": btn_operator.setText("VODAFONE");break;
            case "Bsnl": btn_operator.setText("BSNL");break;
            case "BSNL": btn_operator.setText("BSNL");break;
            default: btn_operator.setText(null);
        }
    } // It will set the operator Text in correct manner
    private void setImageViewOnOperatorImageView(String operator){

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

    } // This will set the operator Image on op_imageView
    private boolean isNullOrNot(String number, String fromCircle){

        if(number.equals("null") && fromCircle.equals("null"))
            return true;
        return false;
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

                context = (recharge_ui) requireActivity();
                sharedPreferences = context.getSharedPreferences("MobileDetails", Context.MODE_PRIVATE);
                edit = sharedPreferences.edit();

                edit.putString("Location", data.getLocation());
                edit.putString("Operator", data.getService());
                edit.apply();

                setImageViewOnOperatorImageView(data.getService());

                tv_mobileNumber.setText(number);
                tv_recahargeType.setText(type);
                btn_circle.setText(getUserLocation(data, userCircle));
                setOperatorText(getUserOperator(data));

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
    private String getUserLocation(MobileDetailsFinder_Data.mobileData data, String fromCircle){

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
    private String getUserOperator(MobileDetailsFinder_Data.mobileData data){

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
    private String getNumber(String fromPrePaid, String fromCircle){

        if(fromCircle.equals("from_prePaid"))
            return fromPrePaid;
        else
            return fromCircle;

    }




    // Functions for Goto another fragments
    private void gotoPrePaidUi(){
        myAnimation.onClickAnimation(iv_mobileDetails_back);
        Navigation.findNavController(view).navigate(R.id.action_mobileDetailsFinder_to_prePaid3);
    }
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

        Intent intent = new Intent((recharge_ui) requireActivity(), getRecahrgePlan.class );

        intent.putExtra("op", btn_operator.getText().toString());
        intent.putExtra("circle", btn_circle.getText().toString());
        intent.putExtra("number", tv_mobileNumber.getText().toString());

        String opCode = operatorData(btn_operator.getText().toString());

        intent.putExtra("opCode", opCode);

        String circleID = circleData(btn_circle.getText().toString());
        intent.putExtra("circleId", circleID);

        startActivityForResult(intent, GOTO_PLAN);

    }// This fun. help to going to getRecahrgePlan(Activity).




// proceed button Functions:-
    // These functions are for storing, updating the number Database :-
        private void findDataInDataBase(String number){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String getNumber = Database_numberJava.getInstance((recharge_ui) requireActivity())
                                .numberDao()
                                .getOneData(number);
                        if(getNumber.equals(number)) {
                            Log.d("myNumber", "save: " + number);
                            updateNumberDataInDatabase();
                        }
                    }
                    catch (Exception e){
                        Log.d("myNumberE", e.getMessage());
                        setNumberDataInDatabase();
                    }
                    return;
                }
            }).start();
}// It update the data if Data is already present else if Data automatically added in Database
        private void setNumberDataInDatabase(){

      String number = tv_mobileNumber.getText().toString();
      String circle = btn_circle.getText().toString();
      String operator = btn_operator.getText().toString();

      entity_numberDetails numberDetails = new entity_numberDetails(number, circle, operator);
      numberViewModel.addNumberDetails(numberDetails);

  }// This will sey the number Data in Database
        private void updateNumberDataInDatabase(){
        String Number = tv_mobileNumber.getText().toString();
        String operator = btn_operator.getText().toString();
        String circle = btn_circle.getText().toString();

        entity_numberDetails numberDetails = new entity_numberDetails(Number, circle, operator);

        numberViewModel.updateNumberData(numberDetails);
    } // This will update the numberData in Database



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                =  (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // when user come from planfragment
        if(requestCode == GOTO_PLAN){
            if(resultCode == Activity.RESULT_OK){
                btn_recahargeAmount.setText("₹"+data.getStringExtra("Amount"));

//                String Validity = "Validity: " + data.getStringExtra("Validity");

                Details_dialoge = data.getStringExtra("Details");
                Validity_dialoge = "Validity: " + data.getStringExtra("Validity");

                tv_mF_planDetails.setText(Validity_dialoge + "\n" +
                        Details_dialoge);
            }
        }
        if (requestCode == 99) {
            Toast.makeText((recharge_ui) requireActivity(), "Come Back", Toast.LENGTH_SHORT).show();
        }

    } // End of onActivityResult

    // opId for pay2All
    // operatod Data/codes
    private String operatorData(String key){

        Map<String, String> op = new HashMap<>();

        op.put("AIRTEL", "1");
        op.put("Vodafone Idea", "10");
        op.put("VODAFONE", "10");
        op.put("IDEA", "10");
        op.put("Jio", "93");
        op.put("BSNL", "4");
        op.put("Bsnl", "4");

        return op.get(key);
    }
    // circle Data/codes
    private String circleData(String key){

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

        return cd.get(key);
    }
}