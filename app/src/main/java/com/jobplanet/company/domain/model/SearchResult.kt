package com.jobplanet.company.domain.model

/**
 * 검색 결과 모델
 */
data class SearchResult(
    val minimum_interviews:Int = 0,
    val total_page:Int = 0,
    val minimum_reviews:Int = 0,
    val total_count:Int = 0,
    val items:List<Items> = listOf(),
    val page:Int = 0,
    val page_size:Int = 0,
    val minimum_salaries:Int = 0
)

/**
 * 검색 결과의 Items element 인터페이스
 */
interface Items {
    val cell_type:String
    val layout_id:Int
}