package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ActivityModule;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.DaggerRecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list.RecipeActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui.StepDetailActivity;

public class StepActivity extends AppCompatActivity implements HasComponent<RecipeComponent>, OnStepSelectListener {

    private static final String TAG = "StepActivity";
    private RecipeComponent recipeComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        Log.d(TAG, "onCreate: " + extras.getInt(RecipeActivity.KEY_RECIPE_ID));

        recipeComponent = DaggerRecipeComponent.builder().activityModule(new ActivityModule(this))
                .applicationComponent(((HasComponent<ApplicationComponent>) getApplication()).getComponent()).build();


        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(extras);

        getSupportFragmentManager().beginTransaction().add(R.id.container, recipeDetailFragment).commit();

    }

    @Override
    public RecipeComponent getComponent() {
        return recipeComponent;
    }

    @Override
    public void onStepSelect(Step step) {
        Intent intent = new Intent(this, StepDetailActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable("step", step);
        intent.putExtras(extras);
        startActivity(intent);


    }
}
