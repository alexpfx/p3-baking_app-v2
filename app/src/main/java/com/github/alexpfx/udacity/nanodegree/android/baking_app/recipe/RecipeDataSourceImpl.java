package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppContract;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.data.Recipe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alexandre on 18/07/17.
 */

public class RecipeDataSourceImpl implements RecipeDataSource {

    @NonNull
    private Context context;


    @Inject
    public RecipeDataSourceImpl(@NonNull Context context) {
        this.context = context;
    }


    @Override
    public List<Recipe> getRecipes() {
        Cursor c = context.getContentResolver().query(BakingAppContract.RecipeEntry.CONTENT_URI, null, null, null, null);
        List<Recipe> recipeList = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                recipeList.add(toRecipe(c));
            } while (c.moveToNext());
        }
        return recipeList;
    }

    private Recipe toRecipe(Cursor c) {
        int id = c.getInt(c.getColumnIndex(BakingAppContract.RecipeEntry._ID));
        int servings = c.getInt(c.getColumnIndex(BakingAppContract.RecipeEntry.COLUMN_SERVINGS));
        String name = c.getString(c.getColumnIndex(BakingAppContract.RecipeEntry.COLUMN_NAME));
        String image = c.getString(c.getColumnIndex(BakingAppContract.RecipeEntry.COLUMN_IMAGE));
        return new Recipe(id, name, servings, image);
    }

    @Override
    public boolean hasData() {
        Cursor cursor = context
                .getContentResolver()
                .query(BakingAppContract.RecipeEntry.CONTENT_URI, new String[]{"count (*) as count"}, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(0) > 0;
    }

    @Override
    public void save(List<Recipe> recipes) {


    }
}

