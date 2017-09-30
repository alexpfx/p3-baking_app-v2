package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail.step_detail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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
    private static final String TAG = "StepDetailActivity";
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
            getSupportFragmentManager().beginTransaction().add(R.id.container_step, fragment).addToBackStack
                    (null).commit();
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setSystemVisibilityByOrientation(newConfig);


    }

    private void setSystemVisibilityByOrientation(Configuration newConfig) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) {
            return;
        }
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            hideSystemUI();
//            supportActionBar.hide();
//        } else {
//            recreate();
//            supportActionBar.show();
//            showSystemUI();
//        }
    }

    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(0);
    }


    @Override
    public void setTitle(CharSequence title) {
        TextView txtToolbarTitle = toolbar.findViewById(R.id.text_toolbar_title);
        txtToolbarTitle.setText(title);
    }


}
