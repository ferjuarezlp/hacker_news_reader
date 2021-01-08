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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ferjuarez.readerhackernews.R
import com.ferjuarez.readerhackernews.data.models.Article
import com.ferjuarez.readerhackernews.databinding.FragmentHomeBinding
import com.ferjuarez.readerhackernews.networking.Resource
import com.ferjuarez.readerhackernews.ui.adapters.ArticleAdapter
import com.ferjuarez.readerhackernews.ui.viewmodels.HomeViewModel
import com.ferjuarez.readerhackernews.utils.SwipeToDeleteCallback
import com.ferjuarez.readerhackernews.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


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
        initGetArticles()
    }

    private fun setupRecyclerView() {
        adapter = ArticleAdapter(this)
        binding.recyclerViewArticles.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewArticles.adapter = adapter
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.removeArticle(adapter.getItem(viewHolder.adapterPosition))
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewArticles)
    }

    private fun initGetArticles() {
        viewModel.getArticles().observe(viewLifecycleOwner, Observer {
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
        hideRefreshing()
        binding.progressBar.visibility = View.GONE
        if (!resource.data.isNullOrEmpty()) {
            val ordered = ArrayList(resource.data)
            ordered.sortByDescending{ article -> article.created_at_i}
            adapter.setItems(ordered)
            binding.textViewEmptyList.visibility = View.GONE
            binding.recyclerViewArticles.visibility = View.VISIBLE
        } else {
            binding.textViewEmptyList.visibility = View.VISIBLE
            binding.recyclerViewArticles.visibility = View.GONE
        }
    }

    private fun showError(message: String?){
        hideRefreshing()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onArticleClicked(url: String?) {
        if (!url.isNullOrEmpty()){
            findNavController().navigate(
                    R.id.action_home_to_article,
                    bundleOf("url" to url)
            )
        } else {
            Toast.makeText(requireContext(), getString(R.string.message_url_empty), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRefresh() {
        binding.swipeRefreshLayout.isRefreshing = true
        initGetArticles()
    }

    private fun hideRefreshing(){
        if (binding.swipeRefreshLayout.isRefreshing){
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

}
