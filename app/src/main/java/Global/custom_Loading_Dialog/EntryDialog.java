package Global.custom_Loading_Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.recharge2mePlay.recharge2me.R;

public class EntryDialog {

    public Activity activity;
    public AlertDialog dialog;
    public View view;

    public EntryDialog(Activity mActivity){
        activity = mActivity;
    }

    public void showDialog(String message){

        AlertDialog.Builder builder =  new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.entry_message,null);

        builder.setView(view);
        builder.setCancelable(false);

        TextView tv_message = view.findViewById(R.id.tv_entryDialog_message);
        tv_message.setText(message);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.dialog_background));
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }

}
