package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import android.database.Cursor;
import android.util.Log;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alexandre on 06/08/17.
 */
@Singleton
public class RecipeDataSourceImpl implements RecipeDataSource {

    private static final String TAG = "RecipeDataSourceImpl";
    private RecipeDao recipeDao;
    private IngredientDao ingredientDao;
    private StepDao stepDao;

    @Inject
    public RecipeDataSourceImpl(RecipeDao recipeDao, IngredientDao ingredientDao, StepDao stepDao) {
        this.recipeDao = recipeDao;
        this.ingredientDao = ingredientDao;
        this.stepDao = stepDao;
    }


    @Override
    public List<Recipe> getRecipes() {
        Cursor cursor = recipeDao.getAll();
        cursor.moveToFirst();
        List<Recipe> recipes = new ArrayList<>();
        do {
            Recipe recipe = MappingUtil.toRecipe(cursor);
            Log.d(TAG, "getRecipes: " + recipe);
            recipes.add(recipe);
        } while (cursor.moveToNext());
        return recipes;
    }

    @Override
    public void store(List<Recipe> recipes) {
        recipeDao.bulkInset(recipes);

        for (Recipe recipe : recipes) {
            Log.d(TAG, "store: " + recipe);
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

    @Override
    public boolean isEmpty() {
        Cursor cursor = recipeDao.rawQuery("select count (*) as count from " + BakingAppContract.RecipeEntry.TABLE_NAME, null);
        cursor.moveToFirst();
        return cursor.getInt(0) == 0;
    }

    @Override
    public Recipe getIngredients(int recipeId) {

        return null;

    }
}
