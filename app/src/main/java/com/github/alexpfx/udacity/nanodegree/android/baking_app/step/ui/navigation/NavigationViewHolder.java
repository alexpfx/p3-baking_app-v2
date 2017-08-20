package com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui.navigation;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui.BaseViewHolder;

import butterknife.BindView;


/**
 * Created by alexandre on 20/08/17.
 */
public class NavigationViewHolder extends BaseViewHolder implements View.OnClickListener, NavigationViewControl {

    NavigableListener listener;

    @BindView(R.id.btn_previous)
    ImageButton btnPrev;

    @BindView(R.id.btn_next)
    ImageButton btnNext;

    @BindView(R.id.text_short_description)
    TextView txtShortDescripton;

    public NavigationViewHolder(View view, final NavigableListener listener) {
        super(view);
        this.listener = listener;
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
    }


    @Override
    public void bind(Step step) {
        txtShortDescripton.setText(step.getShortDescription());
    }

    @Override
    public void onClick(View view) {
        if (listener == null) {
            return;
        }
        if (view.equals(btnPrev)) {
            listener.onPrevClick();
        } else if (view.equals(btnNext)) {
            listener.onNextClick();
        }

    }

    public void setBtnNextVisibility(int visibility) {
        btnNext.setVisibility(visibility);
    }
    public void setBtnPrevVisibility (int visibility){
        btnPrev.setVisibility(visibility);
    }



    public interface NavigableListener {
        void onNextClick();

        void onPrevClick();
    }
}
