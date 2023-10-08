package com.nanda.assigtrawlbens.di

import com.nanda.assigtrawlbens.remote.util.RetrofitBuilder
import com.nanda.assigtrawlbens.remote.api.NewsApi
import org.koin.dsl.module

object Modules {
    private val networkModules = module {
        single {
            RetrofitBuilder.create(NewsApi::class.java)
        }
    }

    fun getAppComponents() = listOf(
        networkModules
    )
}