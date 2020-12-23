package recahrge;

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

import recahrge.myAdapters.operatorAdapter;


public class recharge_selectOperator extends Fragment {

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


    public recharge_selectOperator() {
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
        animation = AnimationUtils.loadAnimation((recharge_ui) requireActivity(), R.anim.click );

        operatorAdapter adapter = new operatorAdapter(recOp,recImg,(recharge_ui) requireActivity());
        rv_selectOperator.setAdapter(adapter);
        rv_selectOperator.setLayoutManager(new LinearLayoutManager((recharge_ui) requireActivity()));

        String number = recharge_selectOperatorArgs.fromBundle(getArguments()).getUserNoForOp();

        rv_selectOperator.addOnItemTouchListener(new operatorAdapter.RecyclerViewListner((recharge_ui) requireActivity(), rv_selectOperator,
                new operatorAdapter.ClickListener() {

                    @Override
                    public void onClick(View v, int position) {

                        v.startAnimation(animation);

                        recharge_selectOperatorDirections.ActionRechargeSelectOperatorToMobileDetailsFinder
                                        action = recharge_selectOperatorDirections
                                .actionRechargeSelectOperatorToMobileDetailsFinder("formCircle", number);

                        action.setOperator(recOp[position]);

                        Navigation.findNavController(view).navigate(action);

                    }
                }));

        return view;
    }

}