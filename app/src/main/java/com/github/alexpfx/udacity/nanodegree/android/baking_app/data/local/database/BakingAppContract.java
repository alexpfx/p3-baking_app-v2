package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by alexandre on 22/07/17.
 */

public interface BakingAppContract {

    String AUTHORITY = "com.github.alexpfx.udacity.nanodegree.android.baking_app";

    Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    String DROP_TABLE = " DROP TABLE IF EXISTS ";


    /* SQLITE */
    String CREATE_TABLE = "CREATE TABLE ";
    String TEXT_TYPE = " TEXT ";
    String INTEGER_TYPE = " INTEGER ";
    String COMMA = ",";
    String SEMICOLON = ";";
    String OPEN_BRACKETS = "(";
    String CLOSE_BRACKETS = ")";
    String PRIMARY_KEY = " PRIMARY KEY ";
    String FOREIGN_KEY = " FOREIGN KEY ";
    String REFERENCES = " REFERENCES ";
    String AUTOINCREMENT = " AUTOINCREMENT ";
    String END_SQL = CLOSE_BRACKETS + SEMICOLON;

    interface IngredientsEntry extends BaseColumns {
        Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(Paths.INGREDIENTS).build();

        /* Sqlite */
        String TABLE_NAME = "INGREDIENTS";

        String COLUMN_QUANTITY = "QUANTITY";

        String COLUMN_MEASURE = "MEASURE";

        String COLUMN_INGREDIENT = "INGREDIENT";

        String COLUMN_RECIPE_ID = "RECIPE_ID";

        String[] ALL_COLUMNS = {_ID, COLUMN_INGREDIENT, COLUMN_MEASURE, COLUMN_QUANTITY, COLUMN_RECIPE_ID};

        String SQL_CREATE_TABLE = CREATE_TABLE +
                TABLE_NAME + OPEN_BRACKETS +
                _ID + INTEGER_TYPE + COMMA +
                COLUMN_QUANTITY + INTEGER_TYPE + COMMA +
                COLUMN_MEASURE + TEXT_TYPE + COMMA +
                COLUMN_INGREDIENT + TEXT_TYPE + COMMA +
                COLUMN_RECIPE_ID + INTEGER_TYPE + COMMA +
                FOREIGN_KEY + OPEN_BRACKETS + COLUMN_RECIPE_ID + CLOSE_BRACKETS +
                REFERENCES + RecipeEntry.TABLE_NAME + OPEN_BRACKETS + RecipeEntry._ID + CLOSE_BRACKETS + COMMA +
                SyntaxUtils.compositePrimaryKey(_ID, COLUMN_RECIPE_ID) + END_SQL;


    }

    interface StepEntry extends BaseColumns {

        Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(Paths.STEPS).build();

        String TABLE_NAME = "STEPS";

        String COLUMN_SHORT_DESCRIPTION = "SHORT_DESCRIPTION";

        String COLUMN_DESCRIPTION = "DESCRIPTION";

        String COLUMN_THUMBNAIL_URL = "THUMBNAIL_URL";

        String COLUMN_VIDEO_URL = "VIDEO_URL";

        String COLUMN_RECIPE_ID = "RECIPE_ID";

        String[] ALL_COLUMNS = {_ID, COLUMN_SHORT_DESCRIPTION, COLUMN_DESCRIPTION, COLUMN_VIDEO_URL,
                COLUMN_THUMBNAIL_URL, COLUMN_RECIPE_ID};

        String SQL_CREATE_TABLE = CREATE_TABLE + TABLE_NAME + OPEN_BRACKETS +
                _ID + INTEGER_TYPE + COMMA +
                COLUMN_SHORT_DESCRIPTION + TEXT_TYPE + COMMA +
                COLUMN_DESCRIPTION + TEXT_TYPE + COMMA +
                COLUMN_THUMBNAIL_URL + TEXT_TYPE + COMMA +
                COLUMN_VIDEO_URL + TEXT_TYPE + COMMA +
                COLUMN_RECIPE_ID + INTEGER_TYPE + COMMA +
                FOREIGN_KEY + OPEN_BRACKETS + COLUMN_RECIPE_ID + CLOSE_BRACKETS +
                REFERENCES + RecipeEntry.TABLE_NAME + OPEN_BRACKETS + RecipeEntry._ID + CLOSE_BRACKETS + COMMA +
                SyntaxUtils.compositePrimaryKey(_ID, COLUMN_RECIPE_ID) +
                END_SQL;

    }

    interface RecipeEntry extends BaseColumns {
        Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(Paths.RECIPES).build();

        String TABLE_NAME = "RECIPES";

        String COLUMN_NAME = "NAME";

        String COLUMN_SERVINGS = "SERVINGS";

        String COLUMN_IMAGE = "IMAGE";

        String[] ALL_COLUMNS = {_ID, COLUMN_NAME, COLUMN_SERVINGS, COLUMN_IMAGE};

        String SQL_CREATE_TABLE = CREATE_TABLE + TABLE_NAME + OPEN_BRACKETS +
                _ID + INTEGER_TYPE + PRIMARY_KEY + AUTOINCREMENT + COMMA +
                COLUMN_NAME + TEXT_TYPE + COMMA +
                COLUMN_IMAGE + TEXT_TYPE + COMMA +
                COLUMN_SERVINGS + INTEGER_TYPE +
                END_SQL;


    }

    class Paths {
        public static final String INGREDIENTS = "ingredients";
        public static final String STEPS = "steps";
        public static final String RECIPES = "recipes";
    }

    class SyntaxUtils {
        static String compositePrimaryKey(String... columns) {
            StringBuilder sb = new StringBuilder(PRIMARY_KEY + OPEN_BRACKETS);
            for (int i = 0; i < columns.length; i++) {
                sb.append(columns[i]);
                if (i != columns.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(CLOSE_BRACKETS);
            return sb.toString();
        }
    }

}
