package com.astix.rajtraderssfasales.FirebaseNotifications;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.astix.rajtraderssfasales.utils.IntentConstants;
import com.astix.rajtraderssfasales.utils.PreferenceManager;


public class NewTokenService extends IntentService {


    public NewTokenService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        PreferenceManager  mPreferencesManager = PreferenceManager.getInstance(getApplicationContext());//mPreferencesManager(CommonInfo.FCM_PREFS_NAME, Context.MODE_PRIVATE);
        mPreferencesManager.setValue(IntentConstants.FCM_TOKEN, Context.MODE_PRIVATE);
    }
}
