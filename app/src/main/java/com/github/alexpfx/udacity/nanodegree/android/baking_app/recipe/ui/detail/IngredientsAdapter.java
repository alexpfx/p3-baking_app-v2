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
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.HeadingableRecycleAdapter;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandre on 13/08/17.
 */
@PerActivity
public class IngredientsAdapter extends HeadingableRecycleAdapter {
    public static final String TITLE = "Ingredients";
    private List<Ingredient> itemList;


    @Inject
    public IngredientsAdapter(Context context) {
        super(R.layout.layout_generic_recyclerview_cardified_header, TITLE, context);
    }

    public void setItemList(List<Ingredient> itemList) {
        this.itemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context()).inflate(R.layout.item_ingredient, parent, false));
    }

    @Override
    public void onAddViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.bind(itemList.get(position));
    }

    @Override
    public int itemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    private String getQuantityFormated(double quantity, String measure) {
        if (quantity == (long) quantity) {
            return String.format(Locale.US, "%s %s", String.valueOf(quantity), measure);
        } else {
            return String.format(Locale.US, "%.1f %s", quantity, measure);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_ingredient)
        TextView txtIngredient;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Ingredient ingredient) {
            String ingredientText = ingredient.getIngredient();
            txtIngredient.setText(String.format(Locale.US, "%s %s ", getQuantityFormated(ingredient.getQuantity(),
                    ingredient.getMeasure()), Character.toUpperCase(ingredientText.charAt(0)) +
                    ingredientText
                            .substring(1)));
        }
    }
}

