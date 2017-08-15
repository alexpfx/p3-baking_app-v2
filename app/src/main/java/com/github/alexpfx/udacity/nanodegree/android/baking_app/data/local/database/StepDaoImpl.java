package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;

import java.util.List;

import javax.inject.Inject;

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
