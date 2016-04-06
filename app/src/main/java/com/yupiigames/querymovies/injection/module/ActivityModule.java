package com.yupiigames.querymovies.injection.module;

import android.app.Activity;
import android.content.Context;
import com.yupiigames.querymovies.injection.scope.ActivityContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yair.carreno on 3/19/2016.
 */
@Module
public class ActivityModule {

    private Activity mActivity;
    private CompositeSubscription mCompositeSubscription;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }
}
