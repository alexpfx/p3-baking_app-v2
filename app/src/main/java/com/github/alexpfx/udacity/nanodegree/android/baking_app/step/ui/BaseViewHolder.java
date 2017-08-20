package com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;

/**
 * Created by alexandre on 20/08/17.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(Step step);
}
