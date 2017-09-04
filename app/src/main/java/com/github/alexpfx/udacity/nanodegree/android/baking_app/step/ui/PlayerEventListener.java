package com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui;

import android.support.v4.media.session.MediaSessionCompat;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import javax.inject.Inject;

import static android.support.v4.media.session.PlaybackStateCompat.Builder;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_PAUSED;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_PLAYING;

/**
 * Created by alexandre on 03/09/17.
 */

public class PlayerEventListener implements Player.EventListener {
    private MediaSessionCompat mediaSession;
    private Builder stateBuidler;
    private SimpleExoPlayer exoPlayer;

    @Inject
    public PlayerEventListener(MediaSessionCompat mediaSession, Builder stateBuidler, SimpleExoPlayer exoPlayer) {
        this.mediaSession = mediaSession;
        this.stateBuidler = stateBuidler;
        this.exoPlayer = exoPlayer;
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
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (Player.STATE_READY == playbackState && playWhenReady) {
            stateBuidler.setState(STATE_PLAYING, exoPlayer.getCurrentPosition(), 1f);
        } else if (Player.STATE_READY == playbackState) {
            stateBuidler.setState(STATE_PAUSED, exoPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(stateBuidler.build());
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
