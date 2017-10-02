package com.github.alexpfx.udacity.nanodegree.android.baking_app.data;

import java.util.List;

/**
 * Created by alexandre on 18/07/17.
 */
public interface BakingRepository {

    void recipes(BakingRepositoryImpl.Callback <List<Recipe>> callback);

    void recipe(int recipeId, Callback<Recipe> callback);

    void ingredients(int recipeId, BakingRepositoryImpl.Callback<List<Ingredient>> callback);

    List<Step> stepsByRecipe(int recipeId);

    List<Ingredient> ingredientsByRecipe(int recipeId);

    boolean hasData();

    interface Callback<T> {
        void onReceive(T data);
    }
}
