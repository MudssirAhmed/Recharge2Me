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
import android.widget.TextView;

import com.recharge2mePlay.recharge2me.R;

import com.recharge2mePlay.recharge2me.constants.AppConstants;
import com.recharge2mePlay.recharge2me.recharge.ui.activities.RechargeUiActivity;
import com.recharge2mePlay.recharge2me.recharge.ui.adapters.OperatorAdapter;


public class RechargeSelectOperatorFragment extends Fragment {

    View view;
    TextView tv_warning;
    RecyclerView rv_selectOperator;

    Animation animation;

    String recOp[];
    int recImg[] = {  R.drawable.idea,
            R.drawable.idea,
            R.drawable.airtel,
            R.drawable.jio,
            R.drawable.bsnl,
    };


    public RechargeSelectOperatorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recharge_select_operator, container, false);
        tv_warning = view.findViewById(R.id.tv_selectOperator_Header);


        rv_selectOperator = view.findViewById(R.id.rv_selectOperator);

        recOp = getResources().getStringArray(R.array.operator);

        // Init onClick Animation
        animation = AnimationUtils.loadAnimation((RechargeUiActivity) requireActivity(), R.anim.click );

        OperatorAdapter adapter = new OperatorAdapter(recOp,recImg,(RechargeUiActivity) requireActivity());
        rv_selectOperator.setAdapter(adapter);
        rv_selectOperator.setLayoutManager(new LinearLayoutManager((RechargeUiActivity) requireActivity()));

        String number = RechargeSelectOperatorFragmentArgs.fromBundle(getArguments()).getUserNoForOp();

        rv_selectOperator.addOnItemTouchListener(new OperatorAdapter.RecyclerViewListner((RechargeUiActivity) requireActivity(), rv_selectOperator,
                new OperatorAdapter.ClickListener() {

                    @Override
                    public void onClick(View v, int position) {
                        v.startAnimation(animation);
                        Navigation.findNavController(view).getPreviousBackStackEntry().getSavedStateHandle().set(AppConstants.SELECTED_OPERATOR, recOp[position]);
                        Navigation.findNavController(view).popBackStack();
                    }
                }));

        return view;
    }

}