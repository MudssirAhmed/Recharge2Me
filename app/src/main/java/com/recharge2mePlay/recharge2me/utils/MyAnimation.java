package com.recharge2mePlay.recharge2me.utils;

import android.os.Handler;
import android.view.View;

public class MyAnimation {

    public void onClickAnimation(View view){
        view.animate()
                .alpha(0f)
                .setDuration(80);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.animate()
                        .alpha(1f)
                        .setDuration(80);
            }
        }, 80);
    }

    public void navDrawerAnimation(final View view){

        view.animate()
                .alpha(0f)
                .setDuration(200L)
                .translationXBy(-100f)
                .setListener(null);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.INVISIBLE);
            }
        }, 200);
    }

}
