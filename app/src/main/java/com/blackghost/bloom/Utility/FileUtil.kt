package com.blackghost.bloom.Utility

import android.content.Context
import android.net.Uri
import android.os.Environment

object FileUtil {
    fun getFullPathFromTreeUri(uri: Uri, context: Context): String? {
        val docId = android.provider.DocumentsContract.getTreeDocumentId(uri)
        val split = docId.split(":")
        val type = split[0]
        val relativePath = split.getOrNull(1) ?: return null

        val fullPath = when (type) {
            "primary" -> "${Environment.getExternalStorageDirectory()}/$relativePath"
            else -> "/storage/$type/$relativePath"
        }

        return fullPath
    }
}
