package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.util.IngredientSpanableListHolder;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.util.HeadingableRecycleAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandre on 13/08/17.
 */
@PerActivity
public class IngredientsAdapter extends HeadingableRecycleAdapter {
    public static final String TITLE = "Ingredients";
    private IngredientSpanableListHolder spanableListHolder;


    @Inject
    public IngredientsAdapter(Context context) {
        super(R.layout.layout_generic_recyclerview_cardified_header, TITLE, context);
    }

    public void setItemList(List<Ingredient> itemList) {
        spanableListHolder = new IngredientSpanableListHolder(itemList, context());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context()).inflate(R.layout.item_ingredient, parent, false));
    }

    @Override
    public void onAddViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.bind(spanableListHolder, position);
    }

    @Override
    public int itemCount() {
        return spanableListHolder.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_ingredient)
        TextView txtIngredient;

        @BindView(R.id.text_ingredient_quantity)
        TextView txtQuantity;

        @BindView(R.id.text_ingredient_measure)
        TextView txtMeasure;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(IngredientSpanableListHolder holder, int position) {
            txtIngredient.setText(holder.getIngredient(position));
            txtQuantity.setText(holder.getQuantity(position));
            txtMeasure.setText(holder.getMeasure(position));
        }
    }
}

