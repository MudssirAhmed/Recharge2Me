package recahrge;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.recharge2mePlay.recharge2me.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Global.customAnimation.MyAnimation;
import Retrofit.JsonConvertor;
import Global.custom_Loading_Dialog.CustomToast;
import Global.custom_Loading_Dialog.LoadingDialog;
import Global.custom_Loading_Dialog.proceedDialog;
import local_Databasse.numberData.Database_numberJava;
import local_Databasse.entity_numberDetails;
import local_Databasse.numberData.numberViewModel;
import local_Databasse.providersData.Database_providers;
import local_Databasse.providersData.Entity_providers;
import recahrge.DataTypes.Paye2All.Pay2All_authToken;
import recahrge.DataTypes.Paye2All.Pay2All_providers;
import recahrge.DataTypes.Paye2All.Pay2All_recharge;
import recahrge.DataTypes.rechargeFirbase.Order;
import recahrge.DataTypes.rechargeFirbase.Pay2All_rechargeFirebase;
import recahrge.DataTypes.rechargeFirbase.Pay2All_status;
import recahrge.DataTypes.rechargeFirbase.Paytm_initiateTransaction;
import recahrge.DataTypes.rechargeFirbase.Paytm_refund;
import recahrge.DataTypes.rechargeFirbase.Paytm_refundStatus;
import recahrge.DataTypes.rechargeFirbase.Paytm_transactonStatus;
import recahrge.paytm.PaytmToken;
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
    final int GOOGLE_PAY_REQUEST_CODE = 123;
    final int PAYTM_RESULT_CODE = 121;
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

        // TODO get providrs Data from Database and add token feild in providrsDatabase

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

                 setOrderData();

//                 if(btn_recahargeAmount.getText().toString().equals("Amount")){
//                     customToast.showToast("Please select plan");
//                 }
//                 else {
//                     showAlertDialog("", "", "", "");
//                 }

