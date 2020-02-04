package com.example.githubsearcher.database

import androidx.room.Dao
import androidx.room.Query
import com.example.githubsearcher.tapmenu.home.model.GithubModel
import io.reactivex.Maybe

@Dao
interface GithubDao : BaseDao<GithubModel> {

    @Query("SELECT * FROM githubData")
    fun selectAll(): Maybe<List<GithubModel>>
}

