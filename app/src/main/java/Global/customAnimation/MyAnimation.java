package Global.customAnimation;

import android.os.Handler;
import android.view.View;

public class MyAnimation {

    public void onClickAnimation(View view){
        view.animate()
                .alpha(0f)
                .setDuration(100);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.animate()
                        .alpha(1f)
                        .setDuration(100);
            }
        }, 100);
    }

}
