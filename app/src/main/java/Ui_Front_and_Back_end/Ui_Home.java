package Ui_Front_and_Back_end;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.recharge2mePlay.recharge2me.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Global.custom_Loading_Dialog.CustomToast;
import Global.custom_Loading_Dialog.LoadingDialog;
import LogInSignIn_Entry.EntryActivity;
import Ui_Front_and_Back_end.Adapters.TransactionAdapter;
import local_Databasse.providersData.Database_providers;
import local_Databasse.providersData.Entity_providers;
import recahrge.DataTypes.rechargeDataTypes.Pay2All_authToken;
import recahrge.DataTypes.rechargeDataTypes.Pay2All_providers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import Retrofit.JsonConvertor;

public class Ui_Home extends Fragment {

    View view;

    TextView tv_Home_Transacyion;

    ImageView iv_prePaid,
              iv_postPaid;

    NestedScrollView ns_home;

    RecyclerView rv_Home_Transaction;
    TransactionAdapter transactionAdapter;

    // SharedPreferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    int touchFlag = 1;
//    int providersFlag = 1;

    Animation animation;

    LoadingDialog loadingDialog;
    CustomToast toast;


    public Ui_Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_ui__home, container, false);

        // TextView
        tv_Home_Transacyion = view.findViewById(R.id.tv_Home_Transactions);

        // ImageView
        iv_postPaid = view.findViewById(R.id.iv_postPaid);
        iv_prePaid = view.findViewById(R.id.iv_prePaid);

        // RecyclerView
        rv_Home_Transaction = view.findViewById(R.id.rv_home_transaction);

        // NestedScrollView
        ns_home = view.findViewById(R.id.ns_home);

        // Init onClick Animation
        animation = AnimationUtils.loadAnimation((Main_UserInterface) requireActivity(), R.anim.click);

        // custom
        loadingDialog = new LoadingDialog(getActivity());
        toast = new CustomToast(getActivity());

        // SharedPrefrences
        sharedPreferences = getActivity().getSharedPreferences("Providers", Context.MODE_PRIVATE);
        String check = sharedPreferences.getString("ProvidersData", "");
        Log.d("shardePrefrences", "msg" + check);

        tv_Home_Transacyion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutFromGoogle();
            }
        });
        iv_prePaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepaid();
            }
        });
        iv_postPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postpaid();
            }
        });

        // These are for anime back the drawe if it is visible
        ns_home.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    animateNavDrawer();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    animateNavDrawer();
                }

                return false;
            }
        });
        rv_Home_Transaction.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    animateNavDrawer();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    animateNavDrawer();
                }

                return false;
            }
        });

        setDataOnRecyclerView();

        if(isNetworkAvailable()){
            if(check.equals("Get")){
                getAuthToken_pay2All();
            }
        }
        else {
            toast.showToast("Please check your Internet connection");
        }


        return view;
    }

    private void getAuthToken_pay2All(){
        loadingDialog.startLoading();

        // Init Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pay2all.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Init JsonConverter Interface
        JsonConvertor jsonConvertor = retrofit.create(JsonConvertor.class);

        Map<String, String> params = new HashMap<>();
        params.put("email", "mudssira01@gmail.com");
        params.put("password", "4nVztc");

        Call<Pay2All_authToken> call = jsonConvertor.getAuthToken(params);

        call.enqueue(new Callback<Pay2All_authToken>() {
            @Override
            public void onResponse(Call<Pay2All_authToken> call, Response<Pay2All_authToken> response) {
                if(!response.isSuccessful()){
                    toast.showToast("Please re-open Application!...");
                    loadingDialog.stopLoading();
                    return;
                }

                try {
                    Pay2All_authToken pay2All_authToken = response.body();

                    String authToken = pay2All_authToken.getAccess_token();


                    if(isNetworkAvailable()){
                        getAllProvides(authToken);
                    }
                    else {
                        toast.showToast("Please Check Your Internet Connection!...");
                        loadingDialog.stopLoading();
                    }
                }
                catch (Exception e){
                    loadingDialog.stopLoading();
                    toast.showToast("Error! " + e.getMessage());
                }

            }
            @Override
            public void onFailure(Call<Pay2All_authToken> call, Throwable t) {
                toast.showToast("Error! " + t.getMessage());
                loadingDialog.stopLoading();
            }
        });
    }// This will fetch the Auth Token
    private void getAllProvides(String Token){

        Retrofit retrofit  = new Retrofit.Builder()
                    .baseUrl("https://api.pay2all.in/v1/app/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        JsonConvertor jsonConvertor = retrofit.create(JsonConvertor.class);

        Call<Pay2All_providers> call = jsonConvertor.getAllProviders("Bearer " + Token);
        call.enqueue(new Callback<Pay2All_providers>() {
            @Override
            public void onResponse(Call<Pay2All_providers> call, Response<Pay2All_providers> response) {

                if(!response.isSuccessful()){
                    toast.showToast("Please re-open Application...");
                    loadingDialog.stopLoading();
                    return;
                }

                try {
                    Pay2All_providers pay2All_providers = response.body();

                    List<Pay2All_providers.Providers> providers = pay2All_providers.getProviders();

                    editor = sharedPreferences.edit();
                    editor.putString("ProvidersData", "Have");
                    editor.putString("Token", Token);
                    editor.apply();

                    loadingDialog.stopLoading();

                    saveInDatabase(providers);
                }
                catch (Exception e){
                    toast.showToast("Error! " + e.getMessage());
                    loadingDialog.stopLoading();
                }
            }
            @Override
            public void onFailure(Call<Pay2All_providers> call, Throwable t) {
                toast.showToast("providersFail " + t.getMessage());
                loadingDialog.stopLoading();
            }
        });
    } // This will fetch all Providers

    // it will save providers Data in Database
    private void saveInDatabase(List<Pay2All_providers.Providers> providers){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Entity_providers> list = new ArrayList<>();
                for(Pay2All_providers.Providers provider: providers){
                    Entity_providers p = new Entity_providers(0, provider.getId(), provider.getProvider_name());
                    list.add(p);
                }
                Database_providers.getInstance(getContext())
                        .providersDao()
                        .insertProvider(list);
            }
        }).start();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                =  (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Anime the drawer if it is visible
    private void animateNavDrawer(){
        NavigationView nav_drawer = getActivity().findViewById(R.id.nav_drawer);

        int a = nav_drawer.getVisibility();
        if(a == 0){
            if(touchFlag == 1){
                touchFlag = 0;

                nav_drawer.animate()
                        .alpha(0f)
                        .setDuration(200L)
                        .translationXBy(-100f)
                        .setListener(null);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nav_drawer.setVisibility(View.INVISIBLE);
                        touchFlag = 1;
                    }
                }, 200);
            }
        }
    }

    // Set the data on RecyclerView
    private void setDataOnRecyclerView(){

        List<String> list = new ArrayList<>();
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");
        list.add("hi");

        transactionAdapter = new TransactionAdapter( (Main_UserInterface) requireActivity(), list, getActivity());
        rv_Home_Transaction.setAdapter(transactionAdapter);
        rv_Home_Transaction.setLayoutManager(new LinearLayoutManager((Main_UserInterface) requireActivity()));

    }

    private void signOutFromGoogle() {

        // TODO default-web-client-id is added esi

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient((Main_UserInterface) requireActivity(), gso);

        // This code clears which account is connected to the app. To sign in again, the user must choose their account again.
        mGoogleSignInClient.signOut()
                .addOnCompleteListener((Main_UserInterface) requireActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // It will go back on LogIn-SignIn Page.
                        Intent intent = new Intent((Main_UserInterface) requireActivity(), EntryActivity.class);
                        startActivity(intent);

                        Toast.makeText((Main_UserInterface) requireActivity(), "You are Logged Out...", Toast.LENGTH_SHORT).show();
                    }
                });


    } // End of signOutFromGoogle method;

    private void prepaid(){

        iv_prePaid.startAnimation(animation);

        Navigation.findNavController(view).navigate(R.id.action_ui_Home_to_recahrge_ui);
    }// End of prePaid method;

    private void postpaid(){

        iv_postPaid.startAnimation(animation);
        Navigation.findNavController(view).navigate(R.id.action_ui_Home_to_recahrge_ui);

    } // End of postpaid method;
}