package Global.Validation;

import android.app.Activity;
import android.util.Log;

import java.util.regex.Pattern;

import javax.xml.validation.Validator;

import Global.custom_Loading_Dialog.CustomToast;
import recahrge.recharge_ui;

public class Validate {

    Activity activity;
    CustomToast toast;

    public Validate(Activity activity){
        this.activity = activity;
        this.toast = new CustomToast(activity);
    }

    public boolean checkName(String Name){
        if(Name.isEmpty()){
            toast.showToast("Pleae Enter Name!...");
            return false;
        }
        else if(Name.length() < 6 ){
            toast.showToast("Name lenth should be 6 or greater");
            return false;
        }
        else if(Name.contains("*") || Name.contains("#") || Name.contains("(")
                || Name.contains(")") || Name.contains(".") || Name.contains("-") || Name.contains("_") || Name.contains(",")
                || Name.contains("/") || Name.contains("+")){
            toast.showToast("Special characters doesn't allowed! in Name");
            return false;
        }
        else{
            return true;
        }
    }
    public boolean checkNumber(String number){
        if(number.isEmpty()){
            toast.showToast("Please Enter Number!...");
            return false;
        }
        else if (number.length() < 10 ) {
            if(number.contains("*") || number.contains("#") || number.contains("(")
                    || number.contains(")") || number.contains(".") || number.contains("-") || number.contains("_") || number.contains(",")
                    || number.contains("/") || number.contains(" ") || number.contains("+")) {
                toast.showToast("Special characters doesn't allowed!");
                return false;
            }
            else {
                toast.showToast("Pleae Enter 10 digit Number!");
                return false;

            }
        }
        else if(number.length() == 10){
            if(number.contains("*") || number.contains("#") || number.contains("(")
                    || number.contains(")") || number.contains(".") || number.contains("-") || number.contains("_") || number.contains(",")
                    || number.contains("/") || number.contains(" ") || number.contains("+")) {
                toast.showToast("Special characters doesn't allowed! in Number");
                return false;
            }
            else {
                return true;
            }
        }
        return false;
    }
    public boolean checkEmail(String Email){

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        boolean email = pat.matcher(Email).matches();

        if(Email.isEmpty()){
            toast.showToast("Please Enter Email!...");
            return false;
        }
        else if(email == true){
            return true;
        }
        else{
            toast.showToast("Please Enter Valid Email!...");
            return false;
        }
    }
    public boolean checkPassword(String Password){

        if(Password.isEmpty()){
            toast.showToast("Please Enter Password!...");
            return false;
        }
        else if(Password.length() < 6){
            toast.showToast("Password length should be 6 or greater!");
            return false;
        }

        return true;
    }

}
