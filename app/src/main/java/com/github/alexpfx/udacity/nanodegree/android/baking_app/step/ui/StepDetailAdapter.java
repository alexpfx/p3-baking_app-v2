package com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.step.ui.navigation.NavigationViewHolder;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class StepDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int NAVIGATION = 0;
    private static final int PLAYER = 1;
    private static final int STEP = 2;
    private static final String TAG = "StepDetailAdapter";
    private final SimpleExoPlayer player;
    private Context context;
    private NavigationViewHolder navigationViewHolder;
    private List<Step> stepList;
    private int index;
    private OnStepLoadListener onStepLoadListener;

    public interface OnStepLoadListener {
        void onStepLoad (Step step);
    }

    @Inject
    public StepDetailAdapter(Context context, SimpleExoPlayer player) {
        this.context = context;
        this.player = player;
    }

    public void setOnStepLoadListener(OnStepLoadListener onStepLoadListener) {
        this.onStepLoadListener = onStepLoadListener;
    }

    public void setStepList(List<Step> stepList, int index) {
        this.index = index;
        this.stepList = stepList;
    }

    void notifyStepLoaded(Step step){
        if (onStepLoadListener != null){
            onStepLoadListener.onStepLoad(step);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        Log.d(TAG, "onCreateViewHolder: ");

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

    private RecyclerView.ViewHolder inflateNavigation(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_navigation, parent, false);
        navigationViewHolder = new NavigationViewHolder(view, this);
        return navigationViewHolder;
    }

    private RecyclerView.ViewHolder inflateStep(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_step, parent, false);

        return new StepDetailViewHolder(view);
    }

    private RecyclerView.ViewHolder inflatePlayer(LayoutInflater inflater, ViewGroup parent, SimpleExoPlayer player) {
        View view = inflater.inflate(R.layout.item_player, parent, false);

        return new PlayerViewHolder(view, player);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        Step step = getCurrentStep();

        switch (getItemViewType(position)) {
            case PLAYER:
                ((PlayerViewHolder) holder).bind(step);
                break;
            case STEP:
                ((StepDetailViewHolder) holder).bind(step);
                break;
            case NAVIGATION:
                ((NavigationViewHolder) holder).bind(step, index != stepList.size() - 1, index != 0);
                break;
        }
    }

    private Step getCurrentStep() {
        return stepList.get(index);
    }

    @Override
    public int getItemCount() {
        return stepList == null ? 0 : 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {
        Integer tag = (Integer) view.getTag();
        index += tag;
        notifyStepLoaded(getCurrentStep());
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PLAYER, STEP, NAVIGATION})
    @interface ViewTypes {
    }

    class StepDetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_text)
        TextView txtStep;

        public StepDetailViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        public void bind(Step step) {
            txtStep.setText(step.getDescription());
        }
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.video_player_view)
        SimpleExoPlayerView exoPlayerView;

        PlayerViewHolder(View view, SimpleExoPlayer exoPlayer) {
            super(view);
            ButterKnife.bind(this, view);
            exoPlayerView.setPlayer(exoPlayer);
        }
        
        public void bind(Step step) {
        }
    }

}
