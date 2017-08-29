package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ActivityModule;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.DaggerRecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list.RecipeActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui.StepDetailActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui.StepDetailFragment;

import javax.inject.Inject;

public class StepActivity extends AppCompatActivity implements HasComponent<RecipeComponent>, OnStepSelectListener,
        RecipeDetailFragment.OnRecipeIdRequested, StepDetailFragment.OnStepRequest {

    private static final String TAG = "StepActivity";
    @Inject
    @PerActivity
    boolean isTablet;
    private RecipeComponent recipeComponent;
    private Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
//        recipeDetailFragment.setArguments(extras);
//
//        getSupportFragmentManager().beginTransaction().add(R.id.container, recipeDetailFragment).commit();

    }

    @Override
    public void initialize() {
        recipeComponent = DaggerRecipeComponent.builder().activityModule(new ActivityModule(this))
                .applicationComponent(((HasComponent<ApplicationComponent>) getApplication()).getComponent()).build();
    }

    @Override
    public RecipeComponent getComponent() {
        if (recipeComponent == null) {
            initialize();
        }
        return recipeComponent;
    }

    @Override
    public void onStepSelect(Step step) {
        if (isTablet){
            this.step = step;
        }else{
            Intent intent = new Intent(this, StepDetailActivity.class);
            Bundle extras = new Bundle();
            extras.putParcelable("step", step);
            intent.putExtras(extras);
            startActivity(intent);
        }

    }

    @Override
    public Step onStepRequest() {
        return step;
    }

    @Override
    public Integer requestRecipeId() {
        Bundle extras = getIntent().getExtras();
        return extras.getInt(RecipeActivity.KEY_RECIPE_ID);
    }
}
