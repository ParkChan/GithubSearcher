package com.example.githubsearcher.tapmenu.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.architecturestudy.base.BaseFragment
import com.example.githubsearcher.R
import com.example.githubsearcher.`interface`.ListScrollEvent
import com.example.githubsearcher.databinding.FragmentHomeBinding
import com.example.githubsearcher.tapmenu.home.viewmodel.GithubViewModel
import com.jsandroid.paging.model.GithubModel
import com.jsandroid.paging.ui.ReposAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {

    private val reposAdapter = ReposAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBinding()
        initListener()
        setRecyclerViewScrollListener(binding.rvList, object : ListScrollEvent {

            override fun onScrolled(
                totalItemCount: Int,
                visibleItemCount: Int,
                lastVisibleItem: Int
            ) {
                binding.githubViewModel?.listScrolled(
                    totalItemCount,
                    visibleItemCount,
                    lastVisibleItem
                )
            }
        })
        initAdapter()
    }

    private fun initBinding() {
        binding.githubViewModel = ViewModelProviders.of(this)
            .get(GithubViewModel::class.java)
    }

    fun initListener() {
        binding.etSearchKeyWord.setOnEditorActionListener { _, actionId, event ->
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

    fun updateList() {
        binding.etSearchKeyWord.text?.let {
            if (it.isNotEmpty()) {
                binding.githubViewModel?.search(it.toString())
                showToast("검색을 시작합니다.")
            }
        }
    }

    private fun initAdapter() {

        binding.rvList.adapter = reposAdapter

        binding.githubViewModel?.run{
            repos.observe(viewLifecycleOwner, Observer<List<GithubModel>> {
                reposAdapter.submitList(it)
            })

            networkErrors.observe(viewLifecycleOwner, Observer<String> {

            })
        }
    }
}