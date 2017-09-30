package com.github.alexpfx.udacity.nanodegree.android.baking_app.di;

import android.app.Activity;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alexandre on 31/07/17.
 */
@Module
public class ActivityModule {
    private static final String TAG = "ActivityModule";
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return activity;
    }

    //new Player
    @Provides
    @PerActivity
    SimpleExoPlayer exoplayer(Activity context, TrackSelector trackSelector) {
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        player.setPlayWhenReady(true);

        return player;
    }


    @Provides
    @PerActivity
    TrackSelector trackSelector(BandwidthMeter bandwidthMeter) {
        return new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
    }


    @Provides
    @PerActivity
    BandwidthMeter bandwidthMeter() {
        return new DefaultBandwidthMeter();
    }


    @Provides
    @PerActivity
    HttpDataSource.Factory httpDataSourceFactory(Activity context) {
        return new DefaultHttpDataSourceFactory(Util.getUserAgent(context, "baking_app"));
    }


    @Provides
    @PerActivity
    ExtractorsFactory extractorsFactory() {
        return new DefaultExtractorsFactory();
    }


}
