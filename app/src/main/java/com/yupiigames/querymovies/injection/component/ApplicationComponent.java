package com.yupiigames.querymovies.injection.component;

import android.app.Application;
import android.content.Context;
import com.yupiigames.querymovies.data.DataManager;
import com.yupiigames.querymovies.data.SyncService;
import com.yupiigames.querymovies.data.local.DatabaseHelper;
import com.yupiigames.querymovies.data.remote.MovieApiInterface;
import com.yupiigames.querymovies.injection.module.ApplicationModule;
import com.yupiigames.querymovies.injection.module.NetworkModule;
import com.yupiigames.querymovies.injection.module.StorageModule;
import com.yupiigames.querymovies.injection.scope.ApplicationContext;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by yair.carreno on 3/19/2016.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, StorageModule.class})
public interface ApplicationComponent {

    void inject(SyncService syncService);

    Application application();
    @ApplicationContext Context context();
    DataManager dataManager();
}
