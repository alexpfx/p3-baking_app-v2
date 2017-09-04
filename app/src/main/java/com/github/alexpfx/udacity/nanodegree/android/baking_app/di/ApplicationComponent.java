package com.github.alexpfx.udacity.nanodegree.android.baking_app.di;

import android.content.Context;
import android.os.Handler;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.RecipesRepository;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alexandre on 30/07/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    Context context ();
    Executor executor ();
    RecipesRepository recipesRepository();
    Boolean isTablet();
    Handler handler ();

}
