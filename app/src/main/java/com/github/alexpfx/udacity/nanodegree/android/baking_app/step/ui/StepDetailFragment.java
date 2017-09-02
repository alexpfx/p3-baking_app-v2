package com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.RecipesRepository;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailFragment extends Fragment implements StepDetailAdapter.OnStepLoadListener {

    private static final String TAG = "StepDetailFragment";
    @BindView(R.id.recycler_step_detail)
    RecyclerView recyclerView;
    @PerActivity
    @Inject
    SimpleExoPlayer simpleExoPlayer;
    @PerActivity
    @Inject
    StepDetailAdapter stepDetailAdapter;
    @PerActivity
    @Inject
    RecipesRepository recipesRepository;

    @PerActivity
    @Inject
    SimpleExoPlayer player;


    private Step step;
    private List<Step> steps;
    private OnStepRequest onStepRequest;


    public StepDetailFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepRequest) {
            onStepRequest = (OnStepRequest) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, view);

        RecipeComponent component = ((HasComponent<RecipeComponent>) getActivity()).getComponent();
        component.inject(this);

        step = onStepRequest.onStepRequest();

        if (step != null) {
            steps = recipesRepository.stepsByRecipe(step.getRecipeId());
            onStepLoad(step);
            setupRecyclerView();
        }


        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(stepDetailAdapter);
        stepDetailAdapter.setStepList(steps, steps.indexOf(step));
        stepDetailAdapter.setOnStepLoadListener(this);
    }

    @Override
    public void onStepLoad(Step step) {
        if (step.getVideoURL() == null || step.getVideoURL().isEmpty()) {
            return;
        }
        String videoURL = step.getVideoURL();
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoURL), new DefaultDataSourceFactory
                (getContext(), Util.getUserAgent(getContext(), "recipePlayer")), new DefaultExtractorsFactory(),
                null, null);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    public void releasePlayer() {
        if (player == null) {
            return;
        }
        player.release();
    }

    public interface OnStepRequest {
        Step onStepRequest();
    }

}
