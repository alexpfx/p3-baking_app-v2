package com.github.alexpfx.udacity.nanodegree.android.baking_app.di;

import android.content.Context;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.BakingRepository;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list.RecipeAdapter;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.util.GlideWrapper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alexandre on 30/07/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    Context context();

    BakingRepository recipesRepository();

    Boolean isTablet();

    GlideWrapper glideWrapper();

    RecipeAdapter recipeAdapter();

}
