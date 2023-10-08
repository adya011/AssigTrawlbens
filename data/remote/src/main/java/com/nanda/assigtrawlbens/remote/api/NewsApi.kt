package com.nanda.assigtrawlbens.remote.api

import com.nanda.assigtrawlbens.remote.model.EverythingDto
import com.nanda.assigtrawlbens.remote.util.Constants.API_KEY
import com.nanda.assigtrawlbens.remote.util.Constants.PAGE_SIZE
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {
    @GET("everything")
    fun everything(
        @Query("sources") source: String,
        @Query("q") query: String = "",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = PAGE_SIZE,
        @Query("apiKey") apiKey: String = API_KEY
    ): Deferred<Response<EverythingDto>>
}