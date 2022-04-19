package com.jobplanet.company.domain.model

import android.os.Parcelable
import com.jobplanet.company.R
import kotlinx.parcelize.Parcelize

/**
 * Company cell type 모델
 */
@Parcelize
data class Company(
    override val cell_type: String,
    val ranking:Int,
    val interview_difficulty:Float,
    val name:String,
    val salary_avg:String,
    val web_site:String,
    val logo_path:String,
    val interview_question:String,
    val company_id:Int,
    val has_job_posting:String,
    val rate_total_avg:String,
    val industry_id:Int,
    val review_summary:String,
    val type:String,
    val industry_name:String,
    val simple_desc:String
):Items, Parcelable {

    companion object {
        const val layoutId = R.layout.item_company
    }

    override val layout_id: Int
        get() = layoutId
}