package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;

import static com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppContract
        .IngredientsEntry;
import static com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppContract
        .RecipeEntry;
import static com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppContract.StepEntry;

/**
 * Created by alexandre on 06/08/17.
 */
public final class MappingUtil {

    public static Recipe toRecipe(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(RecipeEntry._ID));
        String name = cursor.getString(cursor.getColumnIndex(RecipeEntry.COLUMN_NAME));
        String image = cursor.getString(cursor.getColumnIndex(RecipeEntry.COLUMN_IMAGE));
        int servings = cursor.getInt(cursor.getColumnIndex(RecipeEntry.COLUMN_SERVINGS));
        return new Recipe(id, name, servings, image);
    }

    public static Step toStep(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(StepEntry._ID));
        String shortDescription = cursor.getString(cursor.getColumnIndex(StepEntry.COLUMN_SHORT_DESCRIPTION));
        String description = cursor.getString(cursor.getColumnIndex(StepEntry.COLUMN_DESCRIPTION));
        String thumbUrl = cursor.getString(cursor.getColumnIndex(StepEntry.COLUMN_THUMBNAIL_URL));
        String videoUrl = cursor.getString(cursor.getColumnIndex(StepEntry.COLUMN_VIDEO_URL));
        int recipeId = cursor.getInt(cursor.getColumnIndex(StepEntry.COLUMN_RECIPE_ID));
        return new Step(id, shortDescription, description, videoUrl, thumbUrl, recipeId);
    }

    public static Ingredient toIngredient(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(IngredientsEntry._ID));
        String name = cursor.getString(cursor.getColumnIndex(IngredientsEntry.COLUMN_INGREDIENT));
        String measure = cursor.getString(cursor.getColumnIndex(IngredientsEntry.COLUMN_MEASURE));
        double quantity = cursor.getDouble(cursor.getColumnIndex(IngredientsEntry.COLUMN_QUANTITY));
        int recipeId = cursor.getInt(cursor.getColumnIndex(IngredientsEntry.COLUMN_RECIPE_ID));

        Ingredient ingredient = new Ingredient(quantity, measure, name);
        ingredient.setId(id);
        ingredient.setRecipeId(recipeId);
        return ingredient;
    }

    public static ContentValues toContentValues(Recipe recipe) {
        ContentValues vc = new ContentValues();
        vc.put(RecipeEntry._ID, recipe.getId());
        vc.put(RecipeEntry.COLUMN_NAME, recipe.getName());
        vc.put(RecipeEntry.COLUMN_IMAGE, recipe.getImage());
        vc.put(RecipeEntry.COLUMN_SERVINGS, recipe.getServings());
        return vc;
    }

    public static ContentValues toContentValues(Ingredient ingredient) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IngredientsEntry._ID, ingredient.getId());
        contentValues.put(IngredientsEntry.COLUMN_INGREDIENT, ingredient.getIngredient());
        contentValues.put(IngredientsEntry.COLUMN_QUANTITY, ingredient.getQuantity());
        contentValues.put(IngredientsEntry.COLUMN_RECIPE_ID, ingredient.getRecipeId());
        contentValues.put(IngredientsEntry.COLUMN_MEASURE, ingredient.getMeasure());
        return contentValues;
    }

    public static ContentValues toContentValues(Step step) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StepEntry._ID, step.getId());
        contentValues.put(StepEntry.COLUMN_DESCRIPTION, step.getDescription());
        contentValues.put(StepEntry.COLUMN_SHORT_DESCRIPTION, step.getShortDescription());
        contentValues.put(StepEntry.COLUMN_THUMBNAIL_URL, step.getThumbnailURL());
        contentValues.put(StepEntry.COLUMN_RECIPE_ID, step.getRecipeId());
        contentValues.put(StepEntry.COLUMN_VIDEO_URL, step.getVideoURL());

        return contentValues;
    }

}
