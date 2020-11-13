package recahrge;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recharge2me.R;
import com.google.android.material.tabs.TabLayout;

public class recharge_Plan extends Fragment {

    View view;





    public recharge_Plan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_recharge__plan, container, false);

        return view;
    }
}