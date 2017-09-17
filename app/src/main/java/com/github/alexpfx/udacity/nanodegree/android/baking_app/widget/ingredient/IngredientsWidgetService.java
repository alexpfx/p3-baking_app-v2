package com.github.alexpfx.udacity.nanodegree.android.baking_app.widget.ingredient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.IngredientSpanableListHolder;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.BakingAppOpenHelper;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.IngredientDao;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database.IngredientDaoImpl;

import java.util.List;

/**
 * Created by alexandre on 12/09/17.
 */

public class IngredientsWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientListRemoteViewFactory(getApplicationContext(), intent);
    }


    /**
     * Created by alexandre on 12/09/17.
     */

    private static class IngredientListRemoteViewFactory implements RemoteViewsFactory {


        private static final String TAG = "IngredientListRemoteVie";
        private final Context context;
        private final Intent intent;
        private IngredientSpanableListHolder items;
        private IngredientDao ingredientDao;
        private int recipeId;

        public IngredientListRemoteViewFactory(Context context, Intent intent) {
            this.context = context;
            this.intent = intent;
        }

        @Override
        public void onCreate() {
            Log.d(TAG, "onCreate: ingredientDao" + ingredientDao);
            if (ingredientDao == null) {
                ingredientDao = new IngredientDaoImpl(new BakingAppOpenHelper(context));
            }


        }

        @Override
        public void onDataSetChanged() {

            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String value = defaultSharedPreferences.getString(context.getString(R.string.pref_recipe_id_key), "");

            recipeId = Integer.parseInt(value);
            Log.d(TAG, "onDataSetChanged: " + recipeId);
            List<Ingredient> ingredients = ingredientDao.getAll(recipeId);
            if (ingredients == null || ingredients.isEmpty()) {
                return;
            }
            items = new IngredientSpanableListHolder(ingredients, context);
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {


            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item_ingredient);

            remoteViews.setTextViewText(R.id.text_ingredient, items.getIngredient(position));
            remoteViews.setTextViewText(R.id.text_ingredient_measure, items.getMeasure(position));
            remoteViews.setTextViewText(R.id.text_ingredient_quantity, items.getQuantity(position));

            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
