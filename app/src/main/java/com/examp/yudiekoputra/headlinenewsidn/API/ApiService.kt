package com.examp.yudiekoputra.headlinenewsidn.API

import com.examp.yudiekoputra.headlinenewsidn.Entity.ResponseNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/top-headlines")
    fun getListNews(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ResponseNews?>?

    @GET("/v2/top-headlines")
    fun getListAllNews(
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ResponseNews?>?
}