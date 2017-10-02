package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.BakingRepository;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;
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
public class RecipeListFragment extends Fragment implements View.OnClickListener, BakingRepository
        .Callback<List<Recipe>> {

    public static final String RECYCLER_STATE = "recycler_state";
    private static final String TAG = "RecipeListFragment";
    @BindView(R.id.recycler_recipe_list)
    RecyclerView recycler;



    @Singleton
    @Inject
    RecipeAdapter adapter;

    @Singleton
    @Inject
    BakingRepository repository;
    @Inject
    boolean isTablet;

    @BindView(R.id.layout_error_cannot_load_recipes)
    TextView textRecipesCannotLoaded;

    private OnRecipeSelectListener recipeSelectListener;
    private BroadcastReceiver networkStatusChanged = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (repository == null) {
                return;
            }

            if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                if (!repository.hasData()) {
                    repository.recipes(RecipeListFragment.this);
                }
            }
        }
    };

    public RecipeListFragment() {
        setRetainInstance(true);

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
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
        adapter.setListener(this);
        repository.recipes(this);

    }

    private void showRecipes(List<Recipe> data) {
        textRecipesCannotLoaded.setVisibility(View.INVISIBLE);
        adapter.setItemList(data);
        recycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        if (recipeSelectListener == null) {
            return;
        }
        Recipe recipe = (Recipe) view.getTag();
        recipeSelectListener.onRecipeSelect(recipe);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return (isTablet) ?
                new GridLayoutManager(getContext(), 3) :
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(networkStatusChanged, new IntentFilter(android.net.ConnectivityManager
                .CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(networkStatusChanged);
        super.onPause();
    }

    @Override
    public void onReceive(List<Recipe> data) {
        if (!data.isEmpty()) {
            showRecipes(data);
        } else {
            textRecipesCannotLoaded.setVisibility(View.VISIBLE);
        }
    }
}
