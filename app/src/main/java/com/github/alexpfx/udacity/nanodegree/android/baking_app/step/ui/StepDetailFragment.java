package com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui.navigation.NavigationViewHolder;
import com.google.android.exoplayer2.SimpleExoPlayer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailFragment extends Fragment implements NavigationViewHolder.NavigableListener {

    @BindView(R.id.recycler_step_detail)
    RecyclerView recyclerView;


    @PerActivity
    @Inject
    SimpleExoPlayer simpleExoPlayer;

    @PerActivity
    @Inject
    StepDetailAdapter stepDetailAdapter;

    public StepDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, view);
        RecipeComponent component = ((HasComponent<RecipeComponent>) getActivity()).getComponent();
        component.inject(this);

        setupRecyclerView ();
        return view;
    }

    private void setupRecyclerView() {
        stepDetailAdapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onNextClick() {

    }

    @Override
    public void onPrevClick() {

    }
}
