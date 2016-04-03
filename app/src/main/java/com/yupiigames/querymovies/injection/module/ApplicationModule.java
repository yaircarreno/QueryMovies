package com.yupiigames.querymovies.injection.module;

import android.app.Application;
import android.content.Context;

import com.yupiigames.querymovies.injection.scope.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies
 * Created by yair.carreno on 3/19/2016.
 */
@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }
}
