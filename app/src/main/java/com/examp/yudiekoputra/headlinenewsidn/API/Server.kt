package com.examp.yudiekoputra.headlinenewsidn.API

object Server {
    const val URL_API = "https://newsapi.org/"
    val apiService: ApiService?
        get() = RetrofitApi.getClient(URL_API)?.create(
            ApiService::class.java
        )
}