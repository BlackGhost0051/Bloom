package com.blackghost.bloom.Manager

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.blackghost.bloom.R
import java.io.File

class PhotoManager(private val context: Context) {

    private val gPhotosDir: File by lazy {
        val externalMediaPath = context.getExternalFilesDir(null)?.absolutePath
            ?.replace("/Android/data/", "/Android/media/")

        val directory = File(externalMediaPath ?: "", "Photos")

        if (!directory.exists()) {
            val created = directory.mkdirs()
            if (created) {
                Toast.makeText(context, "Photos directory created", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to create Photos directory", Toast.LENGTH_SHORT).show()
            }
        }

        directory
    }

    fun createGPhotosFolderIfNeeded() {
        if (!gPhotosDir.exists()) {
            val created = gPhotosDir.mkdirs()
            if (created) {
                Toast.makeText(context, "G_photos folder created", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to create G_photos folder", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loadPhotosFromGPhotos(): List<File> {
        return gPhotosDir.listFiles { file ->
            file.extension.lowercase() in listOf("jpg", "jpeg", "png")
        }?.toList() ?: emptyList()
    }

    fun togglePrivacy( itemIconTintCallback: (Int) -> Unit, notifyMediaScanner: () -> Unit ) {
        val nomediaFile = File(gPhotosDir, ".nomedia")

        if (nomediaFile.exists()) {
            AlertDialog.Builder(context)
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to make the folder public?")
                .setPositiveButton("Yes") { _, _ ->
                    if (nomediaFile.delete()) {
                        Toast.makeText(context, "Folder is now public", Toast.LENGTH_SHORT).show()
                        itemIconTintCallback(android.R.color.white)
                        notifyMediaScanner()
                    } else {
                        Toast.makeText(context, "Failed to make folder public", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        } else {
            try {
                if (nomediaFile.createNewFile()) {
                    Toast.makeText(context, "Folder is now private", Toast.LENGTH_SHORT).show()
                    itemIconTintCallback(R.color.active_feature)
                    notifyMediaScanner()
                } else {
                    Toast.makeText(context, "Failed to create .nomedia file", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
