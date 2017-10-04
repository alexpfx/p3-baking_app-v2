package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.util.GlideWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandre on 01/08/17.
 */

@Singleton
public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {


    private static final String TAG = "RecipeAdapter";
    private GlideWrapper glideWrapper;
    private List<Recipe> itemList = new ArrayList<>();
    private Context context;
    private View.OnClickListener listener;

    @Inject
    public RecipeAdapter(Context context, GlideWrapper glideWrapper) {
        this.context = context;
        this.glideWrapper = glideWrapper;
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
        return new RecipeViewHolder(view, glideWrapper);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = itemList.get(position);
        holder.bind(context, recipe);
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

    private static final String TAG = "RecipeViewHolder";
    private final GlideWrapper glideWrapper;
    @BindView(R.id.text_recipe_name)
    TextView txtRecipeName;
    View itemView;
    @BindView(R.id.image_recipe)
    ImageView imgRecipe;


    public RecipeViewHolder(View itemView, GlideWrapper glideWrapper) {
        super(itemView);
        this.itemView = itemView;
        this.glideWrapper = glideWrapper;

        ButterKnife.bind(this, itemView);
    }


    public void bind(final Context context, final Recipe recipe) {

        String name = recipe.getName();
        txtRecipeName.setText(name);
        itemView.setTag(recipe);

        String image = recipe.getImage();
        if (!TextUtils.isEmpty(image)) {
            loadImage(recipe.getImage());
        } else {
            loadImage(getImageFromResource(context, recipe.getName()));
        }

    }

    private int getImageFromResource(Context context, String name) {
        String imageName = getImageName(name);
        Log.d(TAG, "getImageFromResource: " + imageName);
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    private String getImageName(String name) {
        return name.replaceAll("\\s+", "").toLowerCase();
    }

    private void loadImage(Object model) {
        glideWrapper.loadInto(model, imgRecipe);
    }


}
