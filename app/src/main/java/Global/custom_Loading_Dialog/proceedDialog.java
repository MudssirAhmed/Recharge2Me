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

    public Dialog showProceedDialog(String number, String circle, String operator, String amount, String details, String validity){

        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.recharge_custom_dialog);
        dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.dialog_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;


        TextView tv_number = dialog.findViewById(R.id.tv_dialogProced_number);
        TextView tv_Circle = dialog.findViewById(R.id.tv_dialogProceed_circle);
        TextView tv_service = dialog.findViewById(R.id.tv_proceedDialog_Service);
        TextView tv_Amount = dialog.findViewById(R.id.tv_dialogProceed_Amount);
        TextView tv_Details = dialog.findViewById(R.id.tv_dialogProceed_details);
        TextView tv_Validity = dialog.findViewById(R.id.tv_dialogProceed_validity);

        tv_number.setText(number);
        tv_Circle.setText(circle);
        tv_service.setText(operator + " prepaid");
        tv_Amount.setText(amount);
        tv_Details.setText(details);
        tv_Validity.setText(validity);

        dialog.show();

        return dialog;
    }

}
