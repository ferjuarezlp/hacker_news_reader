package com.ferjuarez.readerhackernews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ferjuarez.readerhackernews.R
import com.ferjuarez.readerhackernews.data.models.Article
import com.ferjuarez.readerhackernews.databinding.FragmentHomeBinding
import com.ferjuarez.readerhackernews.networking.Resource
import com.ferjuarez.readerhackernews.ui.adapters.ArticleAdapter
import com.ferjuarez.readerhackernews.ui.viewmodels.HomeViewModel
import com.ferjuarez.readerhackernews.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment(), ArticleAdapter.ArticleItemListener, SwipeRefreshLayout.OnRefreshListener {

    private var binding: FragmentHomeBinding by autoCleared()
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = ArticleAdapter(this)
        binding.recyclerViewArticles.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewArticles.adapter = adapter
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun setupObservers() {
        viewModel.articles.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> showNews(it)
                Resource.Status.ERROR -> showError(it.message)
                Resource.Status.LOADING -> showLoading()
            }
        })
    }

    private fun showLoading(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showNews(resource: Resource<List<Article>>){
        binding.progressBar.visibility = View.GONE
        if (!resource.data.isNullOrEmpty()) {
            val ordered = ArrayList(resource.data)
            ordered.sortByDescending{ article -> article.created_at_i}
            adapter.setItems(ordered)
        } else {
            // TODO show empty list
        }
    }

    private fun showError(message: String?){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onArticleClicked(url: String?) {
        findNavController().navigate(
            R.id.action_home_to_article,
            bundleOf("url" to url)
        )
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = true
    }
}
