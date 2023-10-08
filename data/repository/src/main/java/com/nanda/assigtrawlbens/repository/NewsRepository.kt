package com.nanda.assigtrawlbens.repository

import com.nanda.repository.model.ArticleEntity
import com.nanda.repository.model.DataResult

interface NewsRepository {
    suspend fun getArticle(source: String, query: String, page: Int): DataResult<ArticleEntity>
}