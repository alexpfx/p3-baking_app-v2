package com.github.alexpfx.udacity.nanodegree.android.baking_app.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alexandre on 09/09/17.
 */

@Singleton
public class GlideWrapper {
    private Context context;
    private int placeholder;
    private int error;
    private RequestOptions requestOptions;

    @Inject
    public GlideWrapper(Context context, int defaultPlaceHolder, int defaultErrorImage) {
        this.context = context;
        this.placeholder = defaultPlaceHolder;
        this.error = defaultErrorImage;
    }

    public void loadInto(Object model, ImageView imageView) {
        if (requestOptions == null) {
            requestOptions = new RequestOptions();
            requestOptions.placeholder(placeholder);
            requestOptions.error(error);
            requestOptions.centerCrop();
        }
        Glide.with(context).load(model).apply(requestOptions).into(imageView);
    }

}
