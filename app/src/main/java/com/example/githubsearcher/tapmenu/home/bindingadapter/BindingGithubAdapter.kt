package com.example.githubsearcher.tapmenu.home.bindingadapter

import android.view.View
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

@BindingAdapter("visibility")
fun setVisibility(view: View, text: String?) {
    if (text.isNullOrEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}