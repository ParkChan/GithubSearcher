package com.example.githubsearcher.tapmenu.home.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.tapmenu.home.model.GithubModel
import com.jsandroid.paging.ui.GithubAdapter

@BindingAdapter("listData")
fun setGithubListData(recyclerView: RecyclerView, items: List<GithubModel>?) {
    (recyclerView.adapter as? GithubAdapter)?.run {
        addGithubList(items)
    }
}