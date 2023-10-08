package com.nanda.assigtrawlbens.repository

import com.nanda.repository.model.ArticleEntity
import com.nanda.repository.model.DataResult

interface NewsRepository {
    suspend fun getArticle(query: String, page: Int): DataResult<ArticleEntity>
}