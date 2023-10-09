package com.nanda.assigtrawlbens.remote.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "latest_exchange")
data class ArticleItemEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String
)