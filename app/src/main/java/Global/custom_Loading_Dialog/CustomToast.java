package Global.custom_Loading_Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.recharge2mePlay.recharge2me.R;

public class CustomToast {

    Activity activity;
    Toast toast;

    public CustomToast(Activity activity){
        this.activity = activity;
    }

    public void showToast(String text){

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast, null);

        TextView textView = view.findViewById(R.id.tv_customToast_message);
        textView.setText(text);

        toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);

        toast.setView(view);

        toast.show();

    }

}
