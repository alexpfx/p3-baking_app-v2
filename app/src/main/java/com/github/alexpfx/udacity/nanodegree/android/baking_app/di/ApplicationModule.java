package com.github.alexpfx.udacity.nanodegree.android.baking_app.di;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.App;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppOpenHelper;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.IngredientDao;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.RecipeDao;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.RecipeDaoImpl;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.RecipeDataSource;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.RecipeDataSourceImpl;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.StepDao;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.executor.JobExecutor;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.IngredientDaoImpl;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.RecipesRepository;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.RecipesRepositoryImpl;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.StepDaoImpl;

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
    Executor provideExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    RecipeDataSource dataSource(RecipeDataSourceImpl recipeDataSource) {
        return recipeDataSource;
    }


    @Provides
    @Singleton
    RecipesRepository repository(RecipesRepositoryImpl recipesRepository) {
        return recipesRepository;
    }

    @Singleton
    @Provides
    RecipeDao recipeDao (RecipeDaoImpl recipeDao){
        return recipeDao;
    }

    @Singleton
    @Provides
    IngredientDao ingredientDao (IngredientDaoImpl ingredientDao){
        return ingredientDao;
    }

    @Singleton
    @Provides
    StepDao stepDao (StepDaoImpl stepDao){
        return stepDao;
    }
    @Singleton
    @Provides
    SQLiteOpenHelper sqLiteOpenHelper(BakingAppOpenHelper bakingAppOpenHelper){
        return bakingAppOpenHelper;
    }

}
