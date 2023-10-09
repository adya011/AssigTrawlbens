package com.nanda.assigtrawlbens.feature.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanda.assigtrawlbens.domain.model.ArticleItemUiState
import com.nanda.assigtrawlbens.domain.model.ArticleUiState
import com.nanda.assigtrawlbens.domain.usecase.NewsUseCase
import com.nanda.assigtrawlbens.model.DisplayStateArticle
import com.nanda.assigtrawlbens.util.Constants.CHILD_INDEX_LOADING
import com.nanda.assigtrawlbens.util.Constants.CHILD_INDEX_SUCCESS
import com.nanda.assigtrawlbens.util.Constants.CHILD_INDEX_WARNING
import com.nanda.assigtrawlbens.util.Constants.EMPTY_DATA
import com.nanda.assigtrawlbens.util.Constants.PAGE_ERROR
import com.nanda.domain.usecase.resource.DataState
import kotlinx.coroutines.launch

class NewsArticleViewModel(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    private val _newsArticleLiveData by lazy { MutableLiveData<ArticleUiState>() }
    val newsArticleLiveData: LiveData<ArticleUiState> get() = _newsArticleLiveData

    private val _newsArticleLoadMoreLiveData by lazy { MutableLiveData<List<ArticleItemUiState>>() }
    val newsArticleLoadMoreLiveData: LiveData<List<ArticleItemUiState>> get() = _newsArticleLoadMoreLiveData

    private val _displayState: MutableLiveData<DisplayStateArticle> = MutableLiveData()
    val displayState get() = _displayState as LiveData<DisplayStateArticle>

    private var currentPage: Int = 1

    fun fetchNewsArticle(query: String = "", page: Int = 1) {
        viewModelScope.launch {
            newsUseCase.getArticle(query, page).collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        if (currentPage == 1) {
                            _displayState.value = DisplayStateArticle(CHILD_INDEX_LOADING)
                        }
                    }

                    is DataState.Success -> {
                        if (result.data.articles.isEmpty().not()) {
                            _displayState.value = DisplayStateArticle(CHILD_INDEX_SUCCESS)
                        } else {
                            _displayState.value =
                                DisplayStateArticle(CHILD_INDEX_WARNING, EMPTY_DATA)
                        }

                        if (currentPage == 1) {
                            _newsArticleLiveData.value = result.data
                        } else {
                            _newsArticleLoadMoreLiveData.value = result.data.articles
                        }
                    }

                    is DataState.Failure -> {
                        _displayState.value =
                            DisplayStateArticle(
                                CHILD_INDEX_WARNING,
                                PAGE_ERROR,
                                result.errorMessage
                            )
                    }
                }
            }
        }
    }
}