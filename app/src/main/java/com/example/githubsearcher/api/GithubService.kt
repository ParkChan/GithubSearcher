package com.example.githubsearcher.api

import com.example.githubsearcher.tapmenu.home.model.ResGithubInfo
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val TAG = "GithubService"

fun searchRepos(
    service: GithubService,
    query: String,
    page: Int,
    itemsPerPage: Int,
    onSuccess: (githubInfo: ResGithubInfo) -> Unit,
    onError: (error: String) -> Unit
) {
    Logger.d(TAG, "query: $query, page: $page, itemsPerPage: $itemsPerPage")

    val apiQuery = query

    service.searchRepos(apiQuery, page, itemsPerPage).enqueue(
        object : Callback<ResGithubInfo> {
            override fun onFailure(call: Call<ResGithubInfo>?, t: Throwable) {
                onError(t.message ?: "unknown error")
            }

            override fun onResponse(
                call: Call<ResGithubInfo>?,
                response: Response<ResGithubInfo>
            ) {
                Logger.d(TAG, "got a response $response")
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }

        }
    )
}

interface GithubService {
    @GET("search/repositories?sort=stars")
    fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Call<ResGithubInfo>

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GithubService {
            val logger = HttpLoggingInterceptor()
            logger.level = Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService::class.java)
        }
    }
}