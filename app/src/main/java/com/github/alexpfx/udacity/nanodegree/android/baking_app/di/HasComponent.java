package com.github.alexpfx.udacity.nanodegree.android.baking_app.di;

/**
 * Created by alexandre on 31/07/17.
 */

public interface HasComponent<C> {

    void initialize();

    C getComponent();
}
