package com.jack.cpt.rollnumberview;

import android.app.Application;

public class CptApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DrawUtils.init(this);
    }
}
