package com.github.alexpfx.udacity.nanodegree.android.baking_app;

import android.app.Application;
import android.preference.PreferenceManager;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationModule;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.DaggerApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;


/**
 * Created by alexandre on 17/07/17.
 */
public class App extends Application implements HasComponent<ApplicationComponent> {

    private final boolean clearPrefs = false;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (clearPrefs) { // used to test widget default when no default recipe was selected in Widget Config.
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().clear().apply();
        }

    }


    @Override
    public void initialize() {
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this))
                .build();
    }

    @Override
    public ApplicationComponent getComponent() {
        if (applicationComponent == null) {
            initialize();
        }
        return applicationComponent;
    }
}


