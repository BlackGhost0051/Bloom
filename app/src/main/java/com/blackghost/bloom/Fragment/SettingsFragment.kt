package com.blackghost.bloom.Fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.blackghost.bloom.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}