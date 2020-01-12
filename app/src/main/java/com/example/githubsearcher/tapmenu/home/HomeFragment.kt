package com.example.githubsearcher.tapmenu.home

import android.os.Bundle
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
        binding.etSearchKeyWord.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                updateList(textView.text.toString())
                true
            } else {
                false
            }
        }
    }

    fun updateList(query: String) {
        binding.githubRepositoryViewModel?.searchRepo(query)
        showToast("검색을 시작합니다.")
    }
}