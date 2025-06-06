package com.blackghost.bloom.Manager

import android.content.Context
import android.widget.Toast
import java.io.File

class FolderManager(private val context: Context) {

    private var folder: File

    init {
        folder = context.getExternalFilesDir(null) ?: context.filesDir

        if(!folder.exists()){
            folder.mkdirs()

            if(!folder.exists()){
                Toast.makeText(context, "Folder created", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to create folder", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getFolder(): File
    {
        return folder
    }

    fun loadFilesFromFolder(){

    }

    fun makePrivacy(){
        val file = File(folder, ".nomedia")

        if (file.exists()){
            file.delete()
        } else {
            file.createNewFile()
        }
    }
}