package com.github.alexpfx.udacity.nanodegree.android.baking_app.widget.ingredient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.App;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.BakingRepository;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationModule;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.DaggerApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.util.IngredientSpanableListHolder;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    class IngredientListRemoteViewFactory implements RemoteViewsFactory {


        private static final String TAG = "IngredientListRemoteVie";
        private final Context context;
        private final Intent intent;

        BakingRepository repository;


        private IngredientSpanableListHolder items;

        private int recipeId;
        private Recipe recipe;

        public IngredientListRemoteViewFactory(Context context, Intent intent) {
            this.context = context;
            this.intent = intent;
        }


        @Override
        public void onCreate() {
            ApplicationComponent componente = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule
                    ((App) getApplication())).build();
            if (repository == null){
                repository = componente.recipesRepository();
            }
        }

        @Override
        public void onDataSetChanged() {
            final CountDownLatch latch = new CountDownLatch(2);

            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String value = defaultSharedPreferences.getString(context.getString(R.string.pref_recipe_id_key), "-1");

            recipeId = Integer.parseInt(value);
            repository.recipe(recipeId, new BakingRepository.Callback<Recipe>() {
                @Override
                public void onReceive(Recipe data) {
                    recipe = data;
                    latch.countDown();

                }
            });

            repository.ingredients(recipeId, new BakingRepository.Callback<List<Ingredient>>() {
                @Override
                public void onReceive(List<Ingredient> data) {
                    items = new IngredientSpanableListHolder(data, context);
                    latch.countDown();
                }
            });

            waitData(latch);
        }

        private void waitData(CountDownLatch latch) {
            try {
                latch.await(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (position == 0) {
                return updateHeader();
            } else {
                return updateCell(position);
            }
        }

        private RemoteViews updateHeader() {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout
                    .layout_widget_ingredient_list_header);
            remoteViews.setTextViewText(R.id.text_header_title, recipe.getName());
            return remoteViews;
        }

        private RemoteViews updateCell(int position) {
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
            return 2;
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
