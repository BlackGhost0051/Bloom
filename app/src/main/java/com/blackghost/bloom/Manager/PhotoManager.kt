package com.blackghost.bloom.Manager

import android.content.Context
import android.widget.Toast
import java.io.File

class PhotoManager(private val context: Context) {

    fun createGPhotosFolderIfNeeded() {
        val externalMediaPath = context.getExternalFilesDir(null)?.absolutePath
            ?.replace("/Android/data/", "/Android/media/")

        if (externalMediaPath == null) {
            return
        }

    }
}
