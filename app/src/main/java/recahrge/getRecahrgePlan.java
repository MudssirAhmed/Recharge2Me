package recahrge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.recharge2me.R;
import com.google.android.material.tabs.TabLayout;

public class getRecahrgePlan extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recahrge_plan);


        tabLayout = findViewById(R.id.tabLayout_plans);
        viewPager = findViewById(R.id.viewPager_plans);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new Spl_Fragment(), "Special recharge");
        adapter.addFragment(new Data_Fragment(), "DATA");
        adapter.addFragment(new Ftt_Fragment(), "Full Talk Time");
        adapter.addFragment(new Tup_Fragment(), "Top Up");
        adapter.addFragment(new Rmg_Fragment(), "Roaming");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.perpleLight), getResources().getColor(R.color.perple));

    }


    public void sendData(View view) {
        Intent intent = new Intent();
        intent.putExtra("result", "plan");
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}