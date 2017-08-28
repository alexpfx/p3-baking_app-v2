package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.RecipesRepository;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list.RecipeActivity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    @Singleton
    @Inject
    RecipesRepository repository;

    @PerActivity
    @Inject
    StepAdapter stepAdapter;
    OnStepSelectListener stepSelectListener;
    View.OnClickListener onItemRecipeClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Step step = (Step) view.getTag();
            stepSelectListener.onStepSelect(step);

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);
        RecipeComponent component = ((HasComponent<RecipeComponent>) getActivity()).getComponent();
        component.inject(this);

        int recipeId = getArguments().getInt(RecipeActivity.KEY_RECIPE_ID);

        List<Step> steps = repository.stepsByRecipe(recipeId);
        List<Ingredient> ingredients = repository.ingredientsByRecipe(recipeId);

        ingredientsAdapter.setItemList(ingredients);
        stepAdapter.setItemList(steps);
        stepAdapter.setOnClickListener(onItemRecipeClick);


        setupRecycler(recyclerIngredients, ingredientsAdapter, new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        setupRecycler(recyclerSteps, stepAdapter, new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false));
        return view;
    }

    private void setupRecycler(RecyclerView recyclerView, RecyclerView.Adapter adapter, LinearLayoutManager
            layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepSelectListener) {
            stepSelectListener = (OnStepSelectListener) context;
        }
    }


}
