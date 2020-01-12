/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.githubsearcher.repository

import androidx.lifecycle.MutableLiveData
import com.example.githubsearcher.api.GithubService
import com.example.githubsearcher.api.searchRepos
import com.jsandroid.paging.model.GithubModel
import com.jsandroid.paging.model.RepoSearchResult
import com.orhanobut.logger.Logger

class GithubRepository {

    private val service: GithubService = GithubService.create()
    private val responseData = MutableLiveData<List<GithubModel>>()
    private val networkErrors = MutableLiveData<String>()

    private var lastRequestedPage = 1
    private var isRequestInProgress = false


    fun search(query: String): RepoSearchResult {
        Logger.d("GithubRepository", "New query: $query")
        lastRequestedPage = 1
        requestData(query)

        return RepoSearchResult(responseData, networkErrors)
    }

    fun requestMore(query: String) {
        requestData(query)
    }

    private fun requestData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchRepos(service, query, lastRequestedPage, NETWORK_PAGE_SIZE, { repos ->

            responseData.value = repos
            lastRequestedPage++
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