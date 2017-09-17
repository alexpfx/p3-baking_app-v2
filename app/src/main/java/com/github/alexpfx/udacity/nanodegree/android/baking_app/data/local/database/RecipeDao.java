package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;

import java.util.List;

/**
 * Created by alexandre on 06/08/17.
 */

public interface RecipeDao {
    List<Recipe> getAll();

    Recipe get(int recipeId);

    void bulkInsert(List<Recipe> recipes);

    boolean isEmpty();

}
