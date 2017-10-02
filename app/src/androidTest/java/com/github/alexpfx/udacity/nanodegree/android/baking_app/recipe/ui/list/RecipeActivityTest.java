package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list;


import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityTestRule = new ActivityTestRule<>(RecipeActivity.class);

    @Before
    public void setUp() {
        SystemClock.sleep(1000);
    }

    @Test
    public void clickRecipe_OpenStepDetailActivityFindIngredient() {

        Matcher<View> cheesecake = hasDescendant(withText("Cheesecake"));

        onView(withId(R.id.recycler_recipe_list))
                .perform(scrollTo(cheesecake))
                .perform(actionOnItem(cheesecake, click()));


        onView(withId(R.id.recycler_ingredient_list)).check(matches(hasDescendant(withText(startsWith("Graham")))));

    }


    @Test
    public void clickRecipe_OpenStepDetailActivityClickStep() {
        Matcher<View> cheesecake = hasDescendant(withText("Cheesecake"));


        onView(withId(R.id.recycler_recipe_list))
                .perform(scrollTo(cheesecake))
                .perform(actionOnItem(cheesecake, click()));

        onView(withId(android.R.id.content)).perform(ViewActions.swipeUp());

        Matcher<View> stepListRecyclerView = withId(R.id.recycler_step_list);
        onView(stepListRecyclerView).perform(scrollToPosition(5));
        onView(stepListRecyclerView).perform(actionOnItemAtPosition(5, click()));

    }


    @Test
    public void clickRecipe_OpenStepDetailActivityClickNext_CheckPlayer_AndBackToFirstScreenCheckingRecipeList() {
        Matcher<View> cheesecake = hasDescendant(withText("Cheesecake"));


        onView(withId(R.id.recycler_recipe_list))
                .perform(scrollTo(cheesecake))
                .perform(actionOnItem(cheesecake, click()));

        onView(withId(android.R.id.content)).perform(ViewActions.swipeUp());

        Matcher<View> stepListRecyclerView = withId(R.id.recycler_step_list);
        onView(stepListRecyclerView).perform(scrollToPosition(5));

        SystemClock.sleep(2000);

        onView(stepListRecyclerView).perform(actionOnItemAtPosition(5, click()));


        ViewInteraction buttonNext = onView(
                allOf(withId(R.id.btn_next), withText("Next"), isDisplayed()));
        buttonNext.perform(click());


        SystemClock.sleep(1000);

        onView(allOf(withId(R.id.video_player_view), withTagValue(is((Object) Integer.valueOf(5))))).check(matches
                (isDisplayed()));


        pressBack();
        pressBack();

        onView(withId(R.id.recycler_recipe_list)).check(matches(isDisplayed()));


    }


}

