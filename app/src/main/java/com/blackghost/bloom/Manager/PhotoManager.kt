package com.blackghost.bloom.Manager

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.blackghost.bloom.R
import java.io.File

class PhotoManager(private val context: Context) {

//    private val gPhotosDir: File = File(context.getExternalFilesDir(null), "Photos")
    private val gFilesDir: File = context.getExternalFilesDir(null) ?: context.filesDir

    fun getGPhotosDir(): File = gFilesDir

    fun createGPhotosFolderIfNeeded() {

        Log.d("DIR", gFilesDir.toString())
        if (!gFilesDir.exists()) {
            val created = gFilesDir.mkdirs()
            if (created) {
                Toast.makeText(context, "Folder created", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to create folder", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loadPhotosFromGPhotos(shuffle: Boolean = false): List<File> {
        val files = gFilesDir.listFiles { file ->
            file.extension.lowercase() in listOf("jpg", "jpeg", "png")
        }?.toList() ?: emptyList()

        return if (shuffle) files.shuffled() else files
    }

    fun togglePrivacy( itemIconTintCallback: (Int) -> Unit, notifyMediaScanner: () -> Unit ) {
        val nomediaFile = File(gFilesDir, ".nomedia")

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
