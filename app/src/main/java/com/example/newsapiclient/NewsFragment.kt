package com.example.newsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapiclient.data.util.Resource
import com.example.newsapiclient.databinding.FragmentNewsBinding
import com.example.newsapiclient.presentation.adapter.NewsAdapter
import com.example.newsapiclient.presentation.viewmodel.NewsViewModel

class NewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    private lateinit var binding: FragmentNewsBinding
    private var country = "us"
    private var page = 1
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
        initRecyclerView()
        viewNewsList()
    }

    private fun viewNewsList() {
        viewModel.getNewsHeadLines(country,page)
        viewModel.newsHeadlines.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let {
                        adapter.differ.submitList(it.articles.toList())
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
        adapter = NewsAdapter()
        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = LinearLayoutManager(activity)
    }
    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }
}