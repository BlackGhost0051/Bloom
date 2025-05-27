package com.blackghost.bloom.Fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackghost.bloom.Adapter.PhotoAdapter
import com.blackghost.bloom.Manager.PhotoManager
import com.blackghost.bloom.R
import java.io.File


class MainFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private var photos: List<File> = emptyList()
    private lateinit var photoManager: PhotoManager

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var preferences: SharedPreferences

    private var savedScrollPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        recyclerView = view.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager


        photoManager = PhotoManager(requireContext())
        photoManager.createGPhotosFolderIfNeeded()

        photos = photoManager.loadPhotosFromGPhotos()
        recyclerView.adapter = PhotoAdapter(photos)

        if (preferences.getBoolean("save_position", false)){
            savedScrollPosition = preferences.getInt("saved_scroll_position", 0)
            recyclerView.scrollToPosition(savedScrollPosition)
        }

        return view
    }

    override fun onPause() {
        super.onPause()

        if (preferences.getBoolean("save_position", false)) {
            val currentPosition = layoutManager.findFirstVisibleItemPosition()
            preferences.edit().putInt("saved_scroll_position", currentPosition).apply()
        }
    }

    fun refreshPhotos(shuffle: Boolean = false) {
        photos = photoManager.loadPhotosFromGPhotos(shuffle)
        recyclerView.adapter = PhotoAdapter(photos)
    }
}