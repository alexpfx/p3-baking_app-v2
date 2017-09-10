package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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

    private String getQuantityFormated(double quantity) {
        if (quantity == (long) quantity) {
            return String.format(Locale.US, "%s", String.valueOf((long) quantity));
        } else {
            return String.format(Locale.US, "%.1f", quantity);
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
            txtIngredient.setText(
                    TextUtils.concat(
                            getQuantity(ingredient.getQuantity()),
                            getMeasure (ingredient.getMeasure()),
                            getIngredient(ingredient.getIngredient())));
        }

        private CharSequence getMeasure(String measure) {
            return measure + "  ";
        }

        private Spannable getQuantity (double quantity){
            String quantityText = getQuantityFormated(quantity);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(quantityText);
            StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
            spannableStringBuilder.setSpan(styleSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableStringBuilder.append(" ");
        }
        private Spannable getIngredient (String ingredient){
            String camelCase = Character.toUpperCase(ingredient.charAt(0)) + ingredient.substring(1);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(context(), R.color
                    .colorAccent));
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(camelCase);
            spannableStringBuilder.setSpan(colorSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableStringBuilder.append(" ");
        }
    }
}

