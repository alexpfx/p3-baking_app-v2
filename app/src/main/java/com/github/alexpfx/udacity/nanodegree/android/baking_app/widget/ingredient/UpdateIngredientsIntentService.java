package com.github.alexpfx.udacity.nanodegree.android.baking_app.widget.ingredient;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;

/**
 * Created by alexandre on 15/09/17.
 */

public class UpdateIngredientsIntentService extends IntentService {


    private static final String ACTION_UPDATE_RECIPE_LIST = "ACTION_UPDATE_RECIPE_LIST";

    public UpdateIngredientsIntentService() {
        super("UpdateIngredientsIntentService");
    }

    public static void startUpdateIngredientsIntentService(Context context) {
        Intent intent = new Intent(context, UpdateIngredientsIntentService.class);

        intent.setAction(ACTION_UPDATE_RECIPE_LIST);

        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }

        String action = intent.getAction();
        if (ACTION_UPDATE_RECIPE_LIST.equals(action)) {
            handleActionUpdateIngredientList();
        }


    }

    private void handleActionUpdateIngredientList() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                IngredientListWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_ingredients);
    }
}
