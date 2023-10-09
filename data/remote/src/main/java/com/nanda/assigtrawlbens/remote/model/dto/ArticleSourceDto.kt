package com.nanda.assigtrawlbens.remote.model.dto

import com.squareup.moshi.Json

data class ArticleSourceDto(
    @Json(name = "id") val id: String?,
    @Json(name = "name") val name: String?
)