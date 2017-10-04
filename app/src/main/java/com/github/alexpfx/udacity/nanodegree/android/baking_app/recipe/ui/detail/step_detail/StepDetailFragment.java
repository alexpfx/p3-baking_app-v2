package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail.step_detail;


import android.os.Bundle;
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
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.util.Util;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepDetailFragment extends Fragment {

    public static final String KEY_STEP_INDEX = "KEY_STEP_INDEX";
    public static final String KEY_CURRENT_POSITION = "KEY_CURRENT_POSITION";
    public static final String KEY_CURRENT_WINDOW_INDEX = "KEY_CURRENT_WINDOW_INDEX";

    public static final String KEY_PLAY_WHEN_READY = "KEY_PLAY_WHEN_READY";
    private static final String TAG = "StepDetailFragment";
    @PerActivity
    @Inject
    BakingRepository bakingRepository;

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

    ExoplayerManager exoplayerManager;


    private int stepIndex;

    private List<Step> stepList;
    private long currentPosition;
    private int currentWindowIndex;
    private boolean playWhenReady = true;

    public StepDetailFragment() {

//        setRetainInstance(true);

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
        Log.d(TAG, "onActivityCreated: ");
        if (savedInstanceState != null) {
            stepIndex = savedInstanceState.getInt(KEY_STEP_INDEX);
            currentPosition = savedInstanceState.getLong(KEY_CURRENT_POSITION);
            currentWindowIndex = savedInstanceState.getInt(KEY_CURRENT_WINDOW_INDEX);
            playWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);

        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewStateRestored: ");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        outState.putInt(KEY_STEP_INDEX, stepIndex);
        currentPosition = exoplayerManager.getCurrentPosition();
        outState.putLong(KEY_CURRENT_POSITION, currentPosition);

        currentWindowIndex = exoplayerManager.getCurrentWindowIndex();
        outState.putInt(KEY_CURRENT_WINDOW_INDEX, currentWindowIndex);

        playWhenReady = exoplayerManager.getPlayWhenReady();
        outState.putBoolean(KEY_PLAY_WHEN_READY, playWhenReady);

        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, view);

        RecipeComponent component = ((HasComponent<RecipeComponent>) getActivity()).getComponent();
        component.inject(this);

        Bundle arguments = getArguments();

        Step step = arguments.getParcelable("step");

        if (step != null && stepList == null) {
            stepList = bakingRepository.stepsByRecipe(step.getRecipeId());
            stepIndex = stepList.indexOf(step);
        }

        return view;
    }

    private void loadStep(Step step) {
        Log.d(TAG, "loadStep: ");
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
        Log.d(TAG, "preparePlayer: " + exoplayerManager);
        Log.d(TAG, "preparePlayer: currentPositon: " + currentPosition);
        if (exoplayerManager == null) {
            exoplayerManager = new ExoplayerManager(getContext(), simpleExoPlayerView);
            exoplayerManager.initializePlayer(currentWindowIndex, currentPosition, playWhenReady);
        }
        exoplayerManager.playMedia(videoUrl);
    }


    @OnClick(R.id.btn_next)
    public void onNextClick() {
        stepIndex++;
        resetPlayerStatus();
        loadCurrentStep();
    }

    private void resetPlayerStatus() {
        Log.d(TAG, "resetPlayerStatus: ");
        if (exoplayerManager != null) {

            exoplayerManager.stop();

        }
        currentWindowIndex = 0;
        currentPosition = -1;
        playWhenReady = true;
    }

    @OnClick(R.id.btn_previous)
    public void onPrevClick() {
        stepIndex--;
        resetPlayerStatus();
        loadCurrentStep();
    }

    private void loadCurrentStep() {
        loadStep(stepList.get(stepIndex));
    }


    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        if (Util.SDK_INT <= 23) {
            loadCurrentStep();
        }
    }


    @Override
    public void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
        if (Util.SDK_INT > 23) {
            loadCurrentStep();
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private void releasePlayer() {
        Log.d(TAG, "releasePlayer: ");
        if (exoplayerManager != null) {
            exoplayerManager.releasePlayer();
        }
        exoplayerManager = null;
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

}
