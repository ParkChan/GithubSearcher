package com.example.githubsearcher.tapmenu.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.githubsearcher.tapmenu.home.model.GithubModel
import com.example.githubsearcher.tapmenu.home.model.RepoSearchResult
import com.example.githubsearcher.tapmenu.home.repository.GithubRepository


class GithubViewModel : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 20
    }

    private val repository: GithubRepository = GithubRepository()

    private val queryLiveData = MutableLiveData<String>()
    private val repoResult: LiveData<RepoSearchResult> = Transformations.map(queryLiveData) {
        repository.search(it)
    }

    val responseData: LiveData<List<GithubModel>> =
        Transformations.switchMap(repoResult) { it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(repoResult) { it.networkErrors }

    fun search(queryString: String) {
        queryLiveData.postValue(queryString)
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