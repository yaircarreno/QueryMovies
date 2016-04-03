package com.yupiigames.querymovies.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import com.yupiigames.querymovies.injection.scope.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by yair.carreno on 3/19/2016.
 */
@Singleton
public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "query_movies_pref_file";
    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }
}
