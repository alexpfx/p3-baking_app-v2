package com.github.alexpfx.udacity.nanodegree.android.baking_app.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by alexandre on 12/09/17.
 */

public class IngredientsWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientListRemoteViewFactory(getApplicationContext(), intent);
    }


}
