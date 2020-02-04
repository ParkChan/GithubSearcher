package com.example.githubsearcher.tapmenu.home.bindingadapter

import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.database.AppDatabase
import com.example.githubsearcher.tapmenu.home.model.GithubModel
import com.jsandroid.paging.ui.GithubAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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

@BindingAdapter("itemOnClick")
fun itemOnClick(view: View, model: GithubModel?) {
    view.setOnClickListener {
        model?.let {
            Toast.makeText(view.context, "${model.name} add favorite!!", Toast.LENGTH_SHORT).show()
            CoroutineScope(Dispatchers.IO).launch {
                view.context?.let { AppDatabase.getInstance(it)?.GithubDao()?.insert(model) }
            }
        }
    }
}