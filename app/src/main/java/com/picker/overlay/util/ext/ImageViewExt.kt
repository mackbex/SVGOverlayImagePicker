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
import com.picker.overlay.util.getProgressbar


/**
 * ImageView databinding Ext
 */

/**
 * 앨범 커버 리스트 adapter 아이템
 */
@BindingAdapter("albumCover")
fun bindAlbumCover(imageView: ImageView, model: Album?) {
    val circularProgressDrawable = getProgressbar(imageView.context)
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(model?.list?.get(0)?.uri)
        .sizeMultiplier(0.7f) //optimize를 위해 resolution 0.7정도로 설정.
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


/**
 * 오버레이 뷰 안, 오리지멀 이미지 adapter 아이템
 */
@BindingAdapter("overlayTarget")
fun bindOverlayTarget(imageView: ImageView, model: Photo?) {
    val circularProgressDrawable = getProgressbar(imageView.context)
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

/**
 * PhotoPicker adapter 아이템
 */

@BindingAdapter("thumbPhoto")
fun bindThumbPhoto(imageView: ImageView, model: Photo?) {
    val circularProgressDrawable = getProgressbar(imageView.context)
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(Uri.parse(model?.uri))
        .sizeMultiplier(0.7f) //optimize를 위해 resolution 0.7정도로 설정.
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

/**
 * 오버레이 뷰 안, svg resource 어뎁터 아이템.
 */

@BindingAdapter("svgResource")
fun bindSvgResource(imageView: ImageView, path:String?) {

    val circularProgressDrawable = getProgressbar(imageView.context)
    circularProgressDrawable.start()

    //svg 리소스 경로를 통해 PictureDrawable로 변환.
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