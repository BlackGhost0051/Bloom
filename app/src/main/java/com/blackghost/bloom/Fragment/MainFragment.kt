package com.blackghost.bloom.Fragment

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackghost.bloom.Adapter.PhotoAdapter
import com.blackghost.bloom.Manager.FolderManager
import com.blackghost.bloom.R
import java.io.File


class MainFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private var files: List<File> = emptyList()
    private lateinit var folderManager: FolderManager

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var preferences: SharedPreferences

    private var savedScrollPosition: Int = 0
    private var scrollSpeedFactor: Float = 1.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        scrollSpeedFactor = preferences.getInt("inertia_speed", 2).toFloat()

        preferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == "inertia_speed") {
                scrollSpeedFactor = preferences.getInt("inertia_speed", 2).toFloat()
            }
        }

        recyclerView = view.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager


        folderManager = FolderManager(requireContext())

        files = folderManager.loadFilesFromFolder()
        recyclerView.adapter = PhotoAdapter(files)

        if (preferences.getBoolean("save_position", false)){
            savedScrollPosition = preferences.getInt("saved_scroll_position", 0)
            recyclerView.scrollToPosition(savedScrollPosition)
        }

        setupCustomScrollInertia()

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
        files = folderManager.loadFilesFromFolder(shuffle)
        recyclerView.adapter = PhotoAdapter(files)
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setupCustomScrollInertia() {
        recyclerView.setOnTouchListener(object : View.OnTouchListener {
            private var lastY = 0f

            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        val deltaY = event.y - lastY
                        recyclerView.scrollBy(0, (-deltaY * scrollSpeedFactor).toInt())
                        lastY = event.y
                    }

                    MotionEvent.ACTION_DOWN -> {
                        lastY = event.y
                    }
                }
                return false
            }
        })
    }
}