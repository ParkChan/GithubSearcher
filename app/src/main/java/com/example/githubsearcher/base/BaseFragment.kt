package com.architecturestudy.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.`interface`.ListScrollEvent

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes
    private val resource: Int
) : Fragment() {

    protected lateinit var binding: B

    protected val ARGS_SCROLL_Y: String = "ARGS_SCROLL_Y"
    protected var scrollY: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            resource,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    protected fun setRecyclerViewScrollListener(
        recyclerView: RecyclerView,
        listScrollEvent: ListScrollEvent
    ) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                scrollY = dy

                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                listScrollEvent.run {
                    onScrolled(
                        visibleItemCount,
                        lastVisibleItem,
                        totalItemCount
                    )
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARGS_SCROLL_Y, scrollY)
    }

    fun showToast(msg: String?) = Toast.makeText(
        activity,
        msg,
        Toast.LENGTH_SHORT
    ).show()
}