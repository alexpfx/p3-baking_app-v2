package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppContract.StepEntry;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppOpenHelper;

import static com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppContract.AUTHORITY;
import static com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppContract.IngredientsEntry;
import static com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppContract.Paths;
import static com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppContract.RecipeEntry;

/**
 * Created by alexandre on 23/07/17.
 */
@Deprecated
public class BakingAppContentProvider extends ContentProvider {

    public static final int RECIPES = 100;
    public static final int RECIPE_BY_ID = 101;

    public static final int INGREDIENTS = 200;
    public static final int INGREDIENT_BY_RECIPE_ID = 201;
    public static final int INGREDIENT_BY_IDS = 202;


    public static final int STEPS = 300;
    public static final int STEP_BY_RECIPE_ID = 301;
    public static final int STEP_BY_IDS = 302;

    private UriMatcher uriMatcher = buildUriMatcher();

    private static final UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, Paths.RECIPES, RECIPES);
        uriMatcher.addURI(AUTHORITY, Paths.RECIPES + "/#", RECIPE_BY_ID);

        uriMatcher.addURI(AUTHORITY, Paths.INGREDIENTS, INGREDIENTS);
        uriMatcher.addURI(AUTHORITY, Paths.INGREDIENTS + "/#", INGREDIENT_BY_RECIPE_ID);
        uriMatcher.addURI(AUTHORITY, Paths.INGREDIENTS + "/#/#", INGREDIENT_BY_IDS);

        uriMatcher.addURI(AUTHORITY, Paths.STEPS, STEPS);
        uriMatcher.addURI(AUTHORITY, Paths.STEPS + "/#", STEP_BY_RECIPE_ID);
        uriMatcher.addURI(AUTHORITY, Paths.STEPS + "/#/#", STEP_BY_IDS);

        return uriMatcher;
    }


    BakingAppOpenHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new BakingAppOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        int match = uriMatcher.match(uri);

        final Cursor cursor;
        switch (match) {
            case RECIPES:
                cursor = db.query(RecipeEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case RECIPE_BY_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id = ?";
                String[] mSelectionArgs = new String[]{id};
                db.query(RecipeEntry.TABLE_NAME, projection, mSelection, mSelectionArgs, null, null, sortOrder);
            default:
                throw new UnsupportedOperationException("Unknow uri: " + uri);

        }


        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        switch (match) {
            case RECIPES:
                return insertRecipe(db, uri, values);
            case INGREDIENTS:
                return insertIngredient(db, uri, values);
            case STEPS:
                return insertStep(db, uri, values);
            default:
                throw new UnsupportedOperationException("Unknow Uri: " + uri);
        }


    }

    private Uri insertStep(SQLiteDatabase db, Uri uri, ContentValues values) {
        long stepId = db.insert(StepEntry.TABLE_NAME, null, values);
        checkIdAndNotifyChange(uri, stepId);
        long recipeId = values.getAsLong(StepEntry.COLUMN_RECIPE_ID);
        Uri.Builder builder = StepEntry.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, stepId);
        ContentUris.appendId(builder, recipeId);
        return builder.build();
    }


    private Uri insertIngredient(SQLiteDatabase db, Uri uri, ContentValues values) {
        long ingredientId = db.insert(IngredientsEntry.TABLE_NAME, null, values);
        checkIdAndNotifyChange(uri, ingredientId);
        long recipeId = values.getAsLong(IngredientsEntry.COLUMN_RECIPE_ID);
        Uri.Builder builder = IngredientsEntry.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, ingredientId);
        ContentUris.appendId(builder, recipeId);
        return builder.build();
    }

    private Uri insertRecipe(SQLiteDatabase db, Uri uri, ContentValues values) {
        long recipeId = db.insert(RecipeEntry.TABLE_NAME, null, values);
        checkIdAndNotifyChange(uri, recipeId);
        return ContentUris.withAppendedId(RecipeEntry.CONTENT_URI, recipeId);
    }

    private void checkIdAndNotifyChange(Uri uri, long id) {
        if (id <= 0) {
            throw new SQLException("Failed to insert row into: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

