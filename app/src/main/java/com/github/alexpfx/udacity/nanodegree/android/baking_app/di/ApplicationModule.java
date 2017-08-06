package com.github.alexpfx.udacity.nanodegree.android.baking_app.di;

import android.content.Context;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.App;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.executor.JobExecutor;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.RecipeDataSource;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.RecipeDataSourceImpl;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.RecipesRepository;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.RecipesRepositoryImpl;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alexandre on 30/07/17.
 */
@Module
public class ApplicationModule {

    private App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return app;
    }

    @Provides
    @Singleton
    Executor provideExecutor (JobExecutor jobExecutor){
        return jobExecutor;
    }

    @Provides
    @Singleton
    RecipeDataSource dataSource (RecipeDataSourceImpl recipeDataSource){
        return recipeDataSource;
    }


    @Provides
    @Singleton
    RecipesRepository repository (RecipesRepositoryImpl recipesRepository){
        return recipesRepository;
    }



}
