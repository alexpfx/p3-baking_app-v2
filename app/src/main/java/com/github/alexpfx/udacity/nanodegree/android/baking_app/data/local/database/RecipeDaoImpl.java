package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;

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
    public Cursor getAll() {
        return dbHelper.getReadableDatabase().query(
                BakingAppContract.RecipeEntry.TABLE_NAME,
                BakingAppContract.RecipeEntry.ALL_COLUMNS,
                null, null, null, null, null);

    }

    @Override
    public Cursor get(int recipeId) {
        return dbHelper.getReadableDatabase().query(BakingAppContract.RecipeEntry.TABLE_NAME,
                BakingAppContract.RecipeEntry.ALL_COLUMNS,
                BakingAppContract.RecipeEntry._ID + "= ?",
                new String[]{String.valueOf(recipeId)}, null, null, null);
    }

    @Override
    public void insert(Recipe recipe) {


    }

    @Override
    public void bulkInset(List<Recipe> recipes) {
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
    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return dbHelper.getReadableDatabase().rawQuery(sql, selectionArgs);
    }

}
