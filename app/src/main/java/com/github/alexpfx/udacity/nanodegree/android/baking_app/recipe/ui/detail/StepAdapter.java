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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandre on 13/08/17.
 */

@PerActivity
public class StepAdapter extends RecyclerView.Adapter {

    private List<Step> itemList;
    private Context context;


    @Inject
    public StepAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return (itemList == null ? 0 : itemList.size());
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_short_description)
        TextView txtShortDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private static final String TAG = "ViewHolder";
        public void bind (Step step){
            Log.d(TAG, "bind: "+step);
            txtShortDescription.setText(step.getShortDescription());
        }
    }

    public void setItemList(List<Step> itemList) {
        this.itemList = itemList;
    }
}

