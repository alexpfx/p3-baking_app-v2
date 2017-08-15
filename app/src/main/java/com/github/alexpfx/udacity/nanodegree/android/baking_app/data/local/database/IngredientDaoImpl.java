package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;

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

    @Override
    public Cursor getAll(int recipeId) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        return database.query(IngredientsEntry.TABLE_NAME, IngredientsEntry.ALL_COLUMNS, IngredientsEntry.COLUMN_RECIPE_ID + "= ?", new String[]{String.valueOf(recipeId)}, null, null, null);
    }
}
