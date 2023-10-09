package com.nanda.assigtrawlbens.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.nanda.assigtrawlbens.remote.model.entity.ArticleItemEntity

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(articles: List<ArticleItemEntity>)
}