package com.picker.overlay.data.repository

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import com.picker.overlay.R
import com.picker.overlay.di.AppModule
import com.picker.overlay.domain.model.Album
import com.picker.overlay.domain.model.Photo
import com.picker.overlay.domain.model.PhotoAlbum
import com.picker.overlay.domain.repository.MediaRepository
import com.picker.overlay.util.OverlayResult
import com.picker.overlay.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.Exception
import javax.inject.Inject


class MediaRepositoryImpl @Inject constructor(
    @ApplicationContext private val context:Context,
    @AppModule.IODispatcher private val defaultDispatcher: CoroutineDispatcher
): MediaRepository {

    override suspend fun overlayImages(photo: Photo, resource: Bitmap) = withContext(defaultDispatcher) {

        var background:Bitmap? = null
        var result:Bitmap? = null
        try {
            background = ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(context.contentResolver, Uri.parse(photo.uri))
            ) { decoder: ImageDecoder, _: ImageDecoder.ImageInfo?, _: ImageDecoder.Source? ->
                decoder.isMutableRequired = true
                decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
            }

            result = Bitmap.createBitmap(background.width, background.height, resource.config)
            val canvas = Canvas(result)
            val widthBack: Int = background.width
            val widthFront: Int = resource.width
            val move = ((widthBack - widthFront) / 2).toFloat()
            canvas.drawBitmap(background, 0f, 0f, null)
            canvas.drawBitmap(resource, move, move, null)

            background.recycle()
            resource.recycle()
        }
        catch (e:Exception) {
            e.printStackTrace()
        }
        finally {
            background?.recycle()
            resource.recycle()
            result?.recycle()
        }

        return@withContext OverlayResult.Failure("")

    }

    override suspend fun getOverlayResources(assetPath:String) = withContext(defaultDispatcher) {

        return@withContext context.assets.list(assetPath)?.let {
            Resource.Success(it.toList().map {fileName -> assetPath + File.separator + fileName })
        } ?: run {
            Resource.Failure(context.getString(R.string.err_failed_to_load_overlay_resources))
        }
    }

    override suspend fun getAlbumList() = withContext(defaultDispatcher) {

        val albums = HashMap<String, Album>()
        val projection = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_MODIFIED,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.SIZE
        )
        val query = Bundle().apply {
            putStringArray(
                ContentResolver.QUERY_ARG_SORT_COLUMNS,
                arrayOf(MediaStore.Images.ImageColumns.DATE_TAKEN)
            )
            putInt(
                ContentResolver.QUERY_ARG_SORT_DIRECTION,
                ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
            )
            // jpg, png만 로드
            val selection =
                "${MediaStore.Files.FileColumns.MIME_TYPE } = ? OR ${MediaStore.Files.FileColumns.MIME_TYPE } = ? OR ${MediaStore.Files.FileColumns.MIME_TYPE } = ?"
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
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME))
                    val date = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_MODIFIED))
                    val bucket = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME))
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE)) ?: continue

                    val albumItem = albums.getOrDefault(bucket, PhotoAlbum(bucket, mutableListOf()))
                    albumItem.list.add(
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id).toString()
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