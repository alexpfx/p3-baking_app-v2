package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list.RecipeActivity;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment {

    private static final String TAG = "RecipeDetailFragment";

    @BindView(R.id.recycler_ingredients)
    RecyclerView recyclerIngredients;

    @BindView(R.id.recycler_steps)
    RecyclerView recyclerSteps;

    @PerActivity
    @Inject
    IngredientsAdapter ingredientsAdapter;

    @PerActivity
    @Inject
    StepAdapter stepAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);
        RecipeComponent component = ((HasComponent<RecipeComponent>) getActivity()).getComponent();
        component.inject(this);

        int recipe = getArguments().getInt(RecipeActivity.KEY_RECIPE_ID);


        ingredientsAdapter.setItemList(Arrays.asList(new Ingredient(2.4f, "units", "coco"), new Ingredient(3.23f, "ml", "leite"), new Ingredient(12.3f, "g", "nutella")));
        //stepAdapter.setItemList(Arrays.asList(new Step("misturar ingredientes", "misturar ingredients"), new Step("bater na batedeira", "bater na batedeira")));
        setupRecycler(recyclerIngredients, ingredientsAdapter, new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setupRecycler(recyclerSteps, stepAdapter, new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }


    private void setupRecycler(RecyclerView recyclerView, RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


}
