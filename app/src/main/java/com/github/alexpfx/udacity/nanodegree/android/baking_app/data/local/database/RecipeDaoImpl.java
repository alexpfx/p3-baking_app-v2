package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alexandre on 06/08/17.
 */
@Singleton
public class RecipeDaoImpl implements RecipeDao {

    private SQLiteOpenHelper dbHelper;

    @Inject
    public RecipeDaoImpl(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public List<Recipe> getAll() {
        Cursor cursor = dbHelper.getReadableDatabase().query(
                BakingAppContract.RecipeEntry.TABLE_NAME,
                BakingAppContract.RecipeEntry.ALL_COLUMNS,
                null, null, null, null, null);
        if (!cursor.moveToFirst()) {
            return Collections.EMPTY_LIST;
        }
        List<Recipe> list = new ArrayList<>();
        do {
            list.add(MappingUtil.toRecipe(cursor));
        } while (cursor.moveToNext());
        return list;
    }

    @Override
    public Recipe get(int recipeId) {
        Cursor cursor = dbHelper.getReadableDatabase().query(BakingAppContract.RecipeEntry.TABLE_NAME,
                BakingAppContract.RecipeEntry.ALL_COLUMNS,
                BakingAppContract.RecipeEntry._ID + "= ?",
                new String[]{String.valueOf(recipeId)}, null, null, null);

        if (!cursor.moveToFirst()) {
            return null;
        }
        return MappingUtil.toRecipe(cursor);
    }


    @Override
    public void bulkInsert(List<Recipe> recipes) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        try {
            database.beginTransaction();
            for (Recipe recipe : recipes) {
                ContentValues cv = MappingUtil.toContentValues(recipe);
                database.insert(BakingAppContract.RecipeEntry.TABLE_NAME, null, cv);

            }
            database.setTransactionSuccessful();


        } finally {
            database.endTransaction();
        }

    }

    @Override
    public boolean isEmpty() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try (Cursor cursor = database.rawQuery("select count (*) as count from " + BakingAppContract.RecipeEntry
                .TABLE_NAME, null)) {
            cursor.moveToFirst();
            return cursor.getInt(0) == 0;
        }
    }


}
