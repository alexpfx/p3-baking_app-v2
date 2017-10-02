package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.BakingRepository;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Ingredient;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Step;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.PerActivity;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list.RecipeActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment {

    private static final String TAG = "RecipeDetailFragment";

    @BindView(R.id.recycler_ingredient_list)
    RecyclerView recyclerIngredients;

    @BindView(R.id.recycler_step_list)
    RecyclerView recyclerSteps;

    @PerActivity
    @Inject
    IngredientsAdapter ingredientsAdapter;

    @Singleton
    @Inject
    BakingRepository repository;

    @PerActivity
    @Inject
    StepAdapter stepAdapter;

    @BindView(R.id.layout_content_cannot_loaded)
    View viewErrorMsg;

    @BindView(R.id.recipe_detail_content)
    View viewContent;

    OnStepSelectListener stepSelectListener;
    View.OnClickListener onItemRecipeClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Step step = (Step) view.getTag();
            stepSelectListener.onStepSelect(step);
        }
    };

    public static RecipeDetailFragment newInstance() {
        return new RecipeDetailFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);

        initializeInjections();
        initializeRecyclerViews();
        return view;
    }


    public void loadRecipe (Recipe recipe){
        List<Step> steps = repository.stepsByRecipe(recipe.getId());
        List<Ingredient> ingredients = repository.ingredientsByRecipe(recipe.getId());

        if (steps.isEmpty() || ingredients.isEmpty()){
            hideContent();
            return;
        }

        showContent ();

        ingredientsAdapter.setItemList(ingredients);
        stepAdapter.setItemList(steps);
        stepAdapter.setOnClickListener(onItemRecipeClick);
        setTitle(recipe.getName());

    }

    private void showContent() {
        viewContent.setVisibility(View.VISIBLE);
        viewErrorMsg.setVisibility(View.GONE);
    }

    private void hideContent() {
        viewErrorMsg.setVisibility(View.VISIBLE);
        viewContent.setVisibility(View.GONE);
    }


    public void setTitle(CharSequence title) {
        FragmentActivity activity = getActivity();
        activity.setTitle(title);
    }

    private void initializeRecyclerViews() {
        setupRecycler(recyclerIngredients, ingredientsAdapter, new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        setupRecycler(recyclerSteps, stepAdapter, new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false));
    }

    private void initializeInjections() {
        RecipeComponent component = ((HasComponent<RecipeComponent>) getActivity()).getComponent();
        component.inject(this);
    }

    private void setupRecycler(RecyclerView recyclerView, RecyclerView.Adapter adapter, LinearLayoutManager
            layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layoutManager
//                .getOrientation()));
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onRecipeReceived(Recipe recipe) {
        loadRecipe(recipe);
    }

    public Recipe getRecipe() {
        return getArguments().getParcelable(RecipeActivity.KEY_RECIPE);
    }


//110

}
