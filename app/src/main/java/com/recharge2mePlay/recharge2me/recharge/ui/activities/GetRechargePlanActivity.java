package com.recharge2mePlay.recharge2me.recharge.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.recharge2mePlay.recharge2me.R;

import com.recharge2mePlay.recharge2me.constants.AppConstants;
import com.recharge2mePlay.recharge2me.recharge.models.MobileRechargePlan;
import com.recharge2mePlay.recharge2me.recharge.models.MobileRechargePlanCategory;
import com.recharge2mePlay.recharge2me.recharge.models.MobileRechargePlansResponse;
import com.recharge2mePlay.recharge2me.recharge.ui.adapters.MobileRechargeCategoryAdapter;
import com.recharge2mePlay.recharge2me.utils.CardCallbacks;
import com.recharge2mePlay.recharge2me.utils.FunctionUtils;
import com.recharge2mePlay.recharge2me.utils.MyAnimation;

import com.recharge2mePlay.recharge2me.webservices.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRechargePlanActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ProgressBar progressBar;
    private TextView tv_planOperator, tvError, tvHeader;
    private ImageView iv_plansBack, ivOperatorIcon;

    // DATA
    private MobileRechargePlansResponse mobileRechargePlansResponse;
    private String operator, circle, number, circleId, opCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recahrge_plan);

        operator = getIntent().getExtras().getString("op");
        circle = getIntent().getExtras().getString("circle");
        number = getIntent().getExtras().getString("number");
        circleId = getIntent().getExtras().getString("circleId");
        opCode = getIntent().getExtras().getString("opCode");

        tabLayout = findViewById(R.id.tabLayout_plans);
        viewPager = findViewById(R.id.viewPager_plans);
        progressBar = findViewById(R.id.pbLoader);
        iv_plansBack = findViewById(R.id.ivBack);
        tv_planOperator = findViewById(R.id.tv_planOperator);
        tvError = findViewById(R.id.tvError);
        tvHeader = findViewById(R.id.tvHeader);
        ivOperatorIcon = findViewById(R.id.ivOperatorIcon);

        setData();
        setEventListeners();
        getRechragePlans();
    }

    private void setData() {
        tvHeader.setText(R.string.browse_plans);

        tv_planOperator.setText(operator + " " + circle);
        switch(operator){
            case "Idea" : ivOperatorIcon.setImageResource(R.drawable.idea); break;
            case "IDEA" : ivOperatorIcon.setImageResource(R.drawable.idea); break;
            case "Vodafone" : ivOperatorIcon.setImageResource(R.drawable.idea); break;
            case "VODAFONE" : ivOperatorIcon.setImageResource(R.drawable.idea); break;
            case "Reliance Jio" : ivOperatorIcon.setImageResource(R.drawable.jio); break;
            case "Jio" : ivOperatorIcon.setImageResource(R.drawable.jio); break;
            case "Airtel" : ivOperatorIcon.setImageResource(R.drawable.airtel); break;
            case "AIRTEL" : ivOperatorIcon.setImageResource(R.drawable.airtel); break;
            case "Bsnl" : ivOperatorIcon.setImageResource(R.drawable.bsnl); break;
            case "BSNL" : ivOperatorIcon.setImageResource(R.drawable.bsnl); break;
        }
    }

    private void setEventListeners() {
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

    private void getRechragePlans() {
        showProgressBar();

        // Init Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Init JsonConverter Interface
        ApiService apiService = retrofit.create(ApiService.class);

        Call<MobileRechargePlansResponse> call = apiService.getMobileRechargePlans(opCode, circleId);

        call.enqueue(new Callback<MobileRechargePlansResponse>() {
            @Override
            public void onResponse(Call<MobileRechargePlansResponse> call, Response<MobileRechargePlansResponse> response) {
                if (!response.isSuccessful()){
                    setError(FunctionUtils.INSTANCE.parseError(response.errorBody()));
                    return;
                }
                mobileRechargePlansResponse = response.body();
                setRechargePlanse();
                hideProgressBar();
            }

            @Override
            public void onFailure(Call<MobileRechargePlansResponse> call, Throwable t) {
                setError(t.toString());
                hideProgressBar();
            }
        });
    }

    private void setRechargePlanse() {
        ArrayList<MobileRechargePlanCategory> rechargeCategories = mobileRechargePlansResponse.getData().getCategories();

        MobileRechargeCategoryAdapter adapter = new MobileRechargeCategoryAdapter(
                GetRechargePlanActivity.this,
                rechargeCategories,
                new CardCallbacks() {
                    @Override
                    public void onCardItemClick(int categoryPosition, int planPosition) {
                        MobileRechargePlanCategory category = rechargeCategories.get(categoryPosition);
                        MobileRechargePlan rechargePlan = category.getItems().get(planPosition);
                        sendPlanData(String.valueOf(rechargePlan.getAmount()), rechargePlan.getValidity(), rechargePlan.getBenefit());
                    }
                }
        );
        viewPager.setAdapter(adapter);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        for(MobileRechargePlanCategory category : rechargeCategories) {
            tabLayout.addTab(tabLayout.newTab().setText(category.getName()));
        }
        tabLayout.setTabTextColors(getResources().getColor(R.color.perpleLight), getResources().getColor(R.color.perple));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void setError(String error) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(error);
    }

    public void sendPlanData(String amount, String validity, String details) {
        Intent intent = new Intent();
        intent.putExtra("Amount", amount);
        intent.putExtra("Validity", validity);
        intent.putExtra("Details", details);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

}