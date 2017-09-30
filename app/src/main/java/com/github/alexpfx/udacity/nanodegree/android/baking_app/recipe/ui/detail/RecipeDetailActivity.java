package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ActivityModule;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.DaggerRecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.ToolbarUtils;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail.step_detail.StepDetailActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail.step_detail.StepDetailFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list.RecipeActivity.KEY_RECIPE_ID;

public class RecipeDetailActivity extends AppCompatActivity implements HasComponent<RecipeComponent>,
        OnStepSelectListener {


    @Inject
    @PerActivity
    Boolean isMultiPane;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private RecipeComponent recipeComponent;
    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        initialize();

        ToolbarUtils.setupToolbar(this, toolbar);

        setTitle(getString(R.string.app_name));

        if (savedInstanceState == null) {
            if (!isMultiPane) {
                int recipeId = getRecipeId();
                Bundle bundle = new Bundle();
                bundle.putInt(KEY_RECIPE_ID, recipeId);
                RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(bundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container_step, recipeDetailFragment, "recipe_detail")
                        .commit();
            }
        }


    }

    @Override
    public void setTitle(CharSequence title) {
        TextView txtToolbarTitle = toolbar.findViewById(R.id.text_toolbar_title);
        txtToolbarTitle.setText(title);
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
        StepDetailFragment fragment = StepDetailFragment.newInstance(step);

        if (isMultiPane) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_step, fragment, "step_detail")
                    .addToBackStack("recipe_detail")
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(StepDetailActivity.KEY_STEP, step);
            startActivity(intent);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getRecipeId() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        return pref.getInt(KEY_RECIPE_ID, -1);
    }
}
