package com.ferjuarez.readerhackernews.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferjuarez.readerhackernews.data.models.Article
import com.ferjuarez.readerhackernews.networking.Resource
import com.ferjuarez.readerhackernews.repository.ArticlesRepository
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(repository: ArticlesRepository) : ViewModel() {
    private var repo = repository

    fun getArticles(): LiveData<Resource<List<Article>>> {
        return repo.getArticles()
    }

    fun removeArticle(item: Article) {
        viewModelScope.launch {
            repo.deleteArticle(item)
        }
    }
}
