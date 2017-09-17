package com.github.alexpfx.udacity.nanodegree.android.baking_app.widget.ingredient;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list.RecipeActivity;

/**
 * Created by alexandre on 16/09/17.
 */

public class IngredientListWidgetProvider extends AppWidgetProvider {

    public static void updateRecipeName(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,
                                        String name) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
        remoteViews.setTextViewText(R.id.widget_text_recipe_name, name);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);

        PendingIntent launchAppPendingIntent = createLaunchAppPendingIntent(context);
        remoteViews.setOnClickPendingIntent(R.id.widget_image_widget_icon, launchAppPendingIntent);


        remoteViews.setRemoteAdapter(R.id.widget_list_ingredients, new Intent(context, IngredientsWidgetService.class));

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private PendingIntent createLaunchAppPendingIntent(Context context) {
        Intent intent = new Intent(context, RecipeActivity.class);
        return PendingIntent.getActivity(context, 0, intent, 0);
    }

}
