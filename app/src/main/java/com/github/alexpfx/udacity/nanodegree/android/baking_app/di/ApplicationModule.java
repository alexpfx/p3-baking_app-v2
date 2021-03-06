package com.github.alexpfx.udacity.nanodegree.android.baking_app.di;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Looper;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.App;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.BakingRepository;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.BakingRepositoryImpl;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppOpenHelper;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.IngredientDao;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.IngredientDaoImpl;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.RecipeDao;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.RecipeDaoImpl;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.StepDao;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.StepDaoImpl;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.executor.JobExecutor;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.util.GlideWrapper;

import java.util.concurrent.Executor;

import javax.inject.Named;
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
    BakingRepository repository(BakingRepositoryImpl recipesRepository) {
        return recipesRepository;
    }

    @Singleton
    @Provides
    RecipeDao recipeDao(RecipeDaoImpl recipeDao) {
        return recipeDao;
    }

    @Singleton
    @Provides
    IngredientDao ingredientDao(IngredientDaoImpl ingredientDao) {
        return ingredientDao;
    }

    @Singleton
    @Provides
    StepDao stepDao(StepDaoImpl stepDao) {
        return stepDao;
    }

    @Singleton
    @Provides
    SQLiteOpenHelper sqLiteOpenHelper(BakingAppOpenHelper bakingAppOpenHelper) {
        return bakingAppOpenHelper;
    }

    @Singleton
    @Provides
    Boolean isMultiPane(Context context) {
        return context.getResources().getBoolean(R.bool.isMultiPane);
    }

    @Singleton
    @Provides
    Handler handler() {
        return new Handler(Looper.getMainLooper());
    }


    @Named("error")
    @Singleton
    @Provides
    int errorImage() {
        return R.drawable.error_image;
    }

    @Named("placeholder")
    @Singleton
    @Provides
    int imagePlaceHolder() {
        return R.drawable.placeholder_image;
    }

    @Singleton
    @Provides
    GlideWrapper glideWrapper(Context context, @Named("placeholder") int defaultImagePlaceHolder, @Named("error") int
            defaultImageError) {
        return new GlideWrapper(context, defaultImagePlaceHolder, defaultImageError);
    }

}
