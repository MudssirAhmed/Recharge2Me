package com.recharge2mePlay.recharge2me.utils;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.recharge2mePlay.recharge2me.BuildConfig;
import com.recharge2mePlay.recharge2me.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppBaseActivity extends AppCompatActivity {

    private Gson gson = new Gson();

    public void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }

    public void showErrorSnackBar(String error, View view) {
        Snackbar snackbar = Snackbar.make(view, error, Snackbar.LENGTH_SHORT);
        snackbar.setTextColor(Color.WHITE);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.Warning_text));
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setAction("Ok", view1 -> {
            snackbar.dismiss();
        });
        snackbar.show();
    }

    public void somethingWentWrongErrorSnackBar(View view, String error, Exception exception) {
        String somethingWentWrong = getString(R.string.something_went_wrong);
        if(AppEnvironment.instance.isDevelopmentEnv()) {
            somethingWentWrong = error;
        }

        Snackbar snackbar = Snackbar.make(view, somethingWentWrong, Snackbar.LENGTH_SHORT);
        snackbar.setTextColor(Color.WHITE);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.Warning_text));
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setAction("Ok", view1 -> {
            snackbar.dismiss();
        });
        snackbar.show();
    }

    public void somethingWentWrongErrorSnackBar(View view, String TAG, String error, Exception exception) {
        String somethingWentWrong = getString(R.string.something_went_wrong);
        if(AppEnvironment.instance.isDevelopmentEnv()) {
            somethingWentWrong = error;
        }
        showErrorLog(TAG, error, exception);

        Snackbar snackbar = Snackbar.make(view, somethingWentWrong, Snackbar.LENGTH_SHORT);
        snackbar.setTextColor(Color.WHITE);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.Warning_text));
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setAction("Ok", view1 -> {
            snackbar.dismiss();
        });
        snackbar.show();
    }

    public void somethingWentWrongToast(String TAG, String error) {
        runOnUiThread(() -> {
            if(BuildConfig.VERSION_CODE == 1) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
        Log.e(TAG, error);
    }

    public void somethingWentWrongToast(String TAG, String error, Exception exception) {
        runOnUiThread(() -> {
            if(BuildConfig.VERSION_CODE == 1) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });

        Log.e(TAG, error, exception);
    }

    public void showSimpleLog(String TAG, String message) {
        Log.d(TAG, message);
    }

    public void showSimpleLog(String TAG, String message, Object object) {
        String json = gson.toJson(object);
        Log.d(TAG, message + ", Object: " + json);
    }

    public void showErrorLog(String TAG, String error) {
        Log.e(TAG, error);
    }

    public void showErrorLog(String TAG, Exception e) {
        Log.e(TAG, e.getMessage(), e);
    }

    public void showErrorLog(String TAG, String error, Exception e) {
        Log.e(TAG, error, e);
    }

    public boolean isStringValid(String str) {
        return str != null && !str.equals("") && !str.equals("null");
    }

    public boolean isStringInValid(String str) {
        return str == null || str.isEmpty() || str.equals("null");
    }

    public <T> boolean isListValid(ArrayList<T> list) {
        return list != null && list.size() > 0;
    }

    public <T> boolean isListInValid(ArrayList<T> list) {
        return list == null && list.size() == 0;
    }

    public <T> boolean isListValid(List<T> list) {
        return list != null && list.size() > 0;
    }

    public <S, T> boolean isMapValid(HashMap<S, T> map) {
        return map != null && map.size() > 0;
    }


}
