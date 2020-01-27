package com.example.githubsearcher

import android.os.Bundle
import androidx.navigation.findNavController
import com.architecturestudy.base.BaseActivity
import com.example.githubsearcher.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tabNameList = arrayListOf("홈", "대쉬보드", "알림")
        val tabLayout = binding.tab

        tabNameList.forEach { name ->
            tabLayout.addTab(tabLayout.newTab().setText(name))
        }

        val navController = findNavController(R.id.nav_host_fragment)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                showToast(p0?.let { it.toString() })
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.position) {
                    0 -> navController.navigate(R.id.action_navigation_home)
                    1 -> navController.navigate(R.id.action_navigation_dashboard)
                    2 -> navController.navigate(R.id.action_navigation_notifications)
                }
            }
        })
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        bottomNavigationView.setupWithNavController(navController)
//        val menuView = bottomNavigationView.menu
//        Logger.d("menuView size ${menuView.size()}")
    }

}
