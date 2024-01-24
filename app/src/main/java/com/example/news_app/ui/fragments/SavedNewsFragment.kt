package com.example.news_app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_app.R
import com.example.news_app.adapters.NewsAdapter
import com.example.news_app.databinding.FragmentSavedNewsBinding
import com.example.news_app.ui.NewsActivity
import com.example.news_app.ui.NewsViewModel

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    lateinit var newsAdapter: NewsAdapter
    lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentSavedNewsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedNewsBinding.inflate(layoutInflater)
        viewModel = (activity as NewsActivity).viewModel

        setupRecyclerView()

//        newsAdapter.setOnItemClickListener {
//            val bundle = Bundle().apply {
//                putSerializable("article", it)
//            }
//            findNavController().navigate(
//                R.id.action_savedNewsFragment_to_articleFragment,
//                bundle
//            )
//        }
    }

    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter(viewModel)
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}