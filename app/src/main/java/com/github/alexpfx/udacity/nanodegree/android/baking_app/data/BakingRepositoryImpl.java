package com.github.alexpfx.udacity.nanodegree.android.baking_app.data;

import android.os.Handler;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.IngredientDao;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.RecipeDao;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.StepDao;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.remote.RecipeService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by alexandre on 18/07/17.
 */
@Singleton
public class BakingRepositoryImpl implements BakingRepository {

    private final RecipeService recipeService;
    private final Executor executor;
    private final RecipeDao recipeDao;
    private final IngredientDao ingredientDao;
    private final StepDao stepDao;
    private Handler uiThread;

    @Inject
    public BakingRepositoryImpl(RecipeService recipeService, Executor executor,
                                RecipeDao recipeDao, IngredientDao ingredientDao, StepDao stepDao, Handler uiThread
    ) {
        this.recipeService = recipeService;
        this.executor = executor;
        this.recipeDao = recipeDao;
        this.ingredientDao = ingredientDao;
        this.stepDao = stepDao;
        this.uiThread = uiThread;
    }

    @Override
    public void recipes(final Callback<List<Recipe>> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                refresh();
                uiThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onReceive(recipeDao.getAll());
                    }
                });
            }
        });

    }

    @Override
    public void recipe(final int recipeId, final Callback<Recipe> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                refresh();
                callback.onReceive(recipeDao.get(recipeId));
            }
        });
    }

    @Override
    public void ingredients(final int recipeId, final Callback<List<Ingredient>> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                refresh();
                uiThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onReceive(ingredientDao.getAll(recipeId));

                    }
                });

            }
        });

    }


    @Override
    public List<Step> stepsByRecipe(int recipeId) {
        return stepDao.getAll(recipeId);
    }

    @Override
    public List<Ingredient> ingredientsByRecipe(int recipeId) {
        return ingredientDao.getAll(recipeId);
    }

    @Override
    public boolean hasData (){
        return !recipeDao.isEmpty();
    }

    private void refresh() {
        if (!hasData()) {
            try {
                Call<List<Recipe>> allRecipes = recipeService.getAllRecipes();
                Response<List<Recipe>> response = allRecipes.execute();
                storeResponse(response);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void storeResponse(Response<List<Recipe>> response) {
        List<Recipe> recipes = response.body();
        recipeDao.bulkInsert(recipes);

        if (recipes == null) {
            return;
        }
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
