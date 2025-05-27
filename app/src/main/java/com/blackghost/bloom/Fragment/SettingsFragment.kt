package com.blackghost.bloom.Fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.blackghost.bloom.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)


        val darkModeSwitch = findPreference<SwitchPreferenceCompat>("dark_mode")
        darkModeSwitch?.setOnPreferenceChangeListener { _, newValue ->
            val enabled = newValue as Boolean
            AppCompatDelegate.setDefaultNightMode(
                if (enabled) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            requireActivity().recreate()
            true
        }
    }
}