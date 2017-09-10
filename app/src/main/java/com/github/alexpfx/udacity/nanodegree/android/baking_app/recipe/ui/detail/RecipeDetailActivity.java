package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity implements HasComponent<RecipeComponent>, OnStepSelectListener,
        RecipeDetailFragment.OnRecipeIdRequested{


    private static final String TAG = "RecipeDetailActivity";

    @Inject
    @PerActivity
    Boolean isMultiPane;
    private RecipeComponent recipeComponent;
    private Step step;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        initialize();

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState == null){
            if (!isMultiPane) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container_step_detail, new RecipeDetailFragment(), "recipe_detail")
                        .commit();
            }
        }


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
        recipeComponent = DaggerRecipeComponent.builder().activityModule(new ActivityModule(this, RecipeDetailActivity.class
                .getName()))
                .applicationComponent(((HasComponent<ApplicationComponent>) getApplication()).getComponent()).build();
        recipeComponent.inject(this);
    }

    @Override
    public void onStepSelect(Step step) {
        this.step = step;
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_step_detail, fragment, "step_detail").addToBackStack("recipe_detail")
                .commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.layout_step_container, fragment)
//                .commit();

    }

    @Override
    public Integer requestRecipeId() {
        Bundle extras = getIntent().getExtras();
        return extras.getInt(RecipeActivity.KEY_RECIPE_ID);
    }

    @Override
    public String requestRecipeName() {
        Bundle extras = getIntent().getExtras();
        return extras.getString(RecipeActivity.KEY_RECIPE_NAME);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
