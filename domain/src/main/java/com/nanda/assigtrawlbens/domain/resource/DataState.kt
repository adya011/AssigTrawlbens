package com.nanda.assigtrawlbens.domain.resource

import com.nanda.assigtrawlbens.repository.model.DataType

sealed class DataState<T> {
    data class Loading<T>(val data: T? = null) : DataState<T>()
    data class Success<T>(val data: T, val dataType: DataType? = null) : DataState<T>()
    data class Failure<T>(val errorMessage: String) : DataState<T>()
}