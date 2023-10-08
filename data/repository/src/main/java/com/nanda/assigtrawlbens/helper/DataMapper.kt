package com.nanda.assigtrawlbens.helper

interface DataMapper<RemoteDataModel, DomainModel> {
    fun mapDataModel(dataModel: RemoteDataModel?): DomainModel
}