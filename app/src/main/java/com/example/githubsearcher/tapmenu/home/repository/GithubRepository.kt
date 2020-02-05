package com.example.githubsearcher.tapmenu.home.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.githubsearcher.api.GithubService
import com.example.githubsearcher.api.searchRepos
import com.example.githubsearcher.tapmenu.home.model.GithubModel
import com.example.githubsearcher.tapmenu.home.model.RepoSearchResult

class GithubRepository {

    private val service: GithubService = GithubService.create()
    private val responseData = MutableLiveData<List<GithubModel>>()
    private val networkErrors = MutableLiveData<String>()

    private var lastRequestedPage = 1
    private var totalPage = 1
    private var isRequestInProgress = false

    private val items = mutableListOf<GithubModel>()

    fun search(query: String): RepoSearchResult {
        initPageInfo()
        requestData(query)

        return RepoSearchResult(responseData, networkErrors)
    }

    fun requestMore(query: String) {
        Log.d("CHAN >>>", "now Page >>>$lastRequestedPage total Page >>>$totalPage")
        if (lastRequestedPage > totalPage) return
        requestData(query)
    }

    private fun initPageInfo() {
        lastRequestedPage = 1
        totalPage = 1
        items.clear()
    }

    private fun requestData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchRepos(service, query, lastRequestedPage, NETWORK_PAGE_SIZE, { repos ->

            repos.totalCount.let {
                totalPage = if (repos.totalCount / NETWORK_PAGE_SIZE > 0) {
                    (repos.totalCount / NETWORK_PAGE_SIZE) + 1
                } else {
                    repos.totalCount / NETWORK_PAGE_SIZE
                }
            }

            repos.items.let {
                items.addAll(it)
                responseData.postValue(items)
                lastRequestedPage++
            }
            isRequestInProgress = false

        }, { error ->
            networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}