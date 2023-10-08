package com.nanda.assigtrawlbens.repository

import com.nanda.assigtrawlbens.helper.dataSourceHandling
import com.nanda.assigtrawlbens.remote.api.NewsApi
import com.nanda.assigtrawlbens.repository.mapper.ArticleMapper
import com.nanda.repository.model.ArticleEntity
import com.nanda.repository.model.DataResult

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {

    override suspend fun getArticle(query: String, page: Int): DataResult<ArticleEntity> {
        return dataSourceHandling(
            networkCall = { api.everything(query, page) },
            mapper = ArticleMapper()
        )
    }
}