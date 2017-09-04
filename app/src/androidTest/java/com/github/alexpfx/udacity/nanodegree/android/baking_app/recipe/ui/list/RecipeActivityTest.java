package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityTestRule = new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void clickRecipe_OpenStepDetailActivity() {
        onView(withId(R.id.recycler_recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

}

