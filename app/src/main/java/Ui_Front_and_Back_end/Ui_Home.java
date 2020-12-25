package Ui_Front_and_Back_end;

import android.content.Intent;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import LogInSignIn_Entry.EntryActivity;
import Ui_Front_and_Back_end.Adapters.TransactionAdapter;

public class Ui_Home extends Fragment {

    View view;

    TextView tv_Home_Transacyion;

    ImageView iv_prePaid,
              iv_postPaid;

    NestedScrollView ns_home;

    RecyclerView rv_Home_Transaction;
    TransactionAdapter transactionAdapter;

    int touchFlag = 1;

    Animation animation;



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

        return view;
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

    public void signOutFromGoogle()
    {

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