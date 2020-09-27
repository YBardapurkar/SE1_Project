package com.se1.team3.campuscarrental;

import android.app.Application;

import com.amitshekhar.DebugDB;

import com.facebook.stetho.Stetho;

public class CampusCarRentalApplication extends Application {
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
            DebugDB.getAddressLog();
        }
    }
}
