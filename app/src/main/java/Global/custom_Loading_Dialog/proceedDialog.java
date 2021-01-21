package Global.custom_Loading_Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recharge2mePlay.recharge2me.R;

public class proceedDialog {

    Dialog dialog;
    Activity activity;

    public proceedDialog(Activity activity){
        this.activity = activity;
    }

    public Dialog showProceedDialog(){

        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.recharge_custom_dialog);
        dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.dialog_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;

        dialog.show();


        return dialog;
    }


}
