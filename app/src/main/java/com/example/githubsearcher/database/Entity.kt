package com.example.githubsearcher.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "githubData")
data class GithubDataEntity(
    @PrimaryKey val id: Long,
    var name: String,
    var fullName: String,
    var description: String?,
    var url: String
)