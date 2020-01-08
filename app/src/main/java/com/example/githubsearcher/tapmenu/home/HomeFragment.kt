package com.example.githubsearcher.tapmenu.home

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProviders
import com.architecturestudy.base.BaseFragment
import com.example.githubsearcher.R
import com.example.githubsearcher.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBinding()
        initListener()
    }

    private fun initBinding() {
        binding.homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    fun initListener() {
        binding.etSearchKeyWord.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                updateList()
                true
            } else {
                false
            }
        }
    }

    fun updateList() {
        showToast("검색을 시작합니다.")
    }
}