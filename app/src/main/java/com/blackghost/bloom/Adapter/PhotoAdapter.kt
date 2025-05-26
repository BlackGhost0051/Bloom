package com.blackghost.bloom.Adapter

import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.blackghost.bloom.R
import java.io.File

class PhotoAdapter(private val photos: List<File>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val file = photos[position]
        val extension = file.extension.lowercase()

        when (extension) {
            "jpg", "jpeg", "png" -> {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                holder.imageView.setImageBitmap(bitmap)
            }

            "mp4" -> {
                val thumbnail = ThumbnailUtils.createVideoThumbnail(
                    file.toString(),
                    MediaStore.Images.Thumbnails.MINI_KIND
                )
                holder.imageView.setImageBitmap(thumbnail)

            }
        }
    }

    override fun getItemCount(): Int = photos.size
}
