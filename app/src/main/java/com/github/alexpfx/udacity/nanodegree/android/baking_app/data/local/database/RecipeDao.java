package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import android.database.Cursor;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;

import java.util.List;

/**
 * Created by alexandre on 06/08/17.
 */

public interface RecipeDao {
    Cursor getAll();

    void insert(Recipe recipe);

    Cursor get(int recipeId);

    void bulkInset(List<Recipe> recipes);

    Cursor rawQuery(String sql, String[] selectionArgs);
}
