package com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui.navigation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by alexandre on 20/08/17.
 */
public class NavigationViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.btn_previous)
    ImageButton btnPrev;

    @BindView(R.id.btn_next)
    ImageButton btnNext;

    @BindView(R.id.step_text)
    TextView txtShortDescripton;


    public NavigationViewHolder(View itemView, View.OnClickListener onNavigationButtonClick) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        btnNext.setTag(1);
        btnPrev.setTag(-1);
        btnPrev.setOnClickListener(onNavigationButtonClick);
        btnNext.setOnClickListener(onNavigationButtonClick);
    }



    public void bind(Step step, boolean hasNext, boolean hasPrev) {
        txtShortDescripton.setText(step.getShortDescription());
        changeVisibility(btnNext, hasNext);
        changeVisibility(btnPrev, hasPrev);
    }


    public void changeVisibility(ImageButton button, boolean visible) {
        button.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

}
