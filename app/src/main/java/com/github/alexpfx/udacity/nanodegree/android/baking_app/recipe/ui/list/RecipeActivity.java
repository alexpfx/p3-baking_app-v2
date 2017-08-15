package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.list;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ActivityModule;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.ApplicationComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.DaggerRecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui.detail.StepActivity;

import java.util.List;


public class RecipeActivity extends AppCompatActivity implements HasComponent<RecipeComponent>, OnRecipeSelectListener {

    public static final String KEY_RECIPE_ID = "KEY_RECIPE_ID";
    private static final String TAG = "RecipeActivity";
    private RecipeComponent recipeComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        recipeComponent = DaggerRecipeComponent.builder().applicationComponent(
                ((HasComponent<ApplicationComponent>) getApplication()).getComponent()
        ).activityModule(new ActivityModule(this)).build();

        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    public RecipeComponent getComponent() {
        return recipeComponent;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRecipeSelect(Recipe recipe) {
        Intent intent = new Intent(getApplicationContext(), StepActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_RECIPE_ID, recipe.getId());
        intent.putExtras(bundle);


        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void cell() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION}, 323);
        }

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = telephonyManager.getNetworkType();
        List<CellInfo> allCellInfo = telephonyManager.getAllCellInfo();
        int phoneCount = telephonyManager.getPhoneCount();
        Log.d(TAG, "cell: p count"+phoneCount);
        Log.d(TAG, "cell: count "+telephonyManager.getAllCellInfo().size());
        for (CellInfo cellInfo : allCellInfo) {
            CellInfoWcdma info = (CellInfoWcdma) cellInfo;
            CellIdentityWcdma cellIdentity = info.getCellIdentity();
            Log.d(TAG, "cell: "+info.getCellSignalStrength());
            Log.d(TAG, "cell: identity "+ cellIdentity);
        }
    }
}
