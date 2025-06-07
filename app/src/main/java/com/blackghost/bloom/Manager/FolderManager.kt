package com.blackghost.bloom.Manager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File

class FolderManager(private val context: Context) {

    private var folder: File

    init {
        val root = Environment.getExternalStorageDirectory()
        folder = File(root, "Bloom")

        Log.d("FolderManager", folder.toString())

        if(!folder.exists()){
            val created = folder.mkdirs()

            if (created) {
                Toast.makeText(context, "Folder '/Bloom' created", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to create '/Bloom'", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getFolder(): File
    {
        return folder
    }

    fun loadFilesFromFolder(random: Boolean = false): List<File>{
        val files = folder.listFiles { file ->
            file.extension.lowercase() in listOf("jpg", "jpeg", "png", "mp4")
        }?.toList() ?: emptyList()

        if (random){
            return files.shuffled()
        }
        return files
    }

    fun togglePrivacy(){
        val file = File(folder, ".nomedia")

        if (file.exists()){
            Toast.makeText(context,"None privacy", Toast.LENGTH_SHORT).show()
            file.delete()
        } else {
            Toast.makeText(context,"Privacy", Toast.LENGTH_SHORT).show()
            file.createNewFile()
        }

        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(folder)))
    }
}