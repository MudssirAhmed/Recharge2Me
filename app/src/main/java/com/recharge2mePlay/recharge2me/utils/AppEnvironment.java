package com.recharge2mePlay.recharge2me.utils;

import com.recharge2mePlay.recharge2me.BuildConfig;

public class AppEnvironment {

    public static AppEnvironment instance;
    public static void initInstance() {
        if (instance == null) {
            instance = new AppEnvironment();
        }
    }

    public Boolean showOptions(int status) {
        if(isDevelopmentEnv()) {
            return status == 2 || status == 1;
        } else {
            return status == 1;
        }
    }

    public Boolean isDevelopmentEnv() {
        return BuildConfig.VERSION_CODE == 1;
    }
}