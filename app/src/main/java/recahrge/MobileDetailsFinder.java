package recahrge;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

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

import com.google.android.gms.tasks.Task;
import com.recharge2mePlay.recharge2me.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import Retrofit.JsonConvertor;
import custom_Loading_Dialog.CustomToast;
import custom_Loading_Dialog.LoadingDialog;
import custom_Loading_Dialog.proceedDialog;
import local_Databasse.Dao_numberDetails;
import local_Databasse.Database_numberData;
import local_Databasse.Database_numberJava;
import local_Databasse.entity_numberDetails;
import local_Databasse.numberViewModel;
import recahrge.DataTypes.rechargeDataTypes.Pay2All_authToken;
import recahrge.DataTypes.rechargeDataTypes.Pay2All_providers;
import recahrge.DataTypes.rechargeDataTypes.Pay2All_recharge;
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

    ImageView iv_rechargeOperator;

    // Strings:
    String Details_dialoge = ""; // This is for Details shown in Alert Dialog
    String Validity_dialoge = ""; // This is for Validity shown in Alert Dialog
    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"; // This is for GooglePay Payments

    // Integers:
    final int GOOGLE_PAY_REQUEST_CODE = 123;
    final int GOTO_PLAN = 8477;
    int circleId;

    private local_Databasse.numberViewModel numberViewModel;

    Animation animation;


    // Loading Dialog
    LoadingDialog loadingDialog;
    proceedDialog proceedDialog;


    // Retrofit & JsonConverter
    private JsonConvertor jsonConvertor;
//    private final String base_Url = "http://api.rechapi.com/";

    CustomToast customToast;

    Context context;
    SharedPreferences sharedPreferences;
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

        // Init onClick Animation
        animation = AnimationUtils.loadAnimation((recharge_ui) requireActivity(), R.anim.click);

        // Init custom Taost
        customToast = new CustomToast((recharge_ui) requireActivity());


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

//                getPayments();

                // Use this for Add and update Data in Database if Data can't exist then it automatically add the data in Database
                // and if data is already already present in Database and user update then it automatically update Data.


                 if(btn_recahargeAmount.getText().toString().equals("Amount") || btn_recahargeAmount.getText().toString().isEmpty())
                     customToast.showToast("Please select plan First!");
                 else {
                     findDataInDataBase(tv_mobileNumber.getText().toString());
//                     getAuthToken_pay2All(btn_recahargeAmount.getText().toString());
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

        Intent intent = new Intent((recharge_ui) requireActivity(), getRecahrgePlan.class );

        intent.putExtra("op", btn_operator.getText().toString());
        intent.putExtra("circle", btn_circle.getText().toString());
        intent.putExtra("number", tv_mobileNumber.getText().toString());
//        intent.putExtra("circleId", String.valueOf(circleId));

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
                    customToast.showToast("TokkenCode: " + response.code());
                    loadingDialog.stopLoading();
                    return;
                }

                Pay2All_authToken pay2All_authToken = response.body();

                String authToken = pay2All_authToken.getAccess_token();
                Pay2All_authToken.userPay2All user = pay2All_authToken.getUser();
                Pay2All_authToken.userPay2All.Balance balance = user.getBalance();

                Toast.makeText((recharge_ui) requireActivity(), "balance: "+ balance.getUser_balance(), Toast.LENGTH_SHORT).show();
                loadingDialog.stopLoading();

//                getAllProvides(authToken, amount);

            }

            @Override
            public void onFailure(Call<Pay2All_authToken> call, Throwable t) {
                customToast.showToast("TokkenFail: " + t.getMessage());
                loadingDialog.stopLoading();
            }
        });
    }// This will fetch the Auth Token
        private void getAllProvides(String Token, String Amount){

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
                    customToast.showToast("providersCode " + response.code());
                    loadingDialog.stopLoading();
                    return;
                }

                Pay2All_providers pay2All_providers = response.body();

                List<Pay2All_providers.Providers> providers = pay2All_providers.getProviders();

                HashMap<String, String> rechargeProvider = new HashMap<>();

                for(Pay2All_providers.Providers provider: providers){
                    rechargeProvider.put(provider.getProvider_name(), provider.getId());
                }

                HashMap<String, String> opeId = new HashMap<>();
                opeId.put("1", "AIRTEL");
                opeId.put("2", "VODAFONE");
                opeId.put("3", "IDEA");
                opeId.put("8", "BSNL");
                opeId.put("88", "Jio");


                String operator = rechargeProvider.get(btn_operator.getText().toString());
                String opId = rechargeProvider.get(btn_operator.getText().toString());

                loadingDialog.stopLoading();
                showAlertDialog(operator, Token, opId, Amount);

            }

            @Override
            public void onFailure(Call<Pay2All_providers> call, Throwable t) {
                customToast.showToast("providersFail " + t.getMessage());
                loadingDialog.stopLoading();
            }
        });

    } // This will fetch all Providers
        private void showAlertDialog(String Operator, String Token, String opId, String Amount){

        // These are entities which shown on AlertDialog
        String number = tv_mobileNumber.getText().toString();
        String circle = btn_circle.getText().toString();

        // make an object of proceedDialog
        proceedDialog = new proceedDialog((recharge_ui) requireActivity());

        String operator = Operator + " id: " + opId;

        Dialog dialog = proceedDialog.showProceedDialog(number, circle, operator, Amount, Details_dialoge, Validity_dialoge);

        // proceedToPayment onClick Listner
        dialog.findViewById(R.id.btn_dialogProceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText((recharge_ui) requireActivity(), "Please Wait", Toast.LENGTH_SHORT).show();
                doRecharge(Token, number, Amount, opId);
            }
        });

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

        // TODO: Make unique Client Id or user UID from firebase

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
                    Log.d("Fail", "" + response.body());
                    loadingDialog.stopLoading();
                    return;
                }

                Pay2All_recharge recharge = response.body();

                Log.d("sucess", "" + response.body());

                tv_mF_planDetails.setText("Status: " + recharge.getStatus() + "\n" + recharge.getMessage());

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







    // operatod Data/codes
    public String operatorData(String key){

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