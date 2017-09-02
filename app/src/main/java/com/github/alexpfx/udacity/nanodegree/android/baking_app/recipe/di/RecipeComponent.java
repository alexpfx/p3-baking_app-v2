package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ActivityModule;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail.RecipeDetailFragment;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail.StepActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list.RecipeListFragment;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui.StepDetailFragment;

import dagger.Component;

/**
 * Created by alexandre on 31/07/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface RecipeComponent {
    void inject (StepDetailFragment stepDetailFragment);
    void inject (RecipeListFragment recipeListFragment);
    void inject (RecipeDetailFragment recipeDetailFragment);
    void inject(StepActivity stepActivity);
}
