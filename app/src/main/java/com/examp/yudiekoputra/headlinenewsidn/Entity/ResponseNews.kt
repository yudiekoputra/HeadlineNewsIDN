package com.examp.yudiekoputra.headlinenewsidn.Entity

import com.google.gson.annotations.SerializedName

class ResponseNews {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("totalResults")
    var totalResults: String? = null

    @SerializedName("articles")
    var newsList: List<News>? = null
}