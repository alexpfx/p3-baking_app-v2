package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandre on 01/08/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private static final String TAG = "RecipeAdapter";
    private List<Recipe> itemList = new ArrayList<>();
    private Context context;
    private View.OnClickListener listener;

    @Inject
    public RecipeAdapter(Context context) {
        this.context = context;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        if (listener == null) {
            throw new IllegalStateException("must set the listener");
        }
        view.setOnClickListener(listener);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+position);
        Recipe recipe = itemList.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        if (itemList == null) {
            return 0;
        }
        return itemList.size();
    }

    public void setItemList(List<Recipe> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

}

class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_recipe_name)
    TextView txtRecipeName;
    private View itemView;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;

        ButterKnife.bind(this, itemView);

    }


    public void bind(final Recipe recipe) {
        txtRecipeName.setText(recipe.getName());
        itemView.setTag(recipe);
    }
}
