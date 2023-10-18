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

import com.recharge2mePlay.recharge2me.constants.AppConstants;
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
        animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.click);

        rv_circle.setAdapter(circle_adapter);
        rv_circle.setLayoutManager(new LinearLayoutManager((RechargeUiActivity) requireActivity()));

        number = RechargeCircleFragmentArgs.fromBundle(getArguments()).getUserNumber();

        // click Listener
        rv_circle.addOnItemTouchListener(new CircleAdapter.RecyclerTouchListener(requireActivity(),
                rv_circle, (view, position) -> {
                    view.startAnimation(animation);
                    Navigation.findNavController(view).getPreviousBackStackEntry().getSavedStateHandle().set(AppConstants.SELECTED_CIRCLE, circle[position]);
                    Navigation.findNavController(view).popBackStack();
                }));


        return view;
    }
}