package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppContract.DROP_TABLE;
import static com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppContract.IngredientsEntry;
import static com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppContract.RecipeEntry;
import static com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppContract.StepEntry;

/**
 * Created by alexandre on 22/07/17.
 */
@Singleton
public class BakingAppOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "BakingAppOpenHelper";

    public static final int DATABASE_VERSION = 24;
    public static final String DATABASE_NAME = "baking.db";


    private static String[] TABLES = new String[]{IngredientsEntry.TABLE_NAME, StepEntry.TABLE_NAME, RecipeEntry.TABLE_NAME};
    private static String[] CREATE_TABLES = new String[]{RecipeEntry.SQL_CREATE_TABLE, IngredientsEntry.SQL_CREATE_TABLE, StepEntry.SQL_CREATE_TABLE};

    @Inject
    public BakingAppOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String table : CREATE_TABLES) {
            db.execSQL(table);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);
    }


    private static void dropTables(SQLiteDatabase db) {
        for (String table : TABLES) {
            db.execSQL(DROP_TABLE + table);
        }
    }

}
