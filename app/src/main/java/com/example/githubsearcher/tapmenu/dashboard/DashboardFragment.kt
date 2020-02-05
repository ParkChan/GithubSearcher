package com.example.githubsearcher.tapmenu.dashboard

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.architecturestudy.base.BaseFragment
import com.example.githubsearcher.R
import com.example.githubsearcher.databinding.FragmentDashboardBinding
import com.example.githubsearcher.tapmenu.home.adapter.GithubAdapter

class DashboardFragment : BaseFragment<FragmentDashboardBinding>(
    R.layout.fragment_dashboard
) {

    private val reposAdapter = GithubAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()
        updateList()
    }

    private fun initViewModel() {
        binding.dashboardViewModel = ViewModelProviders.of(this)
            .get(DashboardViewModel::class.java)
    }

    private fun updateList() {
        binding.dashboardViewModel?.selectAll()
    }

    private fun initAdapter() {
        binding.rvList.adapter = reposAdapter
    }
}