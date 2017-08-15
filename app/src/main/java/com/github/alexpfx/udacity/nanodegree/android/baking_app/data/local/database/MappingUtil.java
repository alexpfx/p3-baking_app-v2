package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;

/**
 * Created by alexandre on 06/08/17.
 */

public final class MappingUtil {

    public static Recipe toRecipe(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(BakingAppContract.RecipeEntry._ID));
        String name = cursor.getString(cursor.getColumnIndex(BakingAppContract.RecipeEntry.COLUMN_NAME));
        String image = cursor.getString(cursor.getColumnIndex(BakingAppContract.RecipeEntry.COLUMN_IMAGE));
        int servings = cursor.getInt(cursor.getColumnIndex(BakingAppContract.RecipeEntry.COLUMN_SERVINGS));
        return new Recipe(id, name, servings, image);
    }

    public static ContentValues toContentValues(Recipe recipe) {
        ContentValues vc = new ContentValues();
        vc.put(BakingAppContract.RecipeEntry._ID, recipe.getId());
        vc.put(BakingAppContract.RecipeEntry.COLUMN_IMAGE, recipe.getName());
        vc.put(BakingAppContract.RecipeEntry.COLUMN_SERVINGS, recipe.getServings());
        vc.put(BakingAppContract.RecipeEntry.COLUMN_NAME, recipe.getName());
        return vc;
    }

    public static ContentValues toContentValues(Ingredient ingredient){
        ContentValues contentValues = new ContentValues();
        contentValues.put(BakingAppContract.IngredientsEntry._ID, ingredient.getId());
        contentValues.put(BakingAppContract.IngredientsEntry.COLUMN_INGREDIENT, ingredient.getIngredient());
        contentValues.put(BakingAppContract.IngredientsEntry.COLUMN_QUANTITY, ingredient.getQuantity());
        contentValues.put(BakingAppContract.IngredientsEntry.COLUMN_RECIPE_ID, ingredient.getRecipeId());
        return contentValues;
    }

    public static ContentValues toContentValues (Step step){
        ContentValues contentValues = new ContentValues();
        contentValues.put(BakingAppContract.StepEntry._ID, step.getId());
        contentValues.put(BakingAppContract.StepEntry.COLUMN_DESCRIPTION, step.getDescription());
        contentValues.put(BakingAppContract.StepEntry.COLUMN_SHORT_DESCRIPTION, step.getShortDescription());
        contentValues.put(BakingAppContract.StepEntry.COLUMN_THUMBNAIL_URL, step.getThumbnailURL());
        contentValues.put(BakingAppContract.StepEntry.COLUMN_RECIPE_ID, step.getRecipeId());
        contentValues.put(BakingAppContract.StepEntry.COLUMN_VIDEO_URL, step.getVideoURL());
        return contentValues;
    }

}
