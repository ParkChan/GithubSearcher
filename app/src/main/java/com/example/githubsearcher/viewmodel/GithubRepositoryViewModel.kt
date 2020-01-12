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

package com.example.githubsearcher.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.githubsearcher.repository.GithubRepository
import com.jsandroid.paging.model.GithubModel
import com.jsandroid.paging.model.RepoSearchResult


class GithubRepositoryViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val repository: GithubRepository = GithubRepository()

    private val queryLiveData = MutableLiveData<String>()
    private val repoResult: LiveData<RepoSearchResult> = Transformations.map(queryLiveData, {
        repository.search(it)
    })

    val repos: LiveData<List<GithubModel>> = Transformations.switchMap(repoResult,
        { it -> it.data })
    val networkErrors: LiveData<String> = Transformations.switchMap(repoResult,
        { it -> it.networkErrors })

    fun searchRepo(queryString: String) {
        //queryLiveData.postValue(queryString)
        repository.search(queryString)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = lastQueryValue()
            if (immutableQuery != null) {
                repository.requestMore(immutableQuery)
            }
        }
    }

    /**
     * Get the last query value.
     */
    fun lastQueryValue(): String? = queryLiveData.value

}