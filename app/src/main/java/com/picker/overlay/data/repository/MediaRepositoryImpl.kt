package com.picker.overlay.data.repository

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ImageDecoder
import android.graphics.drawable.PictureDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.graphics.drawable.toBitmap
import com.caverock.androidsvg.SVG
import com.picker.overlay.R
import com.picker.overlay.data.constants.OverlayOptions.compressFormat
import com.picker.overlay.data.constants.OverlayOptions.compressQuality
import com.picker.overlay.data.constants.OverlayOptions.getFileName
import com.picker.overlay.di.AppModule
import com.picker.overlay.domain.model.OverlayInfo
import com.picker.overlay.domain.model.Photo
import com.picker.overlay.domain.model.PhotoAlbum
import com.picker.overlay.domain.repository.MediaRepository
import com.picker.overlay.util.wrapper.OverlayResult
import com.picker.overlay.util.wrapper.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Media 관련 작업 Repo
 */
class MediaRepositoryImpl @Inject constructor(
    @ApplicationContext private val context:Context,
    @AppModule.IODispatcher private val defaultDispatcher: CoroutineDispatcher
): MediaRepository {

    override suspend fun overlayImages(info: OverlayInfo) = suspendCoroutine<OverlayResult>  { continuation ->
        thread {
            var background: Bitmap? = null
            var resource: Bitmap? = null
            var result: Bitmap? = null
            val fileName = getFileName()
            try {
                background = ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(context.contentResolver, Uri.parse(info.item.uri))
                ) { decoder: ImageDecoder, _: ImageDecoder.ImageInfo?, _: ImageDecoder.Source? ->
                    decoder.isMutableRequired = true
                    decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                }

                val bgRatioW = background.width.toFloat() / info.bgIntrinsicWidth.toFloat()
                val bgRatioH = background.height.toFloat() / info.bgIntrinsicHeight.toFloat()

                resource = PictureDrawable(SVG.getFromAsset(context.assets, info.resourceAssetPath).apply {
                    documentWidth = info.resourceMeasuredWidth.toFloat() * bgRatioW
                    documentHeight = info.resourceMeasuredHeight.toFloat() * bgRatioH
                }.renderToPicture()).toBitmap()

                result = Bitmap.createBitmap(background.width, background.height, background.config)
                val canvas = Canvas(result)
                val moveH = ((background.width - resource.width) / 2).toFloat()
                val moveV = ((background.height - resource.height) / 2).toFloat()
                canvas.drawBitmap(background, 0f, 0f, null)
                canvas.drawBitmap(resource, moveH, moveV, null)



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
                    val values = ContentValues().apply {
                        put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                        put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
                        put(MediaStore.Images.Media.RELATIVE_PATH, info.item.path)
                        put(MediaStore.Images.Media.IS_PENDING, 1)
                    }

                    val item = context.contentResolver.insert(uri, values)!!
                    context.contentResolver.openFileDescriptor(item, "rw").use { parcel ->
                        ByteArrayOutputStream().use { bao ->
                            result.compress(compressFormat, compressQuality, bao)
                            FileOutputStream(parcel!!.fileDescriptor).use { out ->
                                out.write(bao.toByteArray())
                            }
                        }
                    }

                    values.clear()
                    values.put(MediaStore.Images.Media.IS_PENDING, 0)
                    context.contentResolver.update(item, values, null, null)
                } else {
                    val imgFile = File(info.item.path, fileName)

                    FileOutputStream(imgFile).use { fos ->
                        result.compress(compressFormat, compressQuality, fos)
                        MediaScannerConnection.scanFile(
                            context,
                            arrayOf(imgFile.toString()),
                            null,
                            null
                        )
                    }
                }
                continuation.resume(OverlayResult.Success)

            } catch (e: Exception) {
                e.printStackTrace()
                continuation.resume(OverlayResult.Failure(context.getString(R.string.err_failed_overlay_image)))

            } finally {
                background?.recycle()
                resource?.recycle()
                result?.recycle()
            }
        }
    }

    override suspend fun getOverlayResources(assetPath:String) = suspendCoroutine<Resource<List<String>>> { continuation ->
        thread {
            context.assets.list(assetPath)?.let {
                continuation.resume(Resource.Success(it.toList().map { fileName -> assetPath + File.separator + fileName }))
            } ?: run {
                continuation.resume(Resource.Failure(context.getString(R.string.err_failed_to_load_overlay_resources)))
            }
        }
    }

    override suspend fun getAlbumList() = withContext(defaultDispatcher) {

        val pathColumn = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { MediaStore.Images.ImageColumns.RELATIVE_PATH } else { MediaStore.Images.ImageColumns.DATA }
        val albums = HashMap<String, PhotoAlbum>()
        val projection = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            pathColumn ,
            MediaStore.MediaColumns.SIZE
        )
        val query = Bundle().apply {
            putStringArray(
                ContentResolver.QUERY_ARG_SORT_COLUMNS,
                arrayOf(MediaStore.Images.ImageColumns.DATE_MODIFIED)
            )
            putInt(
                ContentResolver.QUERY_ARG_SORT_DIRECTION,
                ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
            )
            // jpg, png만 로드
            val selection =
                "${MediaStore.Files.FileColumns.MIME_TYPE } = ? " +
                        "OR ${MediaStore.Files.FileColumns.MIME_TYPE } = ? " +
                        "OR ${MediaStore.Files.FileColumns.MIME_TYPE } = ?"

            val selectionArgs = arrayOf("image/jpeg", "image/png", "image/jpg")

            putString(
                ContentResolver.QUERY_ARG_SQL_SELECTION,
                selection
            )
            putStringArray(
                ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS,
                selectionArgs
            )
        }
        val res = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            query,
            null
        )

        try {
            res?.let { cursor ->
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID))
                    val bucket = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME))
                    val path = cursor.getString(cursor.getColumnIndexOrThrow(pathColumn))
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE)) ?: continue

                    val albumItem = albums.getOrDefault(bucket, PhotoAlbum(bucket, mutableListOf()))
                    albumItem.list.add(
                        Photo(
                            bucket,
                            ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id).toString(),
                            path.substring(0, path.lastIndexOf("/") + 1)
                        )
                    )
                    albums[bucket] = albumItem
                }
            }
        }
        catch (e: IllegalArgumentException) {
            return@withContext Resource.Failure(context.getString(R.string.err_failed_load_albums))
        }
        finally {
            res?.close()
        }

        return@withContext Resource.Success(albums)
    }
}