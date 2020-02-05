package com.example.githubsearcher.tapmenu.home.model

import androidx.lifecycle.LiveData

data class RepoSearchResult(
    val data: LiveData<List<GithubModel>>,
    val networkErrors: LiveData<String>
)