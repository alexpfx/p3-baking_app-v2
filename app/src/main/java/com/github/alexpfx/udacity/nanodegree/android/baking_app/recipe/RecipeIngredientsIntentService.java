package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.BakingAppWidgetProvider;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppOpenHelper;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.IngredientDao;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.IngredientDaoImpl;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RecipeIngredientsIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_POPULATE_INGREDIENT_LIST = "com.github.alexpfx.udacity.nanodegree.android" +
            ".baking_app.recipe.action.POPULATE_INGREDIENT_LIST";

    // TODO: Rename parameters
    public static final String KEY_RECIPE_ID = "com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe" +
            ".extra" +
            ".RECIPE_ID";
    private static final String TAG = "RecipeIngredientsIntent";




    public RecipeIngredientsIntentService() {
        super("RecipeIngredientsIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionPopulateIngredientList(Context context, int param1) {
        Intent intent = new Intent(context, RecipeIngredientsIntentService.class);
        intent.setAction(ACTION_POPULATE_INGREDIENT_LIST);
        intent.putExtra(KEY_RECIPE_ID, param1);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_POPULATE_INGREDIENT_LIST.equals(action)) {
                int recipeId = intent.getIntExtra(KEY_RECIPE_ID, -1);
                handleActionPopulateIngredientList(recipeId);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionPopulateIngredientList(int recipeId) {
        IngredientDao ingredientDao = new IngredientDaoImpl(new BakingAppOpenHelper(getBaseContext()));
        List<Ingredient> ingredients = ingredientDao.getAll(recipeId);
        Log.d(TAG, "handleActionPopulateIngredientList: "+ingredients);


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_text_ingredient_list);

    }

}
