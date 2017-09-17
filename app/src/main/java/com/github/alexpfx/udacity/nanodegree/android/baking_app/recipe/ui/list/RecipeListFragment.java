package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.RecipesRepository;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.RecipesRepositoryImpl;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 1. Implementar onClick que abrirá RecipeDetailFragment.
 * 2.
 */
public class RecipeListFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "RecipeListFragment";

    @BindView(R.id.recycler_recipe_list)
    RecyclerView recycler;

    @Singleton
    @Inject
    RecipeAdapter adapter;

    @Singleton
    @Inject
    RecipesRepository repository;
    @Inject
    boolean isTablet;
    private OnRecipeSelectListener recipeSelectListener;

    public RecipeListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        ButterKnife.bind(this, view);

        initInjections();
        setupRecycler();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnRecipeSelectListener) {
            recipeSelectListener = (OnRecipeSelectListener) context;
        }
    }

    private void initInjections() {
        ((HasComponent<RecipeComponent>) getActivity()).getComponent().inject(this);
    }

    private void setupRecycler() {
        recycler.setLayoutManager(getLayoutManager());
        recycler.setAdapter(adapter);
        adapter.setListener(this);

        repository.recipes(new RecipesRepositoryImpl.Callback() {
            @Override
            public void onRecipesReceived(final List<Recipe> recipes) {

                adapter.setItemList(recipes);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (recipeSelectListener == null) {
            return;
        }
        Recipe recipe = (Recipe) view.getTag();
        recipeSelectListener.onRecipeSelect(recipe);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return (isTablet) ?
                new GridLayoutManager(getContext(), 3) :
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }
}
