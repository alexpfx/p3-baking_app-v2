package com.github.alexpfx.udacity.nanodegree.android.baking_app.data;

import java.util.List;

/**
 * Created by alexandre on 18/07/17.
 */

/**
 * Responsavel apenas por obter os dados de receita
 */
public interface RecipesRepository {
    void recipes(RecipesRepositoryImpl.Callback callback);

    List<Step> stepsByRecipe(int recipeId);

    List<Ingredient> ingredientsByRecipe(int recipeId);
}
