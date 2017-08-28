package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;
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
public class IngredientsAdapter extends RecyclerView.Adapter {
    private List<Ingredient> itemList;

    private Context context;

    @Inject
    public IngredientsAdapter(Context context) {
        this.context = context;
    }

    public void setItemList(List<Ingredient> itemList) {
        this.itemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ingredients, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolder";
        @BindView(R.id.text_ingredient)
        TextView txtIngredient;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Ingredient ingredient) {
            txtIngredient.setText(getFormatedText(ingredient));
        }
    }

    private String getFormatedText(Ingredient ingredient) {
        double quantity = ingredient.getQuantity();

        if (quantity == (long) quantity) {
            return String.format(Locale.US, "%s - %s %s", ingredient.getIngredient(), String.valueOf(quantity), ingredient.getMeasure());
        } else {
            return String.format(Locale.US, "%s - %.1f %s", ingredient.getIngredient(), quantity, ingredient.getMeasure());
        }
    }
}

