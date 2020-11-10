package recahrge;

import android.content.Context;
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
import android.widget.Toast;

import com.example.recharge2me.R;
import recahrge.myAdapters.circle_Adapter;

public class recharge_circle extends Fragment {

    String circle[];
    String number;

    RecyclerView rv_circle;

    Animation animation;

    View view;

    public recharge_circle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recharge_circle, container, false);

        circle = getResources().getStringArray(R.array.circle);

        rv_circle = view.findViewById(R.id.rv_circle);

        circle_Adapter circle_adapter = new circle_Adapter((recahrge_ui) requireActivity(), circle);

        // Init onClick Animation
        animation = AnimationUtils.loadAnimation((recahrge_ui) requireActivity(), R.anim.click);

        rv_circle.setAdapter(circle_adapter);
        rv_circle.setLayoutManager(new LinearLayoutManager((recahrge_ui) requireActivity()));

        number = recharge_circleArgs.fromBundle(getArguments()).getUserNumber();

        // click Listener
        rv_circle.addOnItemTouchListener(new circle_Adapter.RecyclerTouchListener((recahrge_ui) requireActivity(),
                rv_circle, new circle_Adapter.ClickListener() {

            @Override
            public void onClick(View view, final int position) {

//                Toast.makeText((recahrge_ui) requireActivity(), "gg:"+circle[position], Toast.LENGTH_SHORT).show();

                view.startAnimation(animation);

                recharge_circleDirections.ActionRechargeCircleToMobileDetailsFinder
                        action = recharge_circleDirections.actionRechargeCircleToMobileDetailsFinder("formCircle", number);

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