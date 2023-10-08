package com.nanda.assigtrawlbens.domain.model


data class ArticleUiState(
    val total: Int = 0,
    val articles: List<ArticleItemUiState> = emptyList()
)