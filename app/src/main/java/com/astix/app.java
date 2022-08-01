package com.astix;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.allana.truetime.TrueTime;
import com.allana.truetime.TrueTimeRx;
import com.crashlytics.android.Crashlytics;

import java.io.IOException;
import java.util.Date;

import io.fabric.sdk.android.Fabric;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;





public class app extends Application {

    private static final String TAG = app.class.getSimpleName();
    int counter = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
      //  Crashlytics.setString("PDACode", AppUtils.getToken(this));
      /*  FirebaseCrashlytics.getInstance().setCustomKey("PDACode", AppUtils.getToken(this));
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);*/
     /*   FirebaseApp.initializeApp(getApplicationContext());
        FirebaseCrashlytics.getInstance().setCustomKey("PDACode", AppUtils.getToken(this));
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);*/

        Fabric.with(this, new Crashlytics());
        //Fabric.with(this, new CrashlyticsCore(), new Crashlytics());
      //  Fabric.getLogger().d("PDACode", AppUtils.getToken(this));



        initTrueTime();


//               initRxTrueTime();
//
//                    try{
//                        PeriodicWorkRequest saveRequest =new PeriodicWorkRequest.Builder(TimeUpdateWorker.class, 1, TimeUnit.HOURS)
//                                .build();
//                        WorkManager.getInstance()
//                                .enqueue(saveRequest);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }


    }


    /**
     * init the TrueTime using a AsyncTask.
     */
    private void initTrueTime() {
        new InitTrueTimeAsyncTask().execute();
    }

    // a little part of me died, having to use this
    private class InitTrueTimeAsyncTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... params) {
            try {
                TrueTime.build()
                        //.withSharedPreferences(SampleActivity.this)
                        .withNtpHost("time.apple.com")
                        .withLoggingEnabled(false)
                        .withSharedPreferencesCache(app.this)
                        .withConnectionTimeout(31428)
                        .initialize();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "something went wrong when trying to initialize TrueTime", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!TrueTime.isInitialized()) {
                if (counter < 3)
                    initTrueTime();
            }
        }
    }


    /**
     * Initialize the TrueTime using RxJava.
     */
    private void initRxTrueTime() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                DisposableSingleObserver<Date> disposable = TrueTimeRx.build()
                        .withConnectionTimeout(31428)
                        .withRetryCount(1000)
                        .withSharedPreferencesCache(getApplicationContext())
                        .withLoggingEnabled(false)
                        .initializeRx("time.apple.com")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Date>() {
                            @Override
                            public void onSuccess(Date date) {
                                Log.d(TAG, "Success initialized TrueTime :" + date.toString());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "something went wrong when trying to initializeRx TrueTime", e);
                            }
                        });
            }
        });
    }


}
