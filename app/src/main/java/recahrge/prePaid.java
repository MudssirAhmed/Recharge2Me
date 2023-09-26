package recahrge;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.recharge2mePlay.recharge2me.R;

import java.util.ArrayList;
import java.util.List;

import Global.customAnimation.MyAnimation;
import Global.custom_Loading_Dialog.CustomToast;
import local_Databasse.entity_numberDetails;
import local_Databasse.numberData.numberViewModel;
import recahrge.myAdapters.dbNumberDetails_adapter;

public class prePaid extends Fragment {

    View view;
    EditText et_EnterMobileNumber;
    Button btn_fetchMobileDetails;
    TextView tv_rechargeWarningText;
    ImageView iv_prePiad_back;

    // Radio button & group
    RadioGroup radioGroup;
    RadioButton radioButton_prePaid, radioButton_postPaid;

    // Integers
    final int GOTO_CONTACT_LIST = 845;

    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;

    // customs
    Animation animation;
    MyAnimation myAnimation;

    CustomToast toast;

    // number Data from Database using entity
    numberViewModel mNumberViewModel;
    RecyclerView rv_dbNumberDetails;
    dbNumberDetails_adapter mAdpter_numberDetails;
    List<entity_numberDetails> list = new ArrayList<>();

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
//        iv_prePiad_back = view.findViewById(R.id.iv_prePaid_back);

        radioGroup = view.findViewById(R.id.rb_preAndPost);
        radioButton_prePaid = view.findViewById(R.id.radioButton_prePaid);
        radioButton_postPaid = view.findViewById(R.id.radioButton_postPaid);

        toast = new CustomToast((recharge_ui) requireActivity());

        // init RecyclerView for db_numberDetails
        mAdpter_numberDetails = new dbNumberDetails_adapter(getActivity());
        rv_dbNumberDetails = view.findViewById(R.id.rv_db_numberDetails);
        rv_dbNumberDetails.setAdapter(mAdpter_numberDetails);
        rv_dbNumberDetails.setLayoutManager(new LinearLayoutManager((recharge_ui)requireActivity()));

        // Init onClick Animation
        animation = AnimationUtils.loadAnimation((recharge_ui) requireActivity(), R.anim.click);
        myAnimation  = new MyAnimation();

        try {
            mNumberViewModel = new numberViewModel(getActivity().getApplication());

            mNumberViewModel.getReadAllData().observe(getViewLifecycleOwner(), entity_numberDetails -> {
                mAdpter_numberDetails.setData(entity_numberDetails);
                list = mNumberViewModel.getReadAllData().getValue();
            });

        }
        catch (Exception e){
            toast.showToast(e.getMessage());
        }


        radioButton_prePaid.setChecked(true);

        btn_fetchMobileDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_fetchMobileDetails.startAnimation(animation);
                if(checkNoisValidateOrNot(et_EnterMobileNumber.getText().toString()))
                    gotoMobileDetailsFinder(et_EnterMobileNumber.getText().toString().trim());
            }
        });

        et_EnterMobileNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_EnterMobileNumber.startAnimation(animation);
            }
        });

        // This is for Filtering the numberList in Database and set on RecyclerView
        et_EnterMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setFilteredList(editable.toString());
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

                        Intent intent = new Intent((recharge_ui) requireActivity(), recahrge.contactList.contactList_activity.class);
                        startActivityForResult(intent, GOTO_CONTACT_LIST);
                        return true;
                    }
                }
                return false;
            }
        });

        return view;
    }

    // It will set the filtered List and pass the list in RecyclerView
    private void setFilteredList(String text){

        List<entity_numberDetails> filteredList = new ArrayList<>();

        if(list != null){
            for(entity_numberDetails child: list){
                String number = child.getNumber();
                if(number.toLowerCase().contains(text.toLowerCase())){
                    filteredList.add(child);
                }
            }
            mAdpter_numberDetails.setData(filteredList);
        }
    }

    // It will responsible for goto MobileFinders UI
    private void gotoMobileDetailsFinder(String num){

        hideBackButton();

        entity_numberDetails entity_numberDetails1 = null;

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
    private void hideBackButton(){
        ImageView iv_rechargeUi_back = getActivity().findViewById(R.id.iv_rechargeUi_back);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_rechargeUi_back.setVisibility(View.GONE);
            }
        }, 100);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOTO_CONTACT_LIST){
            if(resultCode == Activity.RESULT_OK){
                String number = data.getStringExtra("number");
                if(checkNoisValidateOrNot(number));
            }
        }
    }

    // Vaidate Enter number by user
    private boolean checkNoisValidateOrNot(String number){

        CustomToast toast = new CustomToast((recharge_ui) requireActivity());

        if (number.length() < 10 ) {
            if(number.contains("*") || number.contains("#") || number.contains("(")
                    || number.contains(")") || number.contains(".") || number.contains("-") || number.contains("_") || number.contains(",")
                    || number.contains("/")) {
                toast.showToast("Special characters doesn't allowed!");
                return false;
            }
            else {
                toast.showToast("Pleae Enter 10 digit Number!");
                return false;
            }
        }

        else if(number.length() > 10) {

            if(number.contains("*") || number.contains("#") || number.contains("(")
                    || number.contains(")") || number.contains(".") || number.contains("-") || number.contains("_") || number.contains(",")
                    || number.contains("/")) {
                toast.showToast("Special characters doesn't allowed!");
                return false;
            }

            String myNumber = "";
            String firstDigit = String.valueOf(number.charAt(0));
            int flag = 0;

            if(firstDigit.toLowerCase().contains("+"))
                flag = 3;
            if(firstDigit.toLowerCase().contains("0"))
                flag = 1;

            if(number.contains(" ")){
                for (int i = 0; i < number.length(); i++) {
                    if(!(String.valueOf(number.charAt(i)) == " "))
                        myNumber += String.valueOf(number.charAt(i));
                }
            }

            if(flag == 1 || flag == 3) {
                for (int i = flag; i < number.length(); i++) {
                        myNumber += String.valueOf(number.charAt(i));
                }
            }

            et_EnterMobileNumber.setText(myNumber);
            Log.d("number " , myNumber);
            return false;
        }

        else if(number.length() == 10){
            if(number.contains("*") || number.contains("#") || number.contains("(")
                    || number.contains(")") || number.contains(".") || number.contains("-") || number.contains("_") || number.contains(",")
                    || number.contains("/")) {
                toast.showToast("Special characters doesn't allowed!");
                return false;
            }
        }

        else if(number.contains("*") || number.contains("#") || number.contains("(")
                || number.contains(")") || number.contains(".") || number.contains("-") || number.contains("_") || number.contains(",")
                || number.contains("/")) {
            toast.showToast("Special characters doesn't allowed!");
            return false;
        }

        return true;
    }
}