package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppContract.*;

/**
 * Created by alexandre on 06/08/17.
 */
@Singleton
public class IngredientDaoImpl implements IngredientDao {
    private SQLiteOpenHelper openHelper;

    @Inject
    public IngredientDaoImpl(SQLiteOpenHelper openHelper) {
        this.openHelper = openHelper;
    }


    @Override
    public void bulkInsert(List<Ingredient> ingredients) {
        SQLiteDatabase database = openHelper.getWritableDatabase();
        try {
            database.beginTransaction();
            for (Ingredient ingredient : ingredients) {
                database.insert(IngredientsEntry.TABLE_NAME, null, MappingUtil.toContentValues(ingredient));
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    private static final String TAG = "IngredientDaoImpl";
    @Override
    public List<Ingredient> getAll(int recipeId) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query(IngredientsEntry.TABLE_NAME,
                IngredientsEntry.ALL_COLUMNS,
                IngredientsEntry.COLUMN_RECIPE_ID + " = ?", new String[]{String.valueOf(recipeId)}, null, null, null);
        if (!cursor.moveToFirst()) {
            return Collections.EMPTY_LIST;
        }
        Log.d(TAG, "getAll: "+cursor.getCount());
        List<Ingredient> ingredients = new ArrayList<>();
        do {
            Ingredient ingredient = MappingUtil.toIngredient(cursor);
            ingredients.add(ingredient);
        }while (cursor.moveToNext());
        return ingredients;
    }

}