//                 if(btn_recahargeAmount.getText().toString().equals("Amount") || btn_recahargeAmount.getText().toString().isEmpty())
//                     customToast.showToast("Please select plan First!");
//                 else {
//                     if(isNetworkAvailable()){
//                         getAuthToken_pay2All(btn_recahargeAmount.getText().toString().trim());
//                     }
//                     else {
//                         customToast.showToast("Please Check Your Internet Connection!...");
//                     }
//                 }

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

        if (requestCode == PAYTM_RESULT_CODE && data != null) {
            Toast.makeText((recharge_ui) requireActivity(), "Error: " + data.getStringExtra("nativeSdkForMerchantMessage") +
                    data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        }

    } // End of onActivityResult


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
    // These functions are for showAlertDialog, payments and recharge
        private void getAuthToken_pay2All(String amount){
        try {
            loadingDialog.startLoading();
            // Init Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.pay2all.in/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Init JsonConverter Interface
            jsonConvertor = retrofit.create(JsonConvertor.class);

            Map<String, String> params = new HashMap<>();
            params.put("email", "mudssira01@gmail.com");
            params.put("password", "4nVztc");

            Call<Pay2All_authToken> call = jsonConvertor.getAuthToken(params);
            call.enqueue(new Callback<Pay2All_authToken>() {
                @Override
                public void onResponse(Call<Pay2All_authToken> call, Response<Pay2All_authToken> response) {
                    if(!response.isSuccessful()){
                        customToast.showToast("Error! " + response.code());
                        loadingDialog.stopLoading();
                        return;
                    }

                    try {
                        Pay2All_authToken pay2All_authToken = response.body();

                        String authToken = pay2All_authToken.getAccess_token();
                        Pay2All_authToken.userPay2All user = pay2All_authToken.getUser();
                        Pay2All_authToken.userPay2All.Balance balance = user.getBalance();

//                    Toast.makeText((recharge_ui) requireActivity(), "balance: "+ balance.getUser_balance(), Toast.LENGTH_SHORT).show();

                        //todo check ballance also
                        if(isNetworkAvailable()){
                            getProvidersFromDatabase(btn_operator.getText().toString(), amount, authToken);
                        }
                        else {
                            customToast.showToast("Please Check Your Internet Connection!...");
                            loadingDialog.stopLoading();
                        }
                    }
                    catch (Exception e){
                        customToast.showToast("Error! " + e.getMessage());
                    }

                }
                @Override
                public void onFailure(Call<Pay2All_authToken> call, Throwable t) {
                    customToast.showToast("Error! " + t.getMessage());
                    loadingDialog.stopLoading();
                }
            });
        }
        catch (Exception e){
            loadingDialog.stopLoading();
            customToast.showToast("Error! " + e.getMessage());
        }

    }// This will fetch the Auth Token
        private void getProvidersFromDatabase(String pName, String Amount, String Token){

        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Entity_providers p = Database_providers
                            .getInstance(getContext())
                            .providersDao()
                            .getProvider(btn_operator.getText().toString().trim());

                    if(!p.equals(null)){
                        providerNameFromDB = p.getProviderName();
                        providerIDFromDB = p.getProviderId();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("Providers", "Providrs From Database");
                                loadingDialog.stopLoading();
                                showAlertDialog(p.getProviderName(), Token, p.getProviderId(), Amount);
                            }
                        });
                    }
                }
                catch (Exception e){
                    providerIDFromDB = null;
                    providerNameFromDB = null;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getAllProviders(Token, Amount);
                        }
                    });
                    Log.d("ProvidersNullExp", e.getMessage());
                }
            }
        }).start();

    } // Get Auth_Token and Provider_Data from Database
        private void getAllProviders(String Token, String Amount){

        try {

            Retrofit retrofit  = new Retrofit.Builder()
                    .baseUrl("https://api.pay2all.in/v1/app/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            jsonConvertor = retrofit.create(JsonConvertor.class);

            Call<Pay2All_providers> call = jsonConvertor.getAllProviders("Bearer " + Token);
            call.enqueue(new Callback<Pay2All_providers>() {
                @Override
                public void onResponse(Call<Pay2All_providers> call, Response<Pay2All_providers> response) {

                    if(!response.isSuccessful()){
                        customToast.showToast("Error: " + response.code());
                        loadingDialog.stopLoading();
                        return;
                    }

                    Pay2All_providers pay2All_providers = response.body();

                    List<Pay2All_providers.Providers> providers = pay2All_providers.getProviders();

                    HashMap<String, String> rechargeProvider = new HashMap<>();

                    for(Pay2All_providers.Providers provider: providers){
                        rechargeProvider.put(provider.getProvider_name(), provider.getId());
                    }

                    String operator = btn_operator.getText().toString().trim();
                    String opId = rechargeProvider.get(btn_operator.getText().toString());

                    loadingDialog.stopLoading();
                    Log.d("Providers", "Providrs From API");
                    showAlertDialog(operator, Token, opId, Amount);

                }

                @Override
                public void onFailure(Call<Pay2All_providers> call, Throwable t) {
                    customToast.showToast("Error!  " + t.getMessage());
                    loadingDialog.stopLoading();
                }
            });
        }
        catch (Exception e){
            loadingDialog.stopLoading();
            customToast.showToast("Error! " + e.getMessage());
        }

    } // This will fetch all Providers
        private void showAlertDialog(String Operator, String Token, String opId, String Amount){

        try {
            // These are entities which shown on AlertDialog
            String number = tv_mobileNumber.getText().toString();
            String circle = btn_circle.getText().toString();
            Amount = btn_recahargeAmount.getText().toString().trim();
            String operator = btn_operator.getText().toString().trim();

            // make an object of proceedDialog
            proceedDialog = new proceedDialog((recharge_ui) requireActivity());
//            String operator = Operator + " id: " + opId;
            Dialog dialog = proceedDialog.showProceedDialog(number, circle, operator, Amount, Details_dialoge, Validity_dialoge);

            // proceedToPayment onClick Listner
            String finalAmount = Amount;
            dialog.findViewById(R.id.btn_dialogProceed).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if(isNetworkAvailable()){
//                        Toast.makeText((recharge_ui) requireActivity(), "Please Wait", Toast.LENGTH_SHORT).show();
//                    doRecharge(Token, number, Amount, opId);
                        getPaymentsPaytm(finalAmount);
                    }
                    else {
                        customToast.showToast("Please Check Your Internet Connection!...");
                    }

                }
            });

        }
        catch (Exception e){
            customToast.showToast("Error! " + e.getMessage());
        }

    }// This will show the alert Dailoge to user.
    // Get Payments before do Recharge Process
        private void getPayments(){
        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
                        .appendQueryParameter("pa", "918477055721@uco")
                        .appendQueryParameter("pn", "Recharge2me")
//                        .appendQueryParameter("mc", "your-merchant-code")
//                        .appendQueryParameter("tr", "your-transaction-ref-id")
                        .appendQueryParameter("tn", "Recharge")
                        .appendQueryParameter("am", "10.00")
                        .appendQueryParameter("cu", "INR")
//                        .appendQueryParameter("url", "your-transaction-url")
                        .build();


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);

        Intent chooser = Intent.createChooser(intent, "Pay with");

        if( null != chooser.resolveActivity(getContext().getPackageManager())){
            startActivityForResult(chooser, GOOGLE_PAY_REQUEST_CODE);
        }
        else {
                customToast.showToast("You Can't have any UPI app");
        }

        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
        startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);

    }// for gPay payments
        private void doRecharge(String Token, String Number, String Amount, String ProviderId){

            // TODO Check Amount please, CliendId should be unique for every recharge don't user UID as ClientId
            Token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjczMzBmZDcwYTBkNzlkYWI5NGE5NjdhZjkzN2ZkYzY4YzNkOTc3YzBlNmVmNjA2N2I4ZWUyZTM2ZTFlNWFkZDkzM2ZiNWViYWM4NTk5Y2Y4In0.eyJhdWQiOiIxIiwianRpIjoiNzMzMGZkNzBhMGQ3OWRhYjk0YTk2N2FmOTM3ZmRjNjhjM2Q5NzdjMGU2ZWY2MDY3YjhlZTJlMzZlMWU1YWRkOTMzZmI1ZWJhYzg1OTljZjgiLCJpYXQiOjE2MDg2MzA3MjYsIm5iZiI6MTYwODYzMDcyNiwiZXhwIjoxNjQwMTY2NzI2LCJzdWIiOiIzNzciLCJzY29wZXMiOltdfQ.l82eEI9f6d4l8NMapIgkhuPn-8_PEq7tO__5IGpL7-EdpnIrn6zIg7wg8qSagtTRkaqhjI2ZZ-ksEub1OHfdii8PNiFAu8tVEqymf9UVXNYA7Hc7JSwSB2luaiJOHASIIpOJYaDsxNTm7JEhiNODuTY5oNBNcrJcSBo7GUPK0tWxmMD6bmu0G-6BT5aw716Per_bW7GNQZk2IPsNNMYTC62QlwEs6__fWZf9Cd7EOvurWgNGtQdZQemPNU7cSqvtoLaCwTJDIPzuMI9MyeK5TzHZK3hrC1FZnDw2EWnQ3llUGRY8L4o02lj3SsnF9UQEkofHz9T06z8eVMNzAAwy-4H66AK9soIu8E9rAIddc5Qbg_xIbwRm2LEmCzcmFoW-lsakwZ10ePNUjcCVpSeU2LiA1_6eD8Ro43dHlkgKOrB6Ozpp0GURdn9qnlnrmYq28PGSpCAQjcz7S0lZZq7W8NIhWQjelSu4sny3mv_MdjQ_zeAvoZlS8M2rZbscTBCpyjKFMpfNju3kUx8Jky8ytIdmePL_5KgCrAprp1PMRkw5LyvquymZkoALPl-5liH4HxHCkvqsDBEHGgwftC95L_qdQe-gPgTlwMItk5gUmgxdhSMJde6CdoJMwuf88S5gOakaz-TLB4XwzAh3dlAWuCJU1zjPDQpPtDKEgj1pA-A";
            Number = "8477055721";
            Amount = "6995";
            ProviderId = "3";

            loadingDialog.startLoading();

            // Init Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://recharge2me.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Init JsonConverter Interface
            jsonConvertor = retrofit.create(JsonConvertor.class);

            Call<Pay2All_recharge> call = jsonConvertor.doRecharge(Token, Number, Amount, ProviderId, "UID_923749237492");
            call.enqueue(new Callback<Pay2All_recharge>() {
                @Override
                public void onResponse(Call<Pay2All_recharge> call, Response<Pay2All_recharge> response) {

                    if(!response.isSuccessful()){
                        customToast.showToast(String.valueOf(response.code()));
                        Log.d("Fail", "" + response.body() + response.code());
                        loadingDialog.stopLoading();
                        return;
                    }

                    Pay2All_recharge recharge = response.body();

                    Log.d("sucess", "" + response.body());

                    //    "status": 2,
                    //    "status_id": 2,
                    //    "utr": "",
                    //    "report_id": "",
                    //    "orderid": "",
                    //    "message": "Check Wallet"

                    tv_mF_planDetails.setText("Status: " + recharge.getStatus()
                            +"\nstatus_id: " + recharge.getStatus_id() +
                            "\nutr: " + recharge.getUtr() +
                            "\nreport_id: " + recharge.getReport_id() +
                            "\norderId: " + recharge.getOrderid() +
                            "\nmessage: " + recharge.getMessage() +
                            "\nresponceCode: " + response.code());

                    loadingDialog.stopLoading();

                }

                @Override
                public void onFailure(Call<Pay2All_recharge> call, Throwable t) {
    //                Toast.makeText((recharge_ui) requireActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    customToast.showToast(t.getMessage());
                    Log.d("exceptionOnFail", "" + t.getMessage());
                    loadingDialog.stopLoading();
                }
            });

    }// Do Recharge


    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("_ddMMyyyyHHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    // Paytm
    private void getPaymentsPaytm(String Amount){

        loadingDialog.startLoading();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://recharge2me.herokuapp.com/paytm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonConvertor = retrofit.create(JsonConvertor.class);

        char[] a = Amount.toCharArray();
        String amount = "";
        for(int i=1; i<a.length; i ++){
            amount += a[i];
        }

        if(amount.contains(".")){
        }
        else {
            amount += ".00";
        }

        String orderId = getDateTime();

        Log.i("orderId", orderId);

        Call<PaytmToken> call = jsonConvertor.getPaytmTransactionToken(orderId, amount);

        String finalAmount = amount;

        call.enqueue(new Callback<PaytmToken>() {
            @Override
            public void onResponse(Call<PaytmToken> call, Response<PaytmToken> response) {
                if(!response.isSuccessful()){
                    customToast.showToast("Error! code: " + response.code());
                    loadingDialog.stopLoading();
                    return;
                }

                PaytmToken data = response.body();
                PaytmToken.Body body = data.getBody();
                String txnTokken = body.getTxnToken();

                Log.i("Tokken", "Token: " + txnTokken);

                startPaytmOrder(txnTokken, finalAmount, orderId);

            }

            @Override
            public void onFailure(Call<PaytmToken> call, Throwable t) {
                customToast.showToast("Error! " + t.getMessage());
                loadingDialog.stopLoading();
            }
        });
    }

    private void startPaytmOrder(String token, String amount, String orderId){

        Log.i("orderId", orderId + "Token: " + token);

        String host = "https://securegw-stage.paytm.in/";
        String callBackUrl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+orderId;
        String url = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
        String mid = "doAbsM13735083718155";

        PaytmOrder paytmOrder = new PaytmOrder(orderId, mid, token, "10.00", callBackUrl);

        loadingDialog.stopLoading();


        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle bundle) {
                Toast.makeText((recharge_ui) requireActivity(), "Payment Transaction response " + bundle.toString(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void networkNotAvailable() {
                customToast.showToast("nerwork not avialable");
            }

            @Override
            public void onErrorProceed(String s) {
                customToast.showToast("Error! " + s);
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                customToast.showToast("Error! " + s);
            }

            @Override
            public void someUIErrorOccurred(String s) {
                customToast.showToast("Error! " + s);
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                customToast.showToast("Error! " + s + "\n" + s1);
            }

            @Override
            public void onBackPressedCancelTransaction() {
                        customToast.showToast("Cancled");
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                customToast.showToast(s);
            }
        });// code statement);

        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction((recharge_ui) requireActivity(), PAYTM_RESULT_CODE);
    }




    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                =  (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    // This is an example of Transaction Data in firebase
    private void setOrderData(){

        String orderId = mAuth.getUid() +"_"+ getDateTime();

        // clien_id is orderId
        // orderid is pay2all unique orderId
        Pay2All_rechargeFirebase recharge = new Pay2All_rechargeFirebase("0", "1", "54204", "APR2012271021230038",
                "2424166", "success", "699", "8477055721", orderId, tv_mF_planDetails.getText().toString().trim());

        Pay2All_status status = new Pay2All_status("1", "1", "2438098", "8126126759", "699",
                "1578055287", orderId);

        Paytm_initiateTransaction initiateTransaction = new Paytm_initiateTransaction("S", "0000", "Success",
                "fe795335ed3049c78a57271075f2199e1526969112097");
        Paytm_transactonStatus transactonStatus = new Paytm_transactonStatus("TXN_SUCCESS", "01", "Txn Success",
                "fe795335ed3049c78a57271075f2199e1526969112097", orderId, "100.00", "100.00", "2019-02-20 12:35:20.0");

        Paytm_refund refund = new Paytm_refund("2019-09-02 12:31:49.0", orderId, orderId+ "_refund", "PENDING",
                "601", "Refund request was raised for this transaction. But it is pending state",
                "PAYTM_REFUND_ID", "PAYTM_TRANSACTION_ID", "100.00");

        Paytm_refundStatus refundStatus = new Paytm_refundStatus(orderId, "SUCCESS", "TXN_SUCCESS", "10",
                "Refund Successfull", "2019-05-01 19:25:41.0", "2019-05-01 19:27:25.0",
                "SUCCESS", "TO_SOURCE", "2019-05-02", "100.00", orderId+"_refund",
                "100.00", "PAYTM_REFUND_ID", "PAYTM_TRANSACTION_ID");

        Order order = new Order(recharge, status, initiateTransaction, transactonStatus, refund, refundStatus);

        db.collection("USERS").document(mAuth.getUid()).collection("Transactions")
                .document(orderId)
                .set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                        public void onSuccess(Void aVoid) {
                        Toast.makeText((recharge_ui) requireActivity(),"save",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                        public void onFailure(@NonNull Exception e) {
                        Toast.makeText((recharge_ui) requireActivity(),"fail",Toast.LENGTH_SHORT).show();
                    }
                });

    }


    // operatod Data/codes
    private String operatorData(String key){

        Map<String, String> op = new HashMap<>();

        op.put("AIRTEL", "1");
        op.put("Vodafone Idea", "10");
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