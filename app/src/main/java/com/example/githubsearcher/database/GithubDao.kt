package com.example.githubsearcher.database

import androidx.room.Query
import io.reactivex.Maybe

interface GithubDao : BaseDao<GithubDataEntity> {

    @Query("SELECT * FROM githubData")
    fun selectAll(): Maybe<List<GithubDataEntity>>

}

