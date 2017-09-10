package com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.RecipesRepository;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.GlideWrapper;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.btn_next)
    Button btnNext;

    @BindView(R.id.btn_previous)
    Button btnPrev;

    private int stepIndex;


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

        RecipeComponent component = ((HasComponent<RecipeComponent>) getActivity()).getComponent();
        component.inject(this);

        simpleExoPlayerView.setPlayer(player);
        simpleExoPlayerView.requestFocus();


        Bundle arguments = getArguments();
        Step step = arguments.getParcelable("step");

        if (step != null) {
            stepList = recipesRepository.stepsByRecipe(step.getRecipeId());
            stepIndex = stepList.indexOf(step);
            loadStep(step);
        }


        return view;
    }


    public void loadStep(Step step) {
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
        if (stepIndex == 0){
            txtStepNumber.setText(getResources().getString(R.string.introduction));
        }else {
            txtStepNumber.setText(String.format(Locale.US, " %d of %d ", stepIndex, stepList.size() - 1));
        }
    }


    private void updateButtonVisibility(Button btn, boolean visible) {
        btn.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    private void showImageIfAvailable(Step step) {
        if (step.getThumbnailURL() == null || step.getThumbnailURL().isEmpty()) {
            imgThumbnail.setVisibility(View.GONE);
            return;
        }

        imgThumbnail.setVisibility(View.VISIBLE);
        glideWrapper.loadInto(step.getThumbnailURL(), imgThumbnail);
    }

    private static final String TAG = "StepDetailFragment";



    private void playVideoIfAvailable(Step step) {
        simpleExoPlayerView.setVisibility(View.INVISIBLE);
        player.stop();
        if (step.getVideoURL() == null || step.getVideoURL().isEmpty()) {
            simpleExoPlayerView.setVisibility(View.GONE);
            Log.d(TAG, "playVideoIfAvailable: "+stepIndex);
            return;
        }
        simpleExoPlayerView.setVisibility(View.VISIBLE);
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


    @OnClick(R.id.btn_next)
    public void onNextClick(View view) {
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
