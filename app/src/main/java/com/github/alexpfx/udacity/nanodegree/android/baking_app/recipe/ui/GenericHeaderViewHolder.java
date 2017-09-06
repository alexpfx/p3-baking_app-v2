package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandre on 05/09/17.
 */

public class GenericHeaderViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.text_header_title)
    TextView txtHeaderTitle;


    public GenericHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind (String title){
        txtHeaderTitle.setText(title);
    }


}

