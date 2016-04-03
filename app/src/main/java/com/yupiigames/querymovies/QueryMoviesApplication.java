package com.yupiigames.querymovies;

import android.app.Application;
import android.content.Context;
import com.crashlytics.android.Crashlytics;
import com.yupiigames.querymovies.injection.component.ApplicationComponent;
import com.yupiigames.querymovies.injection.component.DaggerApplicationComponent;
import com.yupiigames.querymovies.injection.module.ApplicationModule;
import com.yupiigames.querymovies.injection.module.NetworkModule;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by yair.carreno on 3/18/2016.
 */
public class QueryMoviesApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Fabric.with(this, new Crashlytics());
        }
    }
    /**
     * To get Context
     * @param context
     * @return QueryMoviesApplication
     * */
    public static QueryMoviesApplication get(Context context) {
        return (QueryMoviesApplication) context.getApplicationContext();
    }

    /**
     * To get ApplicationComponent
     * @return ApplicationComponent
     * */
    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .networkModule(new NetworkModule())
                    .build();
        }
        return mApplicationComponent;
    }

    /**
     * Need to replace the component with a test specific one
     * @param applicationComponent
     * */
    public void setComponent(ApplicationComponent applicationComponent) {
        this.mApplicationComponent = applicationComponent;
    }
}
