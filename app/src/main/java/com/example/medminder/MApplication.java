package com.example.medminder;


import android.app.Application;

public class MApplication extends Application {
    private static MApplication mApp;
    public static MApplication getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize the text
        if (mApp == null) {
            mApp = this;
        }
    }

}
