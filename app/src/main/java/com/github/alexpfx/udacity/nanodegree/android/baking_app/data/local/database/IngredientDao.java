package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;

import java.util.List;

/**
 * Created by alexandre on 06/08/17.
 */

public interface IngredientDao {
    void bulkInsert(List<Ingredient> ingredients);


    List<Ingredient> getAll (int recipeId);



}
