package recahrge;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recharge2me.R;

public class recharge_circle extends Fragment {

    String circle[];

    RecyclerView rv_circle;

    View view;

    public recharge_circle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_recharge_circle, container, false);

        circle = getResources().getStringArray(R.array.circle);

        rv_circle = view.findViewById(R.id.rv_circle);

        circle_Adapter circle_adapter = new circle_Adapter((recahrge_ui) requireActivity(), circle);
        rv_circle.setAdapter(circle_adapter);
        rv_circle.setLayoutManager(new LinearLayoutManager((recahrge_ui) requireActivity()));

        return view;
    }
}