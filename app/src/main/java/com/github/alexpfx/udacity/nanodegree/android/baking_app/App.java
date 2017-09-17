package com.github.alexpfx.udacity.nanodegree.android.baking_app;

import android.app.Application;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationModule;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.DaggerApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;


/**
 * Created by alexandre on 17/07/17.
 */

public class App extends Application implements HasComponent<ApplicationComponent> {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)){
//            return;
//        }
//        LeakCanary.install(this);
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


