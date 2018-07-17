package com.example.android.resultvisualizer;

import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_main);
        EditTextPreference preference = (EditTextPreference) getPreferenceScreen().findPreference("quality");
        preference.setSummary(preference.getText());
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
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
    }
}
