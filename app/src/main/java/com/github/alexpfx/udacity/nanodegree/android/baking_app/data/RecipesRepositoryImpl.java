package com.github.alexpfx.udacity.nanodegree.android.baking_app.data;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.IngredientDao;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.RecipeDao;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.StepDao;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.remote.RecipeService;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alexandre on 18/07/17.
 */
@Singleton
public class RecipesRepositoryImpl implements RecipesRepository {

    private final RecipeService recipeService;
    private final Executor executor;
    private final RecipeDao recipeDao;
    private final IngredientDao ingredientDao;
    private final StepDao stepDao;

    @Inject
    public RecipesRepositoryImpl(RecipeService recipeService, Executor executor,
                                 RecipeDao recipeDao, IngredientDao ingredientDao, StepDao stepDao
    ) {
        this.recipeService = recipeService;
        this.executor = executor;
        this.recipeDao = recipeDao;
        this.ingredientDao = ingredientDao;
        this.stepDao = stepDao;
    }

    @Override
    public List<Recipe> recipes() {
        refresh();
        return recipeDao.getAll();
    }

    @Override
    public List<Step> stepsByRecipe(int recipeId) {
        return stepDao.getAll(recipeId);
    }

    @Override
    public List<Ingredient> ingredientsByRecipe(int recipeId) {
        return ingredientDao.getAll(recipeId);
    }

    private void refresh() {
        if (recipeDao.isEmpty()) {
            recipeService.getAllRecipes().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    storeResponse(response);
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    throw new RuntimeException(t);
                }
            });
        }
    }

    private void storeResponse(Response<List<Recipe>> response) {
        List<Recipe> recipes = response.body();
        recipeDao.bulkInsert(recipes);

        for (Recipe recipe : recipes) {
            insertSteps(recipe, recipe.getSteps());
            insertIngredients(recipe, recipe.getIngredients());
        }

    }

    private void insertIngredients(Recipe recipe, List<Ingredient> ingredients) {
        int count = 1;
        for (Ingredient ingredient : ingredients) {
            ingredient.setId(count++);
            ingredient.setRecipeId(recipe.getId());
        }
        ingredientDao.bulkInsert(ingredients);
    }

    private void insertSteps(Recipe recipe, List<Step> steps) {
        for (Step step : steps) {
            step.setRecipeId(recipe.getId());
        }
        stepDao.bulkInsert(steps);

    }


}
