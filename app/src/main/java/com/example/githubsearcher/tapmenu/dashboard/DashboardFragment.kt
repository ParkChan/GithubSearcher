package com.example.githubsearcher.tapmenu.dashboard

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.githubsearcher.R
import com.example.githubsearcher.base.BaseFragment
import com.example.githubsearcher.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment<FragmentDashboardBinding>(
    R.layout.fragment_dashboard
) {
    private val favoriteAdapter = FavoriteAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()
        updateList()
    }

    private fun initViewModel() {
        binding.dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
    }

    private fun updateList() {
        binding.dashboardViewModel?.selectAll()
    }

    private fun initAdapter() {
        binding.rvList.adapter = favoriteAdapter
    }
}