package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ActivityModule;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.DaggerRecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list.RecipeActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui.StepDetailFragment;

import javax.inject.Inject;

public class StepActivity extends AppCompatActivity implements HasComponent<RecipeComponent>, OnStepSelectListener,
        RecipeDetailFragment.OnRecipeIdRequested, StepDetailFragment.OnStepRequest {

    private static final String TAG = "StepActivity";

    @Inject
    @PerActivity
    Boolean isTablet;

    private RecipeComponent recipeComponent;
    private Step step;

    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();
        TransitionManager.beginDelayedTransition(new FrameLayout(getApplicationContext()));

        if(!isTablet){
            getSupportFragmentManager().beginTransaction().replace(R.id.step_container, new RecipeDetailFragment())
                    .commit();
        }


    }

    private int getContainerViewId() {
        return !isTablet ? R.id.step_container : R.id.step_detail_container;
    }

    @Override
    public RecipeComponent getComponent() {
        if (recipeComponent == null) {
            initialize();
        }
        return recipeComponent;
    }

    @Override
    public void initialize() {
        recipeComponent = DaggerRecipeComponent.builder().activityModule(new ActivityModule(this))
                .applicationComponent(((HasComponent<ApplicationComponent>) getApplication()).getComponent()).build();
        recipeComponent.inject(this);
    }

    @Override
    public void onStepSelect(Step step) {
        this.step = step;
        StepDetailFragment fragment = new StepDetailFragment();
        int containerViewId = getContainerViewId();

        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment)
                .commit();

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
