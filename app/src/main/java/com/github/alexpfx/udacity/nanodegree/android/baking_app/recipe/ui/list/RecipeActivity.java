package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ActivityModule;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.DaggerRecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.ToolbarUtils;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail.RecipeDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeActivity extends AppCompatActivity implements HasComponent<RecipeComponent>, OnRecipeSelectListener {

    public static final String KEY_RECIPE_ID = "KEY_RECIPE_ID";
    public static final String KEY_RECIPE_NAME = "KEY_RECIPE_NAME";
    private static final String TAG = "RecipeActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private RecipeComponent recipeComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        ToolbarUtils.setupToolbarWithLogo(this, toolbar, R.drawable.ic_action_name);
    }

    @Override
    public void initialize() {
        recipeComponent = DaggerRecipeComponent.builder().applicationComponent(
                ((HasComponent<ApplicationComponent>) getApplication()).getComponent()
        ).activityModule(new ActivityModule(this)).build();
    }

    @Override
    public RecipeComponent getComponent() {
        if (recipeComponent == null) {
            initialize();
        }
        return recipeComponent;
    }

    @Override
    public void onRecipeSelect(Recipe recipe) {
        Log.d(TAG, "onRecipeSelect: " + recipe);
        Intent intent = new Intent(getBaseContext(), RecipeDetailActivity.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d(TAG, "onRecipeSelect: " + recipe.getId());
        preferences.edit()
                .putInt(KEY_RECIPE_ID, recipe.getId())
                .putString(KEY_RECIPE_NAME, recipe.getName())
                .apply();

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_widget_settings:
                openWidgetSettings();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openWidgetSettings() {


    }
}

