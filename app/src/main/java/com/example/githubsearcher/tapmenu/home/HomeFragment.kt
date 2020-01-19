package com.example.githubsearcher.tapmenu.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProviders
import com.architecturestudy.base.BaseFragment
import com.example.githubsearcher.R
import com.example.githubsearcher.databinding.FragmentHomeBinding
import com.example.githubsearcher.viewmodel.GithubRepositoryViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBinding()
        initListener()
    }

    private fun initBinding() {
        binding.githubRepositoryViewModel = ViewModelProviders.of(this)
            .get(GithubRepositoryViewModel::class.java)
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
                binding.githubRepositoryViewModel?.searchRepo(it.toString())
                showToast("검색을 시작합니다.")
            }
        }
    }
}