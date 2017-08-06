package com.github.alexpfx.udacity.nanodegree.android.baking_app.di;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alexandre on 31/07/17.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides @PerActivity
    Activity provideActivity (){
        return activity;
    }
}
