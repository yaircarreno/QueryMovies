package com.yupiigames.querymovies.injection.component;

import com.yupiigames.querymovies.injection.module.ActivityModule;
import com.yupiigames.querymovies.injection.scope.PerActivity;
import com.yupiigames.querymovies.ui.activity.MainActivity;
import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 * Created by yair.carreno on 3/19/2016.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
}
