package com.nanda.assigtrawlbens.domain.usecase

import com.nanda.assigtrawlbens.repository.NewsRepository
import com.nanda.assigtrawlbens.domain.resource.AppDispatchers
import com.nanda.domain.usecase.resource.DataState
import com.nanda.assigtrawlbens.domain.model.ArticleItemUiState
import com.nanda.assigtrawlbens.domain.model.ArticleUiState
import com.nanda.repository.model.ArticleEntity
import com.nanda.repository.model.DataResult
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsUseCase(
    private val newsRepository: NewsRepository,
    private val appDispatchers: AppDispatchers
) {
    fun getArticle(query: String, page: Int) = flow {
        emit(DataState.Loading(null))

        when (val response = newsRepository.getArticle(query, page)) {
            is DataResult.Success -> {
                val data = response.body
                emit(DataState.Success(data.mapNewsArticle()))
            }

            is DataResult.Error -> {
                emit(DataState.Failure(response.message))
            }
        }
    }.flowOn(appDispatchers.io)

    private fun ArticleEntity.mapNewsArticle(): ArticleUiState {
        return ArticleUiState(
            total = total,
            articles = articles.map {
                ArticleItemUiState(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    url = it.url,
                    urlToImage = it.urlToImage
                )
            }
        )
    }
}