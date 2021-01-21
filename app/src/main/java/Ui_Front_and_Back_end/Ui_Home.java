package Ui_Front_and_Back_end;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.recharge2mePlay.recharge2me.R;

import java.util.ArrayList;
import java.util.List;

import Global.customAnimation.MyAnimation;
import Global.custom_Loading_Dialog.CustomToast;
import Global.custom_Loading_Dialog.LoadingDialog;
import Global.custom_Loading_Dialog.proceedDialog;
import LogInSignIn_Entry.DataTypes.CreateAccount_userDetails;
import LogInSignIn_Entry.DataTypes.User_googleAndOwn;
import Ui_Front_and_Back_end.Adapters.TransactionAdapter;
import recahrge.DataTypes.rechargeFirbase.Order;

public class Ui_Home extends Fragment {

    View view;

    TextView tv_rewards;

    ImageView iv_prePaid,
              iv_postPaid;

    ProgressBar pb_uiHome_transactions;

    NestedScrollView ns_home;

    RecyclerView rv_Home_Transaction;
    TransactionAdapter transactionAdapter;

    // SharedPreferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    int touchFlag = 1;

    MyAnimation animation;

    // Customs
    LoadingDialog loadingDialog;
    CustomToast toast;

    // Firebase
    FirebaseAuth mAuth;
    FirebaseFirestore db;


    public Ui_Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_ui__home, container, false);

//        //Facebook adds
//        adView = new AdView(getActivity(), "IMG_16_9_APP_INSTALL#748059209467827_748063486134066", AdSize.BANNER_HEIGHT_50);
//        // Find the Ad Container
//        LinearLayout adContainer = (LinearLayout) view.findViewById(R.id.fragContainer);
//        // Add the ad view to your activity layout
//        adContainer.addView(adView);
//        // Request an ad
//        adView.loadAd();

        //TextView
        tv_rewards = view.findViewById(R.id.tv_home_rewards);

        // ImageView
        iv_postPaid = view.findViewById(R.id.iv_postPaid);
        iv_prePaid = view.findViewById(R.id.iv_prePaid);

        // RecyclerView
        rv_Home_Transaction = view.findViewById(R.id.rv_home_transaction);

        // NestedScrollView
        ns_home = view.findViewById(R.id.ns_home);

        // progressBar
        pb_uiHome_transactions = view.findViewById(R.id.pb_uiHome_transactions);

        // Init onClick Animation
        animation = new MyAnimation();

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // custom
        loadingDialog = new LoadingDialog(getActivity());
        toast = new CustomToast(getActivity());

        // SharedPrefrences
        sharedPreferences = getActivity().getSharedPreferences("Providers", Context.MODE_PRIVATE);
        String check = sharedPreferences.getString("ProvidersData", "");
        Log.d("shardePrefrences", "msg" + check);


        iv_prePaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    prePaid(iv_prePaid);
                }
            }
        });
//        iv_postPaid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });


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


        getRechargeData();
        setRewardsData();

        return view;
    }// End of onCreate()

    private void setRewardsData(){
        DocumentReference docRef = db.collection("USERS").document(mAuth.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User_googleAndOwn user = documentSnapshot.toObject(User_googleAndOwn.class);
                CreateAccount_userDetails userDetails = user.getUser_details();
                String rewards = userDetails.getRewards();
                tv_rewards.setText("₹ " + rewards + ".00");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                toast.showToast("Error! " + e.getMessage());
            }
        });
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

    private void getRechargeData(){
        CollectionReference colRef = db.collection("USERS").document(mAuth.getUid()).collection("Transactions");

        Query query = colRef.orderBy("orderId", Query.Direction.DESCENDING).limit(5);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Order> list = new ArrayList<>();

                for(QueryDocumentSnapshot q: queryDocumentSnapshots){
                    list.add(q.toObject(Order.class));
                }
                setDataOnRecyclerView(list);
                pb_uiHome_transactions.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pb_uiHome_transactions.setVisibility(View.GONE);
                toast.showToast("Error!  " + e.getMessage());
            }
        });

    }

    // Set the data on RecyclerView
    private void setDataOnRecyclerView(List<Order> list){

        transactionAdapter = new TransactionAdapter( (Main_UserInterface) requireActivity(), list, getActivity(), view, "Home");
        rv_Home_Transaction.setAdapter(transactionAdapter);
        rv_Home_Transaction.setLayoutManager(new LinearLayoutManager((Main_UserInterface) requireActivity()));

    }


    private void prePaid(View view){
        animation.onClickAnimation(view);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(view).navigate(R.id.action_ui_Home_to_recahrge_ui);
            }
        }, 120);

    }// End of prePaid method;

}