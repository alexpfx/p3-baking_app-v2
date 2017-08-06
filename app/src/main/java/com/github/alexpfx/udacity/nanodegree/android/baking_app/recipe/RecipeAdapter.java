package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.data.Recipe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandre on 01/08/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private List<Recipe> itemList = new ArrayList<>();


    Context context;

    @Inject
    public RecipeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = itemList.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_recipe_name)
    TextView txtRecipeName;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bind(Recipe recipe) {
        txtRecipeName.setText(recipe.getName());
    }
}
