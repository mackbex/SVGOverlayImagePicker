package com.picker.overlay.util.ext

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.picker.overlay.R
import com.picker.overlay.domain.model.Album
import com.picker.overlay.domain.model.Photo
import com.picker.overlay.domain.model.Theme


/**
 * ImageView databinding Ext
 */


@BindingAdapter("albumCover")
fun bindAlbumCover(imageView: ImageView, model: Album?) {
    val circularProgressDrawable = CircularProgressDrawable(imageView.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
    }
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(model?.list?.get(0))
        .sizeMultiplier(0.5f)
        .fitCenter()
//        .diskCacheStrategy(DiskCacheStrategy.NONE)
//        .skipMemoryCache(true)
        .error(ColorDrawable(ContextCompat.getColor(imageView.context,R.color.dark_green_1)))
        .placeholder(circularProgressDrawable)
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                circularProgressDrawable.stop()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                circularProgressDrawable.stop()
                return false
            }
        })
        .into(imageView)
}

@BindingAdapter("photo")
fun bindPhoto(imageView: ImageView, model: Photo?) {
    val circularProgressDrawable = CircularProgressDrawable(imageView.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
    }
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(model?.uri)
        .sizeMultiplier(0.5f)
        .fitCenter()
//        .diskCacheStrategy(DiskCacheStrategy.NONE)
//        .skipMemoryCache(true)
        .error(ColorDrawable(ContextCompat.getColor(imageView.context,R.color.dark_green_1)))
        .placeholder(circularProgressDrawable)
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                circularProgressDrawable.stop()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                circularProgressDrawable.stop()
                return false
            }
        })
        .into(imageView)
}


@BindingAdapter("coverImage")
fun bindCoverImage(imageView: ImageView, model: Theme?) {
    val circularProgressDrawable = CircularProgressDrawable(imageView.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
    }
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(model?.cover_image)
        .fitCenter()
//        .diskCacheStrategy(DiskCacheStrategy.NONE)
//        .skipMemoryCache(true)
        .error(ColorDrawable(Color.parseColor(model?.color)))
        .placeholder(circularProgressDrawable)
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                circularProgressDrawable.stop()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                circularProgressDrawable.stop()
                return false
            }
        })
        .into(imageView)
}

@BindingAdapter("companyLogo")
fun bindCompanyLogo(imageView: ImageView, logoPath:String?) {

    val circularProgressDrawable = CircularProgressDrawable(imageView.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
    }
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(logoPath)
        .fitCenter()
//        .diskCacheStrategy(DiskCacheStrategy.NONE)
//        .skipMemoryCache(true)
        .placeholder(circularProgressDrawable)
        .error(R.drawable.logo_failed)
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                circularProgressDrawable.stop()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                circularProgressDrawable.stop()
                return false
            }
        })
        .into(imageView)
}