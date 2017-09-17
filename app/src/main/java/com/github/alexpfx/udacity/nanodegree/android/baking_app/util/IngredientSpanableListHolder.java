package com.github.alexpfx.udacity.nanodegree.android.baking_app.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;

import java.util.List;
import java.util.Locale;

/**
 * Created by alexandre on 13/09/17.
 */

public class IngredientSpanableListHolder {

    private final Context context;
    private List<Ingredient> ingredients;

    public IngredientSpanableListHolder(@NonNull List<Ingredient> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    public Spannable getIngredient(int index) {
        checkSize(index);
        return formatIngredient(ingredients.get(index).getIngredient());
    }

    public int size() {
        return ingredients.size();
    }

    public Spannable getQuantity(int index) {
        checkSize(index);
        return formatQuantity(ingredients.get(index).getQuantity());
    }

    public Spannable getMeasure(int index) {
        checkSize(index);
        return formatMeasure(ingredients.get(index).getMeasure());
    }

    private void checkSize(int index) {
        if (ingredients.size() <= index) {
            int size = ingredients.size();
            throw new ArrayIndexOutOfBoundsException("index: " + index + " size: " + size);
        }
    }

    private Spannable formatMeasure(String measure) {
        return new SpannableStringBuilder(measure);
    }


    private String getQuantityFormated(double quantity) {
        if (quantity == (long) quantity) {
            return String.format(Locale.US, "%s", String.valueOf((long) quantity));
        } else {
            return String.format(Locale.US, "%.1f", quantity);
        }
    }

    private Spannable formatQuantity(double quantity) {
        String quantityText = getQuantityFormated(quantity);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(quantityText);
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        spannableStringBuilder.setSpan(styleSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder.append(" ");
    }


    private Spannable formatIngredient(String ingredient) {
        String camelCase = Character.toUpperCase(ingredient.charAt(0)) + ingredient.substring(1);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color
                .colorAccent));
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(camelCase);
        spannableStringBuilder.setSpan(colorSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder.append(" ");
    }


}
