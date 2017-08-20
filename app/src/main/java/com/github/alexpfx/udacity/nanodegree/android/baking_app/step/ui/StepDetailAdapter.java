package com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui.navigation.NavigationViewControl;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui.navigation.NavigationViewHolder;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class StepDetailAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int NAVIGATION = 0;
    private static final int PLAYER = 1;
    private static final int STEP = 2;
    private final SimpleExoPlayer player;
    private NavigationViewHolder.NavigableListener listener;
    private Context context;
    private Step step;
    private NavigationViewHolder navigationViewHolder;

    @Inject
    public StepDetailAdapter(Context context, SimpleExoPlayer player) {
        this.context = context;
        this.player = player;
    }

    public void setListener(NavigationViewHolder.NavigableListener listener) {
        this.listener = listener;
    }

    public void setStep(Step step) {
        this.step = step;
        notifyDataSetChanged();
    }

    public NavigationViewControl getNavigationControl() {
        return navigationViewHolder;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case PLAYER:
                return inflatePlayer(inflater, parent, player);
            case STEP:
                return inflateStep(inflater, parent);
            case NAVIGATION:
                return inflateNavigation(inflater, parent);
        }
        throw new IllegalArgumentException();
    }

    private BaseViewHolder inflateNavigation(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_steps_navigation, parent);
        navigationViewHolder = new NavigationViewHolder(view, listener);
        return navigationViewHolder;
    }

    private BaseViewHolder inflateStep(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_step, parent);
        return new StepDetailViewHolder(view);
    }

    private BaseViewHolder inflatePlayer(LayoutInflater inflater, ViewGroup parent, SimpleExoPlayer player) {
        View view = inflater.inflate(R.layout.item_player, parent);
        return new PlayerViewHolder(view, player);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(step);
    }

    @Override
    public int getItemCount() {
        return step == null ? 0 : 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PLAYER, STEP, NAVIGATION})
    @interface ViewTypes {
    }

    class StepDetailViewHolder extends BaseViewHolder {
        @BindView(R.id.text_step_description)
        TextView txtStepDescription;

        public StepDetailViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void bind(Step step) {
            txtStepDescription.setText(step.getDescription());
        }
    }

    class PlayerViewHolder extends BaseViewHolder {
        @BindView(R.id.video_player_view)
        SimpleExoPlayerView exoPlayerView;

        PlayerViewHolder(View view, SimpleExoPlayer exoPlayer) {
            super(view);
            ButterKnife.bind(this, view);
            exoPlayerView.setPlayer(exoPlayer);
        }

        @Override
        public void bind(Step step) {
        }
    }

}
