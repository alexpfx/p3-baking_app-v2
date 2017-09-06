package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandre on 13/08/17.
 */

@PerActivity
public class StepAdapter extends RecyclerView.Adapter {


    private static final String TAG = "StepAdapter";
    private List<Step> itemList;
    private Context context;
    private View.OnClickListener onClickListener;
    private int selectedPosition = -1;

    @Inject
    public StepAdapter(Context context) {
        this.context = context;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        Step step = itemList.get(position);
        vh.bind(step, onClickListener, position, itemList.size());

    }


    @Override
    public int getItemCount() {
        return (itemList == null ? 0 : itemList.size());
    }


    public void setItemList(List<Step> itemList) {
        this.itemList = itemList;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final String TAG = "ViewHolder";
        @BindView(R.id.text_step_short_description)
        TextView txtShortDescription;

        @BindView(R.id.text_step_of)
        TextView txtStepOf;

        private View.OnClickListener onClickListener;
        private boolean selected;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Step step, View.OnClickListener onClickListener, int index, int length) {
            this.onClickListener = onClickListener;
            txtShortDescription.setText(step.getShortDescription());
            txtShortDescription.setTag(step);
            txtShortDescription.setOnClickListener(this);
            setSelected(getAdapterPosition() == selectedPosition);
            txtShortDescription.setSelected(selected);
            txtStepOf.setText(String.format(Locale.US, "%d / %d - ", index, length));
            Log.d(TAG, "bind: " + selectedPosition);
        }


        @Override
        public void onClick(View view) {
            selectedPosition = getAdapterPosition();
            onClickListener.onClick(view);
            Log.d(TAG, "onClick: " + selectedPosition);
            notifyDataSetChanged();
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}

