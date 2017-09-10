package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.GlideWrapper;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.HeadingableRecycleAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandre on 13/08/17.
 */

@PerActivity
public class StepAdapter extends HeadingableRecycleAdapter {

    public static final String TITLE = "Steps";
    private final GlideWrapper glideWrapper;
    private List<Step> itemList;
    private Context context;
    private View.OnClickListener onClickListener;
    private int selectedPosition = -1;

    @Inject
    public StepAdapter(Context context, GlideWrapper glideWrapper) {
        super(R.layout
                .layout_generic_recyclerview_cardified_header, TITLE, context);
        this.context = context;
        this.glideWrapper = glideWrapper;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_step, parent, false));
    }

    @Override // onBindViewHolder
    public void onAddViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        Step step = itemList.get(position);
        vh.bind(step, onClickListener, position, itemList.size());
    }

    @Override
    public int itemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    public void setItemList(List<Step> itemList) {
        this.itemList = itemList;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final String TAG = "ViewHolder";
        @BindView(R.id.text_step_short_description)
        TextView txtShortDescription;


        @BindView(R.id.image_step_has_video)
        ImageView imgHasVideo;

        private View.OnClickListener onClickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(Step step, View.OnClickListener onClickListener, int index, int length) {
            this.onClickListener = onClickListener;

            itemView.setOnClickListener(this);
            itemView.setTag(step);

            loadHasVideo(step);

            txtShortDescription.setText(step.getShortDescription());
        }


        private void loadHasVideo(Step step) {
            if (step.getVideoURL() != null && !step.getVideoURL().isEmpty()) {
                imgHasVideo.setImageResource(R.drawable.ic_videocam_black_24dp);
            } else {
                imgHasVideo.setImageResource(R.drawable.ic_videocam_off_black_24dp);
            }
        }


        @Override
        public void onClick(View view) {
            selectedPosition = getAdapterPosition();
            onClickListener.onClick(view);
            Log.d(TAG, "onClick: " + selectedPosition);
            notifyDataSetChanged();
        }

    }


}

