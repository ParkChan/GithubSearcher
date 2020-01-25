package com.jsandroid.paging.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.BR
import com.example.githubsearcher.R
import com.example.githubsearcher.databinding.RepoViewItemBinding
import com.example.githubsearcher.tapmenu.home.model.GithubModel

class RepoViewHolder(private val binding: RepoViewItemBinding, view: View) :
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
        //showRepoData(repo)
    }

    private fun showRepoData(repo: GithubModel) {
//        this.repo = repo
//        name.text = repo.fullName
//
//        // if the description is missing, hide the TextView
//        var descriptionVisibility = View.GONE
//        if (repo.description != null) {
//            description.text = repo.description
//            descriptionVisibility = View.VISIBLE
//        }
//        description.visibility = descriptionVisibility
//
//        stars.text = repo.stars.toString()
//        forks.text = repo.forks.toString()
//
//        // if the language is missing, hide the label and the value
//        var languageVisibility = View.GONE
//        if (!repo.language.isNullOrEmpty()) {
//            val resources = this.itemView.context.resources
//            language.text = resources.getString(R.string.language, repo.language)
//            languageVisibility = View.VISIBLE
//        }
//        language.visibility = languageVisibility
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val binding: RepoViewItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.repo_view_item,
                parent,
                false
            )
            return RepoViewHolder(binding, binding.root)
        }
    }

}