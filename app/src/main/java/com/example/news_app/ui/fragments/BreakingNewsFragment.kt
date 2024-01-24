package com.example.news_app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_app.R
import com.example.news_app.adapters.NewsAdapter

import com.example.news_app.databinding.FragmentBreakingNewsBinding
import com.example.news_app.db.ArticleDatabase
import com.example.news_app.repository.NewsRepository


import com.example.news_app.ui.NewsViewModel
import com.example.news_app.ui.NewsViewModelProviderFactory

import com.example.news_app.utils.Resource

class BreakingNewsFragment : Fragment() {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var binding: FragmentBreakingNewsBinding
    var TAG = "BreakingNewsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = (activity as NewsActivity).viewModel

        val newsRepository= NewsRepository(ArticleDatabase.createDatabase(requireContext()))
        val viewModelProviderFactory= NewsViewModelProviderFactory(newsRepository)
        viewModel= ViewModelProvider(this,viewModelProviderFactory)[NewsViewModel::class.java]
//        viewModel = (activity as NewsActivity).viewModel

        setupRecyclerView()


        viewModel.articleClicked.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "Item Clicked", Toast.LENGTH_SHORT)
            val action = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(it)
            findNavController().navigate(action)
        }

//        newsAdapter.setOnItemClickListener {
//            Toast.makeText(requireContext(), "Item Clicked", Toast.LENGTH_SHORT)
////            val bundle = Bundle().apply {
////                putSerializable("article", it)
////            }
////            findNavController().navigate(
////                R.id.action_breakingNewsFragment_to_articleFragment,
////                bundle
////            )
//            val action = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(it)
//            findNavController().navigate(action)
//        }

        viewModel.breakingNews.observe(
            viewLifecycleOwner,
            Observer {
                response ->
                when (response){
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Log.e(TAG, "An error occurred: $message")
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        )
    }

    private fun hideProgressBar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter(viewModel)
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}