package recahrge.plans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.recharge2mePlay.recharge2me.R;
import com.google.android.material.tabs.TabLayout;

import Global.customAnimation.MyAnimation;
import recahrge.plans.Data_Fragment;
import recahrge.plans.Ftt_Fragment;
import recahrge.plans.Rmg_Fragment;
import recahrge.plans.Spl_Fragment;
import recahrge.plans.Tup_Fragment;
import recahrge.plans.ViewPagerAdapter;

public class getRecahrgePlan extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private ProgressBar progressBar;

    String operator,
            circle,
            Number,
            circleId,
            opCode;

    TextView tv_planOperator;

    ImageView iv_plansBack;

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
        progressBar = findViewById(R.id.progressBar_getRechargePlan);
        iv_plansBack = findViewById(R.id.iv_plan_back);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        try {
            adapter.addFragment(new Spl_Fragment(), "Special recharge");
            adapter.addFragment(new Data_Fragment(), "DATA");
            adapter.addFragment(new Ftt_Fragment(), "Full Talk Time");
            adapter.addFragment(new Tup_Fragment(), "Top Up");
            adapter.addFragment(new Rmg_Fragment(), "Roaming");

            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setTabTextColors(getResources().getColor(R.color.perpleLight), getResources().getColor(R.color.perple));
        }
        catch (Exception e){
            Log.i("Fragment", "Fragment" + e.getMessage());
        }

        try {
            tv_planOperator = findViewById(R.id.tv_planOperator);
            intent = new Intent();

            String operator = getIntent().getExtras().getString("op");
            String circle = getIntent().getExtras().getString("circle");
            Number = getIntent().getExtras().getString("number");
            circleId = getIntent().getExtras().getString("circleId");
            opCode = getIntent().getExtras().getString("opCode");

            tv_planOperator.setText("for "+ operator + " " +circle + " " + opCode);

            iv_plansBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyAnimation animation = new MyAnimation();
                    animation.onClickAnimation(iv_plansBack);
                    finish();
                    overridePendingTransition(R.anim.to_left_pop, R.anim.from_right_pop);
                }
            });

        }
        catch (Exception e){
            Log.i("FragmentAnother", "Fragment" + e.getMessage());
        }
    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
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