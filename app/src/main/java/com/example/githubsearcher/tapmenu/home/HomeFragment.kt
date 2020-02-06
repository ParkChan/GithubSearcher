package com.example.githubsearcher.tapmenu.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import com.example.githubsearcher.R
import com.example.githubsearcher.`interface`.ListScrollEvent
import com.example.githubsearcher.base.BaseFragment
import com.example.githubsearcher.databinding.FragmentHomeBinding
import com.example.githubsearcher.tapmenu.home.adapter.GithubAdapter
import com.example.githubsearcher.tapmenu.home.viewmodel.GithubViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {

    private val reposAdapter = GithubAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initListener()
        setRecyclerViewScrollListener(binding.rvList, object : ListScrollEvent {

            override fun onScrolled(
                visibleItemCount: Int,
                lastVisibleItem: Int,
                totalItemCount: Int
            ) {
                binding.githubViewModel?.listScrolled(
                    visibleItemCount,
                    lastVisibleItem,
                    totalItemCount
                )
            }
        })
        initAdapter()
    }

    private fun initViewModel() {
        binding.githubViewModel = ViewModelProvider(this)
            .get(GithubViewModel::class.java)
    }

    private fun initListener() {
        binding.etSearchKeyWord.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_GO) {
                updateList()
                true
            } else {
                false
            }
        }

        binding.etSearchKeyWord.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateList()
                true
            } else {
                false
            }
        }
    }

    private fun updateList() {
        binding.etSearchKeyWord.text?.let {
            if (it.isNotEmpty()) {
                binding.githubViewModel?.search(it.toString())
            }
        }
    }

    private fun initAdapter() {
        binding.rvList.adapter = reposAdapter
    }
}