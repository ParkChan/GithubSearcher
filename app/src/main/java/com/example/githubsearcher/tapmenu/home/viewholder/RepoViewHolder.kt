package com.jsandroid.paging.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.BR
import com.example.githubsearcher.databinding.ItemGithubBinding
import com.example.githubsearcher.tapmenu.home.model.GithubModel

class RepoViewHolder(private val binding: ItemGithubBinding, view: View) :
    RecyclerView.ViewHolder(view) {

    init {
//        view.setOnClickListener {
//            repo?.url?.let { url ->
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                view.context.startActivity(intent)
//            }
//        }
    }
    fun bind(repo: GithubModel) {
        binding.setVariable(BR.githubModel, repo)
    }
}