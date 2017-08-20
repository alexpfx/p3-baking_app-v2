package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.MappingUtil.toRecipe;

/**
 * Created by alexandre on 06/08/17.
 */
public class StepDaoImpl implements StepDao {

    private SQLiteOpenHelper sqLiteOpenHelper;

    @Inject
    public StepDaoImpl(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    @Override
    public int insert(Step step) {
        return 0;
    }

    @Override
    public List<Step> getAll(int recipeId) {
        Cursor cursor = sqLiteOpenHelper.getReadableDatabase().query(
                BakingAppContract.StepEntry.TABLE_NAME,
                BakingAppContract.StepEntry.ALL_COLUMNS,
                BakingAppContract.StepEntry.COLUMN_RECIPE_ID + "= ?",
                new String[]{String.valueOf(recipeId)}, null, null, null
        );

        if (!cursor.moveToFirst()) {
            return Collections.EMPTY_LIST;
        }
        List<Step> steps = new ArrayList<>();
        do {
            Step step = MappingUtil.toStep(cursor);
            steps.add(step);
        } while (cursor.moveToNext());
        return steps;
    }

    @Override
    public void bulkInsert(List<Step> steps) {
        SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();
        try {
            database.beginTransaction();
            for (Step step : steps) {
                database.insert(BakingAppContract.StepEntry.TABLE_NAME, null, MappingUtil.toContentValues(step));
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }
}
