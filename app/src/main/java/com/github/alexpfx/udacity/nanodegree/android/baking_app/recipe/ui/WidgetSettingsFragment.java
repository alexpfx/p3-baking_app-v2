package com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

import com.github.alexpfx.udacity.nanodegree.android.baking_app.R;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.BakingRepository;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.BakingRepositoryImpl;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.data.Recipe;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.di.HasComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.recipe.di.RecipeComponent;
import com.github.alexpfx.udacity.nanodegree.android.baking_app.widget.ingredient.UpdateIngredientsIntentService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alexandre on 14/09/17.
 */

public class WidgetSettingsFragment extends PreferenceFragmentCompat implements BakingRepositoryImpl
        .Callback<List<Recipe>>, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "WidgetSettingsFragment";
    @Singleton
    @Inject
    BakingRepository repository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);



    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_widget);

        initializeInjections();
        repository.recipes(this);
    }

    private void initializeInjections() {
        RecipeComponent component = ((HasComponent<RecipeComponent>) getActivity()).getComponent();
        component.inject(this);
    }



    @Override
    public void onReceive(List<Recipe> recipes) {
        String prefKey = getString(R.string.pref_recipe_id_key);
        ListPreference pref = (ListPreference) findPreference(prefKey);

        if (recipes == null || recipes.isEmpty()) {
            Toast.makeText(getContext(), getString(R.string.message_content_cannot_loaded), Toast.LENGTH_LONG).show();
            pref.setEnabled(false);
            return;
        }

        pref.setEnabled(true);
        CharSequence[] entries, entriesValues;
        int size = recipes.size();
        entries = new CharSequence[size];
        entriesValues = new CharSequence[size];

        for (int i = 0; i < size; i++) {
            Recipe recipe = recipes.get(i);
            entries[i] = recipe.getName();
            entriesValues[i] = String.valueOf(recipe.getId());
        }




        pref.setEntries(entries);
        pref.setEntryValues(entriesValues);

        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//

        onSharedPreferenceChanged(defaultSharedPreferences, prefKey);

    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference == null) {
            return;
        }

        String value = sharedPreferences.getString(preference.getKey(), "");
        setPreferenceSummary(preference, value);

        notifyWidget();

    }

    private void notifyWidget() {
        UpdateIngredientsIntentService.startUpdateIngredientsIntentService(getContext());
    }


    private void setPreferenceSummary(Preference preference, String value) {
        if (!(preference instanceof ListPreference)) {
            return;
        }

        ListPreference listPreference = (ListPreference) preference;
        int indexOfValue = listPreference.findIndexOfValue(value);

        if (indexOfValue >= 0) {
            listPreference.setSummary(listPreference.getEntries()[indexOfValue]);
        }

    }


}
