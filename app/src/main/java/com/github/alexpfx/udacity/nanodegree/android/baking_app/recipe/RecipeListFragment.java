package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.data.Recipe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 1. Obter repositório e fazer consulta.
 * 2. Preencher lista do adapter.
 */
public class RecipeListFragment extends Fragment {

    private static final String TAG = "RecipeListFragment";

    @BindView(R.id.recycler_recipe_list)
    RecyclerView recycler;

    @Inject
    RecipeAdapter adapter;

    @Inject
    RecipesRepository repository;

    private boolean isTablet = false;

    public RecipeListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        ButterKnife.bind(this, view);

        initInjections ();
        setupRecycler();

        Log.d(TAG, "onCreateView: "+adapter);
        return view;
    }

    private void initInjections() {
        ((HasComponent<RecipeComponent>) getActivity()).getComponent().inject(this);
    }

    private void setupRecycler() {
        final RecyclerView.LayoutManager layoutManager;
        if (isTablet) {
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        } else {
            layoutManager = new GridLayoutManager(getContext(), 3);
        }
        recycler.setAdapter(adapter);

        List<Recipe> recipes = repository.recipes();
        Log.d(TAG, "setupRecycler: "+recipes);

        recycler.setLayoutManager(layoutManager);
    }

}
