package com.example.githubsearcher.tapmenu.common

import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.database.GithubDatabase
import com.example.githubsearcher.tapmenu.dashboard.FavoriteAdapter
import com.example.githubsearcher.tapmenu.home.adapter.GithubAdapter
import com.example.githubsearcher.tapmenu.home.model.GithubModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@BindingAdapter("listData")
fun setGithubListData(recyclerView: RecyclerView, items: List<GithubModel>?) {
    if (recyclerView.adapter is GithubAdapter) {
        (recyclerView.adapter as GithubAdapter).addList(items)
    } else if (recyclerView.adapter is FavoriteAdapter) {
        (recyclerView.adapter as FavoriteAdapter).addList(items)
    }
}

@BindingAdapter("visibility")
fun setVisibility(view: View, text: String?) {
    if (text.isNullOrEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("addFavorite")
fun addFavorite(view: View, model: GithubModel?) {
    view.setOnClickListener {
        model?.let {
            Toast.makeText(view.context, "${model.name} add favorite!!", Toast.LENGTH_SHORT).show()
            CoroutineScope(Dispatchers.IO).launch {
                view.context?.let { GithubDatabase.getInstance(it)?.githubDao()?.insert(model) }
            }
        }
    }
}

@BindingAdapter("deleteFavorite")
fun deleteFavorite(view: View, model: GithubModel?) {
    view.setOnClickListener {
        model?.let {
            Toast.makeText(view.context, "${model.name} delete favorite!!", Toast.LENGTH_SHORT)
                .show()
            CoroutineScope(Dispatchers.IO).launch {
                GithubDatabase.getInstance(view.context).githubDao().delete(model)
            }
        }
    }
}
