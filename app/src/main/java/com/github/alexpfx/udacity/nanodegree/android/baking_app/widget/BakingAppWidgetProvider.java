package com.github.alexpfx.udacity.nanodegree.android.baking_app.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.RecipeIngredientsIntentService;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "BakingAppWidgetProvider";

    private int getRecipeId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(RecipeActivity.KEY_RECIPE_ID, -1);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);

        int recipeId = getRecipeId(context);
        Log.d(TAG, "onUpdate: "+recipeId);

        RecipeIngredientsIntentService.startActionPopulateIngredientList(context, recipeId);

        Intent intent = new Intent(context, RecipeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_image_widget_icon, pendingIntent);

        for (int appWidgetId : appWidgetIds) {
            Intent listIntent = new Intent(context, IngredientsWidgetService.class);
            listIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            remoteViews.setRemoteAdapter(R.id.widget_list_ingredients, listIntent);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        }
        super.onUpdate(context,appWidgetManager, appWidgetIds);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}

