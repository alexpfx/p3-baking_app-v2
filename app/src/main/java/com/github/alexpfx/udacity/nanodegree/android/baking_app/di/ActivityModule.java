package com.github.alexpfx.udacity.nanodegree.android.baking_app.di;

import android.app.Activity;
import android.content.Context;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alexandre on 31/07/17.
 */
@Module
public class ActivityModule {
    private final Activity activity;
    private String mediaSessionTag;

    public ActivityModule(Activity activity, String mediaSessionTag) {
        this.activity = activity;
        this.mediaSessionTag = mediaSessionTag;
    }

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return activity;
    }


    private static final String TAG = "ActivityModule";

    @Provides
    @PerActivity
    BandwidthMeter bandwidthMeter (){

        return new DefaultBandwidthMeter();
    }


    @Provides
    @PerActivity
    TrackSelector trackSelector(BandwidthMeter bandwidthMeter) {
        return new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
    }


    @Provides
    @PerActivity
    SimpleExoPlayer exoplayer(Activity context, TrackSelector trackSelector) {
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        return player;
    }


    @PerActivity
    @Provides
    PlaybackStateCompat.Builder builder (){
        return new PlaybackStateCompat.Builder();
    }

    @Provides
    @PerActivity
    PlaybackStateCompat playbackStateCompat(PlaybackStateCompat.Builder builder) {
        return builder.setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat
                .ACTION_PAUSE | PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS | PlaybackStateCompat.ACTION_PLAY_PAUSE).build();
    }


    @Provides
    @PerActivity
    MediaSessionCompat.Callback sessionCallback (final SimpleExoPlayer player){

        return new MediaSessionCompat.Callback(){
            @Override
            public void onPlay() {
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                player.setPlayWhenReady(false);
            }

            @Override
            public void onSkipToPrevious() {
                player.seekTo(0);
            }
        };

    }

    @Provides
    @PerActivity
    MediaSessionCompat mediaSessionCompat(Context context, PlaybackStateCompat state, MediaSessionCompat.Callback
            callback) {
        MediaSessionCompat mediaSession = new MediaSessionCompat(context, mediaSessionTag);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat
                .FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);
        mediaSession.setPlaybackState(state);
        mediaSession.setActive(true);
        mediaSession.setCallback(callback);
        return mediaSession;
    }


}
