package recahrge;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.recharge2mePlay.recharge2me.R;

public class prePaid extends Fragment {

    View view;
    EditText et_EnterMobileNumber;
    Button btn_fetchMobileDetails;
    TextView tv_rechargeWarningText;

    // Radio button & group
    RadioGroup radioGroup;
    RadioButton radioButton_prePaid, radioButton_postPaid;


    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;

    Animation animation;


    public prePaid() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pre_paid, container, false);

        et_EnterMobileNumber = view.findViewById(R.id.et_EnterMobileNumber);
        btn_fetchMobileDetails = view.findViewById(R.id.btn_fetchMobileDetails);
        tv_rechargeWarningText = view.findViewById(R.id.tv_rechargeWarningText);

        radioGroup = view.findViewById(R.id.rb_preAndPost);
        radioButton_prePaid = view.findViewById(R.id.radioButton_prePaid);
        radioButton_postPaid = view.findViewById(R.id.radioButton_postPaid);


        radioButton_prePaid.setChecked(true);

        // Init onClick Animation
        animation = AnimationUtils.loadAnimation((recharge_ui) requireActivity(), R.anim.click);

        et_EnterMobileNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_EnterMobileNumber.startAnimation(animation);
            }
        });

        btn_fetchMobileDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_fetchMobileDetails.startAnimation(animation);

                if(et_EnterMobileNumber.length() != 10 || et_EnterMobileNumber.length() > 10){
                    Toast.makeText((recharge_ui) requireActivity(), "Please Enter 10 digits Mobile No.", Toast.LENGTH_SHORT).show();
                    tv_rechargeWarningText.setText("Please Enter 10 digits Mobile no.");
                }
                else {
                    gotoMobileDetailsFinder(et_EnterMobileNumber.getText().toString().trim());
                }
            }
        });

        // EditText Right clickListener
        et_EnterMobileNumber.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {

                    if(event.getRawX() >= (et_EnterMobileNumber.getRight() -
                            et_EnterMobileNumber.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here

                        v.startAnimation(animation);
                        Toast.makeText((recharge_ui) requireActivity(), "contactList", Toast.LENGTH_SHORT).show();

                        return true;
                    }
                }
                return false;
            }
        });

        return view;
    }

    private void gotoMobileDetailsFinder(String num){


        prePaidDirections.ActionPrePaid3ToMobileDetailsFinder
                action = prePaidDirections.actionPrePaid3ToMobileDetailsFinder(num, "from_prePaid");

        if(radioButton_prePaid.isChecked()){
            action.setRecahrgeType("Prepaid");
        }
        else if(radioButton_postPaid.isChecked()){
            action.setRecahrgeType("Postpaid");
        }

         Navigation.findNavController(view).navigate(action);

    }// End of Mobile Details Finder;
}