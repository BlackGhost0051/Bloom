package com.blackghost.bloom.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.blackghost.bloom.R

class SettingsFragment : PreferenceFragmentCompat() {

//    private val folderPickerRequestCode = 1001

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)


//        val folderPreference = findPreference<Preference>("custom_folder")
//        folderPreference?.setOnPreferenceClickListener {
//            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//            startActivityForResult(intent, folderPickerRequestCode)
//            true
//        }

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
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == folderPickerRequestCode && data != null) {
//            val uri = data.data ?: return
//            requireActivity().contentResolver.takePersistableUriPermission(
//                uri,
//                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
//            )
//            val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
//            prefs.edit().putString("custom_folder_uri", uri.toString()).apply()
//
//            findPreference<Preference>("custom_folder")?.summary = uri.path
//        }
//    }
}