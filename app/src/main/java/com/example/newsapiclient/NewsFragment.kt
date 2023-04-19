package com.example.newsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapiclient.data.util.Resource
import com.example.newsapiclient.databinding.FragmentNewsBinding
import com.example.newsapiclient.presentation.adapter.NewsAdapter
import com.example.newsapiclient.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    private lateinit var binding: FragmentNewsBinding
    private var country = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        adapter = (activity as MainActivity).newsAdapter
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selectedArticle",it)
            }
            findNavController().navigate(R.id.action_newsFragment_to_infoFragment,bundle)
        }
        initRecyclerView()
        viewNewsList()
        setSearchView()
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItems >= sizeOfCurrentList
            val shouldPaginates = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            if (shouldPaginates){
                page++
                viewModel.getNewsHeadLines(country,page)
                isScrolling = false
            }
        }
    }

    private fun viewNewsList() {
        viewModel.getNewsHeadLines(country,page)
        viewModel.newsHeadlines.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let {
                        adapter.differ.submitList(it.articles.toList())
                        if (it.totalResults % 20 == 0){
                            pages = it.totalResults / 20
                        } else {
                            pages = it.totalResults / 20 + 1
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    it.message?.let {
                        Toast.makeText(activity,"An error occured: $it",Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }
    private fun initRecyclerView() {
        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = LinearLayoutManager(activity)
        binding.rvNews.addOnScrollListener(this@NewsFragment.onScrollListener)
    }
    private fun showProgressBar(){
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        isLoading = false
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun setSearchView(){
        binding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.searchNews("us",p0.toString(),page)
                viewSearchedNews()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                MainScope().launch {
                    delay(2000)
                    viewModel.searchNews("us",p0.toString(),page)
                    viewSearchedNews()
                }
                return false
            }

        })
        binding.svNews.setOnCloseListener {
            initRecyclerView()
            viewNewsList()
            false
        }
    }

    fun viewSearchedNews(){
        viewModel.searchedNews.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let {
                        adapter.differ.submitList(it.articles.toList())
                        if (it.totalResults % 20 == 0){
                            pages = it.totalResults / 20
                        } else {
                            pages = it.totalResults / 20 + 1
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    it.message?.let {
                        Toast.makeText(activity,"An error occured: $it",Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }
}