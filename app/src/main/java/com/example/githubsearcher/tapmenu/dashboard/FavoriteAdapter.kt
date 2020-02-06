package com.example.githubsearcher.tapmenu.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.BR
import com.example.githubsearcher.R
import com.example.githubsearcher.databinding.ItemFavoriteBinding
import com.example.githubsearcher.tapmenu.home.model.GithubModel
import com.orhanobut.logger.Logger

class FavoriteAdapter : ListAdapter<GithubModel, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemFavoriteBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_favorite,
            parent,
            false
        )
        return RepoViewHolder(binding, binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        repoItem.also {
            Logger.d("position $position data >>> $repoItem")
        }?.let {
            (holder as RepoViewHolder).bind(it)
        }
    }

    private class RepoViewHolder(private val binding: ItemFavoriteBinding, view: View) :
        RecyclerView.ViewHolder(view) {

        fun bind(repo: GithubModel) {
            binding.setVariable(BR.githubModel, repo)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<GithubModel>() {
            override fun areItemsTheSame(oldItem: GithubModel, newItem: GithubModel): Boolean =
                oldItem.fullName == newItem.fullName

            override fun areContentsTheSame(oldItem: GithubModel, newItem: GithubModel): Boolean =
                oldItem == newItem
        }
    }

    fun addList(list: List<GithubModel>?) {
        list?.let {
            submitList(ArrayList(list))
        }
    }
}