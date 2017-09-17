package com.github.alexpfx.udacity.nanodegree.android.baking_app.util;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.HeaderViewHolder;


/**
 * Created by alexandre on 07/09/17.
 */

public abstract class HeadingableRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER_VIEW_TYPE = 0;

    @LayoutRes
    private int headerLayoutResource;
    private String headerTitle;
    private Context context;

    public HeadingableRecycleAdapter(@LayoutRes int headerLayoutResourceId, String headerTitle, Context context) {
        this.headerLayoutResource = headerLayoutResourceId;
        this.headerTitle = headerTitle;
        this.context = context;
    }

    public Context context() {
        return context;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW_TYPE) {
            return new HeaderViewHolder(LayoutInflater.from(context).inflate(headerLayoutResource, parent, false));
        }
        return onCreateViewHolder(parent);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == HEADER_VIEW_TYPE) {
            HeaderViewHolder vh = (HeaderViewHolder) holder;
            vh.bind(headerTitle);
        } else {
            onAddViewHolder(holder, position - 1);
        }
    }

    @Override
    public final int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public final int getItemCount() {
        return itemCount() + 1;
    }

    public abstract int itemCount();

    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    public abstract void onAddViewHolder(RecyclerView.ViewHolder holder, int position);

}
