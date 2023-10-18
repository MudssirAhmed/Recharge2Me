package com.recharge2mePlay.recharge2me.utils.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.recharge2mePlay.recharge2me.R;

public class LoadingDialog {

    public Activity activity;
    public AlertDialog dialog;
    public View view;
    public LottieAnimationView lottieAnimationView;

    public LoadingDialog(Activity mActivity){
            activity = mActivity;
    }

    public void startLoading(){

        AlertDialog.Builder builder =  new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.custom_loading_dialog,null);

        builder.setView(view);
        builder.setCancelable(false);

        lottieAnimationView = view.findViewById(R.id.planeAnimation);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.dialog_background));
        dialog.show();
    }

    public void stopLoading(){
        dialog.dismiss();
    }

    public void success(){
        lottieAnimationView.setAnimation("process_success.json");
        lottieAnimationView.playAnimation();
        lottieAnimationView.loop(false);
        dialog.setCancelable(true);
    }

    public void pending(){
        lottieAnimationView.setAnimation("process_pending.json");
        lottieAnimationView.playAnimation();
        lottieAnimationView.loop(false);
        dialog.setCancelable(true);
    }

    public void failed(){
        lottieAnimationView.setAnimation("process_failed.json");
        lottieAnimationView.playAnimation();
        lottieAnimationView.loop(false);
        dialog.setCancelable(true);
    }

}
