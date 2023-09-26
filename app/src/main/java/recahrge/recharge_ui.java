package recahrge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.recharge2mePlay.recharge2me.R;

import com.recharge2mePlay.recharge2me.utils.MyAnimation;

public class recharge_ui extends AppCompatActivity {

    MyAnimation animation;
    ImageView iv_rechargeUi_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recahrge_ui);

        iv_rechargeUi_back = findViewById(R.id.iv_rechargeUi_back);

        iv_rechargeUi_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.to_left_pop, R.anim.from_right_pop);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.to_left_pop, R.anim.from_right_pop);
    }
}