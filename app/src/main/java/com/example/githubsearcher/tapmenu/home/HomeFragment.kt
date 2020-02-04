package com.example.githubsearcher.tapmenu.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProviders
import com.architecturestudy.base.BaseFragment
import com.example.githubsearcher.R
import com.example.githubsearcher.`interface`.ListScrollEvent
import com.example.githubsearcher.databinding.FragmentHomeBinding
import com.example.githubsearcher.tapmenu.home.viewmodel.GithubViewModel
import com.jsandroid.paging.ui.GithubAdapter
import com.orhanobut.logger.Logger

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {

    private val reposAdapter = GithubAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("HomeFragment lifecycle onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.d("HomeFragment lifecycle onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.d("HomeFragment lifecycle onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Logger.d("HomeFragment lifecycle onActivityCreated")
        super.onActivityCreated(savedInstanceState)
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

    override fun onResume() {
        super.onResume()
        Logger.d("HomeFragment lifecycle onResume")
    }

    override fun onPause() {
        super.onPause()
        Logger.d("HomeFragment lifecycle onPause")
    }

    override fun onStop() {
        super.onStop()
        Logger.d("HomeFragment lifecycle onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.d("HomeFragment lifecycle onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("HomeFragment lifecycle onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Logger.d("HomeFragment lifecycle onDetach")
    }

    private fun initViewModel() {
        binding.githubViewModel = ViewModelProviders.of(this)
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