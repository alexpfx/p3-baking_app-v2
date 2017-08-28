package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ActivityModule;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.DaggerRecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail.StepActivity;


public class RecipeActivity extends AppCompatActivity implements HasComponent<RecipeComponent>, OnRecipeSelectListener {

    public static final String KEY_RECIPE_ID = "KEY_RECIPE_ID";
    private static final String TAG = "RecipeActivity";
    private RecipeComponent recipeComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        recipeComponent = DaggerRecipeComponent.builder().applicationComponent(
                ((HasComponent<ApplicationComponent>) getApplication()).getComponent()
        ).activityModule(new ActivityModule(this)).build();

        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public RecipeComponent getComponent() {
        return recipeComponent;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRecipeSelect(Recipe recipe) {
        Intent intent = new Intent(getApplicationContext(), StepActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_RECIPE_ID, recipe.getId());
        intent.putExtras(bundle);


        startActivity(intent);
    }
}
