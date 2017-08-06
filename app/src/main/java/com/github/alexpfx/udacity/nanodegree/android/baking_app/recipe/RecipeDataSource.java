package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.data.Recipe;

import java.util.List;

/**
 * Created by alexandre on 18/07/17.
 */

public interface RecipeDataSource {

    List<Recipe> getRecipes();

    boolean hasData();

    void save(List<Recipe> recipes);

}
