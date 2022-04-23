package com.picker.overlay.data.constants

import android.graphics.Bitmap
import android.text.format.DateFormat
import java.util.*

// 이미지 오버레이 실행 시의 옵션 리스트
object OverlayOptions {
    val compressFormat = Bitmap.CompressFormat.JPEG
    const val compressQuality = 100
    fun getFileName():String {
        val calendar = GregorianCalendar()
        calendar.timeZone = TimeZone.getDefault()
        val dateTaken = calendar.timeInMillis
        return DateFormat.format("yyyyMMdd_kkmmss", dateTaken).toString() + dateTaken % 1000 + ".jpg"
    }
}