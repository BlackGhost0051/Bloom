package com.blackghost.bloom.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackghost.bloom.Manager.PhotoManager
import com.blackghost.bloom.R
import java.io.File


class MainFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private var photos: List<File> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val photoManager = PhotoManager(requireContext())
        photoManager.createGPhotosFolderIfNeeded()

        return view
    }
}