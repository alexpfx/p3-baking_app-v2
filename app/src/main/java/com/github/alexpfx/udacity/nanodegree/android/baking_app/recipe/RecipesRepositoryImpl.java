package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.data.Recipe;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.data.remote.RecipeService;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alexandre on 18/07/17.
 */
public class RecipesRepositoryImpl implements RecipesRepository {

    private final RecipeService recipeService;
    private final Executor executor;
    private final RecipeDataSource recipeDataSource;

    @Inject
    public RecipesRepositoryImpl(RecipeService recipeService, Executor executor, RecipeDataSource recipeDataSource) {
        this.recipeService = recipeService;
        this.executor = executor;
        this.recipeDataSource = recipeDataSource;
    }

    @Override
    public List<Recipe> recipes() {
        refresh();
        return recipeDataSource.getRecipes();
    }

    private void refresh() {
        if (!recipeDataSource.hasData()) {
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
        recipeDataSource.save(response.body());
    }


}
