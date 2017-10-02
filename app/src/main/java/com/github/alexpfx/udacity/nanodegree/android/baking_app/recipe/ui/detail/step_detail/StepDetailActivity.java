package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail.step_detail;

import android.os.Bundle;
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
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.DaggerRecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.ToolbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailActivity extends AppCompatActivity implements HasComponent<RecipeComponent> {
    public static final String KEY_STEP = "step";
    public static final String TAG_STEP_DETAIL = "step_detail";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private RecipeComponent recipeComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);

        ToolbarUtils.setupToolbar(this, toolbar);

        if (savedInstanceState == null) {
            Step step = getIntent().getExtras().getParcelable(KEY_STEP);
            StepDetailFragment fragment = StepDetailFragment.newInstance(step);
            getSupportFragmentManager().beginTransaction().add(R.id.container_step, fragment, TAG_STEP_DETAIL)
                    .commit();
        }


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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setTitle(CharSequence title) {
        TextView txtToolbarTitle = toolbar.findViewById(R.id.text_toolbar_title);
        txtToolbarTitle.setText(title);
    }


}
