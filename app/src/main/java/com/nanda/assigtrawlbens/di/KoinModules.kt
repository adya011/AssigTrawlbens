package com.nanda.assigtrawlbens.di

import com.nanda.assigtrawlbens.domain.resource.AppDispatchers
import com.nanda.assigtrawlbens.domain.usecase.NewsUseCase
import com.nanda.assigtrawlbens.feature.article.NewsArticleViewModel
import com.nanda.assigtrawlbens.remote.util.RetrofitBuilder
import com.nanda.assigtrawlbens.remote.api.NewsApi
import com.nanda.assigtrawlbens.repository.NewsRepository
import com.nanda.assigtrawlbens.repository.NewsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModules {
    private val networkModules = module {
        single {
            RetrofitBuilder.create(NewsApi::class.java)
        }
    }

    private val persistenceModule = module {
        factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO, Dispatchers.Default) }
    }

    private val useCaseModules = module {
        single { NewsUseCase(get(), get()) }
    }

    private val viewModelModules = module {
        viewModel { NewsArticleViewModel(get()) }
    }

    private val repositoryModules = module {
        single<NewsRepository> { NewsRepositoryImpl(get()) }
    }

    fun getAppComponents() = listOf(
        networkModules,
        persistenceModule,
        useCaseModules,
        viewModelModules,
        repositoryModules
    )
}