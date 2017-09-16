package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ActivityModule;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.DaggerRecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WidgetSettingsActivity extends AppCompatActivity implements HasComponent<RecipeComponent> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RecipeComponent recipeComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_settings);
        ButterKnife.bind(this);
        ToolbarUtils.setupToolbar(this, toolbar, true, true);
        toolbar.setTitle(getString(R.string.pref_screen_title_widget_setup));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
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
            return recipeComponent;
        }
        return recipeComponent;
    }

}
