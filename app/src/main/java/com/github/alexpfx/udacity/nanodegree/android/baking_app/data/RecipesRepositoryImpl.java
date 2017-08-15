package com.github.alexpfx.udacity.nanodegree.android.baking_app.data;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.RecipeDataSource;
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

    public Recipe loadRecipe (){
        refresh();

        return recipeDataSource.getRecipes().get(0);

    }

    private void refresh() {
        if (recipeDataSource.isEmpty()) {
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
        recipeDataSource.store(response.body());
    }


}
