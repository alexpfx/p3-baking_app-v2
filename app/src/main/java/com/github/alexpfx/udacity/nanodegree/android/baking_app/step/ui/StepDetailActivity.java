package com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.RecipesRepository;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ActivityModule;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.DaggerRecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;

import javax.inject.Inject;

public class StepDetailActivity extends AppCompatActivity implements HasComponent<RecipeComponent> {

    private RecipeComponent recipeComponent;

    @PerActivity
    @Inject
    RecipesRepository recipesRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StepDetailFragment fragment = new StepDetailFragment();

        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();

        recipeComponent = DaggerRecipeComponent.builder().applicationComponent(
                ((HasComponent<ApplicationComponent>) getApplication()).getComponent()
        ).activityModule(new ActivityModule(this)).build();


    }

    @Override
    public RecipeComponent getComponent() {
        return recipeComponent;
    }
}
