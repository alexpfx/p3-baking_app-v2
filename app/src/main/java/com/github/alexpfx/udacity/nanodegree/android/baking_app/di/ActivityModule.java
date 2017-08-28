package com.github.alexpfx.udacity.nanodegree.android.baking_app.di;

import android.app.Activity;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alexandre on 31/07/17.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides @PerActivity
    Activity provideActivity (){
        return activity;
    }

    @Provides @PerActivity
    TrackSelector providesTrackSelector (){
        return new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter()));
    }



    @Provides @PerActivity
    SimpleExoPlayer providesPlayer(Activity context, TrackSelector trackSelector){
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        player.setPlayWhenReady(true);
        return player;
    }



}
