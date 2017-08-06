package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.data.remote;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.data.Recipe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by alexandre on 17/07/17.
 */
@Singleton
public class RecipeService {

    private final Endpoints mEndpoints;
    private static final String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";



    @Inject
    public RecipeService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://d17h27t6h515a5.cloudfront.net")
                .addConverterFactory(GsonConverterFactory.create()).build();
        mEndpoints = retrofit.create(Endpoints.class);

    }

    public Call<List<Recipe>> getAllRecipes() {
        return mEndpoints.getAllRecipes(URL);
    }

    interface Endpoints {
        @GET
        Call<List<Recipe>> getAllRecipes(@Url String url);
    }


}