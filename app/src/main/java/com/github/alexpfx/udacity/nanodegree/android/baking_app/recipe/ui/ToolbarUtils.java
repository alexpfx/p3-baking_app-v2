package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui;

import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by alexandre on 10/09/17.
 */

public class ToolbarUtils {

    public static void setupToolbarWithLogo (AppCompatActivity activity, Toolbar toolbar, @DrawableRes int resLogo){
        setupToolbar(activity, toolbar, false, false);
        activity.getSupportActionBar().setDisplayUseLogoEnabled(true);
        activity.getSupportActionBar().setLogo(resLogo);
    }

    public static void setupToolbar(AppCompatActivity activity, Toolbar toolbar) {
        setupToolbar(activity, toolbar, true, true);
    }

    public static void setupToolbar(AppCompatActivity activity, Toolbar toolbar, boolean homeButtonEnabled, boolean
            displayHomeAsUp) {
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setHomeButtonEnabled(homeButtonEnabled);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUp);
    }

}
