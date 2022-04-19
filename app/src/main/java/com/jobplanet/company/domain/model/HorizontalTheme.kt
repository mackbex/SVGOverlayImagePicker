package com.jobplanet.company.domain.model

import android.os.Parcelable
import com.jobplanet.company.R
import kotlinx.parcelize.Parcelize

/**
 * Horizontal theme cell type 모델
 */
@Parcelize
data class HorizontalTheme(
    override val cell_type: String,
    val count:Int,
    val themes:List<Theme>,
): Items, Parcelable {
    companion object {
        const val layoutId = R.layout.item_horizontal_theme
    }
    override val layout_id: Int
        get() = layoutId
}

@Parcelize
data class Theme(
    val color:String,
    val cover_image:String,
    val id:Int,
    val title:String
):Parcelable