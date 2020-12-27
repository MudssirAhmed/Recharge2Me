package Global.custom_Loading_Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.recharge2mePlay.recharge2me.R;

public class LoadingDialog {

    public Activity activity;
    public AlertDialog dialog;
    public View view;

    public LoadingDialog(Activity mActivity){
            activity = mActivity;
    }

    public void startLoading(){

        AlertDialog.Builder builder =  new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.custom_loading_dialog,null);

        builder.setView(view);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.dialog_background));
        dialog.show();
    }

    public void stopLoading(){
        dialog.dismiss();
    }

}