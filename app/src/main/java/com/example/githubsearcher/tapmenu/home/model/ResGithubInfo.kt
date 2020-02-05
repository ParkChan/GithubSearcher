package com.example.githubsearcher.tapmenu.home.model

import com.google.gson.annotations.SerializedName

data class ResGithubInfo(
    @SerializedName("total_count") val totalCount: Int = 0,
    @SerializedName("items") val items: List<GithubModel> = emptyList()
)
