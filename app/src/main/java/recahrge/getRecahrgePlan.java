package recahrge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.recharge2me.R;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

public class getRecahrgePlan extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    String operator,
            circle,
            Number,
            circleId,
            opCode;

    TextView tv_planOperator;

    Intent intent;
    String Amount,
           Validity,
           Details;

    public void getRecahrgePlan(String Amount, String Validity, String Details){
            this.Amount = Amount;
            this.Validity = Validity;
            this.Details = Details;
    }

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

        tv_planOperator = findViewById(R.id.tv_planOperator);

        intent = new Intent();
        String operator = getIntent().getExtras().getString("op");
        String circle = getIntent().getExtras().getString("circle");
        Number = getIntent().getExtras().getString("number");
        circleId = getIntent().getExtras().getString("circleId");
        opCode = getIntent().getExtras().getString("opCode");

        tv_planOperator.setText("for "+ operator + " " +circle + " " + opCode);


    }

    public void sendPlanData(){
        intent.putExtra("Amount", Amount);
        intent.putExtra("Validity", Validity);
        intent.putExtra("Details", Details);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public String getOpCode(){
        return opCode;
    }

    public String getCircleId(){
        return circleId;
    }

}