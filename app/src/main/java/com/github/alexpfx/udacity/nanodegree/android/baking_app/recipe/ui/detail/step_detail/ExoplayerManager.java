package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail.step_detail;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by alexandre on 03/10/17.
 */

public class ExoplayerManager {
    private static final BandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private SimpleExoPlayer player;
    private ComponentListener componentListener = new ComponentListener();
    private Context context;
    private SimpleExoPlayerView simpleExoPlayerView;


    public ExoplayerManager(Context context, SimpleExoPlayerView simpleExoPlayerView) {
        this.context = context;
        this.simpleExoPlayerView = simpleExoPlayerView;
    }

    public void initializePlayer(int windowIndex, long currentPosition, boolean playWhenReady) {
        if (player == null) {
            TrackSelection.Factory adaptativeTrackSelectorFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(context),
                    new DefaultTrackSelector(adaptativeTrackSelectorFactory), new DefaultLoadControl());

            player.addListener(new ComponentListener());
            simpleExoPlayerView.setPlayer(player);
            simpleExoPlayerView.requestFocus();
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(windowIndex, currentPosition);
        }

    }

    public void playMedia (String mediaUrl){
        MediaSource mediaSource = buildMediaSource(Uri.parse(mediaUrl));
        player.prepare(mediaSource, true, false);
    }


    public long getCurrentPosition() {
        return player == null ? -1 : player.getCurrentPosition();
    }

    public int getCurrentWindowIndex() {
        return player == null ? 0 : player.getCurrentWindowIndex();
    }

    public boolean getPlayWhenReady (){
        return player == null ? true: player.getPlayWhenReady();
    }



    public void releasePlayer() {
        if (player == null) {
            return;
        }

        stop();
        simpleExoPlayerView.setPlayer(null);
        player.removeListener(componentListener);
        player.release();
        player = null;
    }


    private MediaSource buildMediaSource(Uri uri) {
        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(Util.getUserAgent(context,
                "baking_app"));

        return new ExtractorMediaSource(uri,
                httpDataSourceFactory,
                new DefaultExtractorsFactory(), new Handler(), null);


    }

    public void stop() {
        if (player != null){
            player.stop();
        }
    }

    private class ComponentListener implements Player.EventListener {

        private static final String TAG = "ComponentListener";

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

            String stateString;
            switch (playbackState) {
                case Player.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;
                case Player.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case Player.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case Player.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
            Log.d(TAG, "changed state to " + stateString + " playWhenReady: " + playWhenReady);

        }


        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }


        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {

        }

        @Override
        public void onPositionDiscontinuity() {

        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

        }
    }
}
