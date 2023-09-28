package com.recharge2mePlay.recharge2me.recharge.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.recharge2mePlay.recharge2me.R;

import local_Databasse.entity_numberDetails;

import com.recharge2mePlay.recharge2me.recharge.ui.activities.RechargeUiActivity;
import com.recharge2mePlay.recharge2me.recharge.ui.adapters.CircleAdapter;

public class RechargeCircleFragment extends Fragment {

    String circle[];
    String number;

    RecyclerView rv_circle;

    Animation animation;

    View view;

    public RechargeCircleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recharge_circle, container, false);

        circle = getResources().getStringArray(R.array.circle);

        rv_circle = view.findViewById(R.id.rv_circle);

        CircleAdapter circle_adapter = new CircleAdapter((RechargeUiActivity) requireActivity(), circle);

        // Init onClick Animation
        animation = AnimationUtils.loadAnimation((RechargeUiActivity) requireActivity(), R.anim.click);

        rv_circle.setAdapter(circle_adapter);
        rv_circle.setLayoutManager(new LinearLayoutManager((RechargeUiActivity) requireActivity()));

        number = RechargeCircleFragmentArgs.fromBundle(getArguments()).getUserNumber();

        // click Listener
        rv_circle.addOnItemTouchListener(new CircleAdapter.RecyclerTouchListener((RechargeUiActivity) requireActivity(),
                rv_circle, new CircleAdapter.ClickListener() {

            @Override
            public void onClick(View view, final int position) {

//                Toast.makeText((recahrge_ui) requireActivity(), "gg:"+circle[position], Toast.LENGTH_SHORT).show();

                view.startAnimation(animation);

                entity_numberDetails a = null;

                RechargeCircleFragmentDirections.ActionRechargeCircleToMobileDetailsFinder
                        action = RechargeCircleFragmentDirections
                        .actionRechargeCircleToMobileDetailsFinder("formCircle", number);

                action.setUserCircle(circle[position]);

                Navigation.findNavController(view).navigate(action);
            }

            // This is for LongPress Events
//            @Override
//            public void onLongPress(View view, int position){
//                Toast.makeText((recahrge_ui) requireActivity(), " LongPress on: " + circle[position] , Toast.LENGTH_SHORT).show();
//            }

        }));


        return view;
    }
}