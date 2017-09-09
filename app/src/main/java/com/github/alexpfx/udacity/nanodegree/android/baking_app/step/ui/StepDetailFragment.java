package com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
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
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailFragment extends Fragment {


    @PerActivity
    @Inject
    RecipesRepository recipesRepository;

    @PerActivity
    @Inject
    SimpleExoPlayer player;

    @PerActivity
    @Inject
    MediaSessionCompat mediaSessionCompat;

    @PerActivity
    @Inject
    PlayerEventListener playerEventListener;

    @BindView(R.id.video_player_view)
    SimpleExoPlayerView simpleExoPlayerView;


    private List<Step> stepList;


    public StepDetailFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, view);

        simpleExoPlayerView.setPlayer(player);
        RecipeComponent component = ((HasComponent<RecipeComponent>) getActivity()).getComponent();
        component.inject(this);

        Bundle arguments = getArguments();
        Step step = arguments.getParcelable("step");

        if (step != null) {
            stepList = recipesRepository.stepsByRecipe(step.getRecipeId());
            loadStep(step);
        }


        return view;
    }


    public void loadStep(Step step) {
        if (step.getVideoURL() == null || step.getVideoURL().isEmpty()) {
            return;
        }
        playVideoUrl(step.getVideoURL());

    }

    private void playVideoUrl(String videoURL) {
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoURL), new DefaultDataSourceFactory
                (getActivity(), Util.getUserAgent(getContext(), "recipePlayer")), new DefaultExtractorsFactory(),
                null, null);

        player.prepare(mediaSource, true, false);
        player.setPlayWhenReady(true);
        mediaSessionCompat.setActive(true);


        player.addListener(playerEventListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mediaSessionCompat.setActive(false);

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
        player.stop();
        player.release();
        player = null;
    }

}
