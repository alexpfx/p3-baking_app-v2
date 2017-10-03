package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail.step_detail;


import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.BakingRepository;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.util.GlideWrapper;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.util.NetworkUtils;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepDetailFragment extends Fragment implements ExtractorMediaSource.EventListener {

    public static final String KEY_STEP_INDEX = "KEY_STEP_INDEX";
    private static final String TAG = "StepDetailFragment";
    @PerActivity
    @Inject
    BakingRepository bakingRepository;

    @PerActivity
    @Inject
    SimpleExoPlayer player;

    @Singleton
    @Inject
    GlideWrapper glideWrapper;

    @BindView(R.id.video_player_view)
    SimpleExoPlayerView simpleExoPlayerView;

    @BindView(R.id.text_step_description)
    TextView txtDescription;

    @BindView(R.id.text_step_number)
    TextView txtStepNumber;

    @BindView(R.id.image_thumbnail)
    ImageView imgThumbnail;

    @BindView(R.id.image_video_placeholder)
    ImageView imgVideoPlaceHolder;

    @BindView(R.id.btn_next)
    Button btnNext;

    @BindView(R.id.btn_previous)
    Button btnPrev;

    @Inject
    @PerActivity
    HttpDataSource.Factory httpDataSourceFactory;

    @Inject
    @PerActivity
    ExtractorsFactory extractorsFactory;

    private int stepIndex;
    private List<Step> stepList;

    public StepDetailFragment() {

        setRetainInstance(true);

    }

    public static StepDetailFragment newInstance(Step step) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            stepIndex = savedInstanceState.getInt(KEY_STEP_INDEX);
            loadStep(stepList.get(stepIndex));
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_STEP_INDEX, stepIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, view);

        RecipeComponent component = ((HasComponent<RecipeComponent>) getActivity()).getComponent();
        component.inject(this);

        simpleExoPlayerView.setPlayer(player);

        Bundle arguments = getArguments();
        Step step = arguments.getParcelable("step");

        if (step != null) {
            stepList = bakingRepository.stepsByRecipe(step.getRecipeId());
            stepIndex = stepList.indexOf(step);
            loadStep(step);
        }

        return view;
    }

    private void loadStep(Step step) {
        player.stop();
        getActivity().setTitle(step.getShortDescription());
        txtDescription.setText(step.getDescription());
        int size = stepList.size();
        showStepNumber();
        updateButtonVisibility(btnNext, stepIndex + 1 < size);
        updateButtonVisibility(btnPrev, stepIndex > 0);
        playVideoIfAvailable(step);
        showImageIfAvailable(step);
    }

    private void showStepNumber() {
        if (stepIndex == 0) {
            txtStepNumber.setText(getResources().getString(R.string.introduction));
        } else {
            txtStepNumber.setText(String.format(Locale.US, " %d of %d ", stepIndex, stepList.size() - 1));
        }
    }

    private void updateButtonVisibility(Button btn, boolean visible) {
        btn.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    private void showImageIfAvailable(Step step) {
        if (TextUtils.isEmpty(step.getThumbnailURL())) {
            imgThumbnail.setVisibility(View.GONE);
            return;
        }

        imgThumbnail.setVisibility(View.VISIBLE);
        glideWrapper.loadInto(step.getThumbnailURL(), imgThumbnail);
    }

    private void playVideoIfAvailable(Step step) {
        if (TextUtils.isEmpty(step.getVideoURL())) {
            hidePlayer();
            return;
        }
        if (!NetworkUtils.isNetworkAvailable(getContext())) {
            hidePlayer();
            View mainView = getActivity().findViewById(android.R.id.content);
            Snackbar.make(mainView, TextUtils.concat(getString(R.string.message_network_unavailable), ": ",
                    getString(R.string.message_video_cannot_played)), Snackbar.LENGTH_LONG).show();
            return;
        }

        simpleExoPlayerView.setTag(step.getId());
        showPlayer();
        preparePlayer(step.getVideoURL());
    }

    private void showPlayer() {
        imgVideoPlaceHolder.setVisibility(View.INVISIBLE);
        simpleExoPlayerView.setVisibility(View.VISIBLE);
    }

    private void hidePlayer() {
        simpleExoPlayerView.setVisibility(View.INVISIBLE);
        imgVideoPlaceHolder.setVisibility(View.VISIBLE);
    }

    private void preparePlayer(String videoUrl) {
        player.stop();
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(videoUrl), httpDataSourceFactory,
                extractorsFactory, new Handler(), this);
        player.prepare(videoSource, true, false);
    }


    @OnClick(R.id.btn_next)
    public void onNextClick() {
        stepIndex++;
        loadStep(stepList.get(stepIndex));
    }

    @OnClick(R.id.btn_previous)
    public void onPrevClick() {
        stepIndex--;
        loadStep(stepList.get(stepIndex));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
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

    private void releasePlayer() {
        if (player == null) {
            return;
        }
        player.release();
        player = null;
    }

    @Override
    public void onLoadError(IOException error) {
        Log.e(TAG, "onLoadError: " + error.getMessage());
    }


}
