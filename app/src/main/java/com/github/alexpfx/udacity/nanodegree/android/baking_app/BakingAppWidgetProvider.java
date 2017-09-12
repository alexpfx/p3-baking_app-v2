package com.github.alexpfx.udacity.nanodegree.android.baking_app;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.RecipeIngredientsIntentService;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list.RecipeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {


    private static final String TAG = "BakingAppWidgetProvider";


    private int getRecipeId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(RecipeActivity.KEY_RECIPE_ID, 1);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        int recipeId = getRecipeId(context);
        RecipeIngredientsIntentService.startActionPopulateIngredientList(context, recipeId);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    class ListProvider implements RemoteViewsService.RemoteViewsFactory {
        List<String> ingredients = new ArrayList<>();
        Context context;
        Intent intent;

        public ListProvider(Context context, Intent intent) {
            this.context = context;
            this.intent = intent;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_ingredient);
            String ingredient = ingredients.get(position);
            remoteViews.setTextViewText(R.id.text_ingredient, ingredient);
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return ingredients.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

}

