package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;

/**
 * Created by alexandre on 05/09/17.
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder {


    private TextView txtHeader;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        txtHeader = itemView.findViewById(R.id.text_header_title);
        if (txtHeader == null) {
            throw new IllegalStateException("Header resource file must have a TextView with id text_header_title");
        }

    }

    public void bind(String title) {
        txtHeader.setText(title);
    }


}

