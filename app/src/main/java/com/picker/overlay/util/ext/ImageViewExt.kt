package com.picker.overlay.util.ext

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.caverock.androidsvg.SVG
import com.picker.overlay.R
import com.picker.overlay.domain.model.Album
import com.picker.overlay.domain.model.Photo


/**
 * ImageView databinding Ext
 */


@BindingAdapter("albumCover")
fun bindAlbumCover(imageView: ImageView, model: Album?) {
    val circularProgressDrawable = DynamicView().getProgressbar(imageView.context)
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(model?.list?.get(0)?.uri)
        .sizeMultiplier(0.7f)
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



@BindingAdapter("overlayTarget")
fun bindOverlayTarget(imageView: ImageView, model: Photo?) {
    val circularProgressDrawable = DynamicView().getProgressbar(imageView.context)
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(model?.uri)
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

@BindingAdapter("thumbPhoto")
fun bindThumbPhoto(imageView: ImageView, model: Photo?) {
    val circularProgressDrawable = DynamicView().getProgressbar(imageView.context)
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(Uri.parse(model?.uri))
        .sizeMultiplier(0.7f)
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



@BindingAdapter("svgResource")
fun bindSvgResource(imageView: ImageView, path:String?) {

    val circularProgressDrawable = DynamicView().getProgressbar(imageView.context)
    circularProgressDrawable.start()

    val svgImg = path?.let {
        try {
            val svg = SVG.getFromAsset(imageView.context.assets, it)
            return@let PictureDrawable(svg.renderToPicture())
        }
        catch (e:RuntimeException) {
            return@let null
        }
    }

    Glide.with(imageView.context)
        .load(svgImg)
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