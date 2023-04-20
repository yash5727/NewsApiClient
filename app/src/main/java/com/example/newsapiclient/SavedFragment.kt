package com.example.newsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapiclient.databinding.FragmentSavedBinding
import com.example.newsapiclient.presentation.adapter.NewsAdapter
import com.example.newsapiclient.presentation.viewmodel.NewsViewModel

class SavedFragment : Fragment() {
    private lateinit var binding: FragmentSavedBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("selectedArticle",it)
            }
            findNavController().navigate(R.id.action_savedFragment_to_infoFragment,bundle)
        }
        initRecyclerView()
        viewModel.getSavedNews().observe(viewLifecycleOwner){
            newsAdapter.differ.submitList(it)
        }
    }
    private fun initRecyclerView() {
        binding.rvSaved.adapter = newsAdapter
        binding.rvSaved.layoutManager = LinearLayoutManager(activity)
    }
}