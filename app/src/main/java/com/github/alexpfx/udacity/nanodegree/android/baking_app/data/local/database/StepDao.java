package com.github.alexpfx.udacity.nanodegree.android.baking_app.data.local.database;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;

import java.util.List;

/**
 * Created by alexandre on 06/08/17.
 */

public interface StepDao {

    List<Step> getAll(int recipeId);

    void bulkInsert(List<Step> steps);
}
