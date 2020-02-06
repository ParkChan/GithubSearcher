package com.example.githubsearcher.tapmenu.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubsearcher.database.GithubDatabase
import com.example.githubsearcher.tapmenu.home.model.GithubModel
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val githubDatabase: GithubDatabase = GithubDatabase.getInstance(application)

    private val _githubListLiveData = MutableLiveData<List<GithubModel>>()
    val githubListLiveData: LiveData<List<GithubModel>> = _githubListLiveData

    fun selectAll() {
        CoroutineScope(Dispatchers.IO).launch {
            githubDatabase.githubDao().selectAll()
                .subscribe({ githubModels ->
                    _githubListLiveData.postValue(githubModels)
                }, { throwable ->
                    Logger.d(throwable.message)
                })
        }
    }
}