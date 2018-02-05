package cz.dmn.droneworldguide.util

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imagePath")
fun bindImagePath(imageView: ImageView, imagePath: String?) {
    if (imagePath == null) {
        imageView.setImageResource(0)
    } else {
        Glide
                .with(imageView)
                .load(imagePath)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
    }
}

private fun loadBitmap(path: String, maxSize: Int): Bitmap? {
    val opts = BitmapFactory.Options()
    opts.inJustDecodeBounds = true
    BitmapFactory.decodeFile(path, opts)
    if (opts.outWidth <= 0 || opts.outHeight <= 0) {
        return null
    }
    val smallerSize = Math.min(opts.outWidth, opts.outHeight)
    opts.inJustDecodeBounds = false
    opts.inSampleSize = Math.min(1, smallerSize / maxSize)
    return BitmapFactory.decodeFile(path, opts)
}
