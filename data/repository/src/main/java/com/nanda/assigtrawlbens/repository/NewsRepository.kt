package com.nanda.assigtrawlbens.repository

import com.nanda.assigtrawlbens.remote.model.entity.ArticleEntity
import com.nanda.assigtrawlbens.repository.model.DataResult

interface NewsRepository {
    suspend fun getArticle(query: String, page: Int): DataResult<ArticleEntity>
}