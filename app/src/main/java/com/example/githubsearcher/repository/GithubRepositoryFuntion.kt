package com.example.githubsearcher.repository

import com.example.githubsearcher.network.GithubApi
import com.example.githubsearcher.tapmenu.home.model.ResGithubInfo
import com.orhanobut.logger.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getGithubRepositoryList(
    api: GithubApi,
    query: String,
    page: Int,
    itemsPerPage: Int,
    onSuccess: (githubInfo: ResGithubInfo) -> Unit,
    onError: (error: String) -> Unit
) {
    Logger.d("query: $query, page: $page, itemsPerPage: $itemsPerPage")

    val strQuery = query

    api.searchRepos(strQuery, page, itemsPerPage).enqueue(
        object : Callback<ResGithubInfo> {
            override fun onFailure(call: Call<ResGithubInfo>?, t: Throwable) {
                onError(t.message ?: "unknown error")
            }

            override fun onResponse(
                call: Call<ResGithubInfo>?,
                response: Response<ResGithubInfo>
            ) {
                Logger.d("got a response $response")
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }

        }
    )
}