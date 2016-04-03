package com.yupiigames.querymovies.ui.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import com.yupiigames.querymovies.BuildConfig;
import com.yupiigames.querymovies.QueryMoviesApplication;
import com.yupiigames.querymovies.injection.component.ActivityComponent;
import com.yupiigames.querymovies.injection.component.DaggerActivityComponent;
import com.yupiigames.querymovies.injection.module.ActivityModule;

/**
 * Created by yair.carreno on 3/19/2016.
 */
public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule(this))
                    .applicationComponent(QueryMoviesApplication.get(this).getComponent()).build();
        }
        return mActivityComponent;
    }
}
