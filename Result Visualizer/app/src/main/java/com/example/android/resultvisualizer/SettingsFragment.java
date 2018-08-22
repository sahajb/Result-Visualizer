package com.example.android.resultvisualizer;

import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import static com.example.android.resultvisualizer.Utilities.JsonUtils.invalidateJson;
import static com.example.android.resultvisualizer.Utilities.JsonUtils.jsonValid;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_main);
        EditTextPreference textPreference = (EditTextPreference) getPreferenceScreen().findPreference("quality");
        textPreference.setSummary(textPreference.getText());
        textPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String string = newValue.toString();
                Toast error = Toast.makeText(getContext(), "Please enter a valid input", Toast.LENGTH_SHORT);
                try {
                    int size = Integer.parseInt(string);
                    if (size > 100 || size < 0) {
                        error.show();
                        return false;
                    }
                } catch (NumberFormatException nfe) {
                    error.show();
                    return false;
                }
                preference.setSummary(string);
                return true;

            }
        });
        Preference preference = getPreferenceScreen().findPreference("cache");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (jsonValid()) {
                    invalidateJson();
                    Toast.makeText(getContext(), "Cache Cleared", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), "No cache to clear", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
