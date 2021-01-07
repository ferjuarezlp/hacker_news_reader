package com.ferjuarez.readerhackernews.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.ferjuarez.readerhackernews.repository.ArticlesRepository

class HomeViewModel @ViewModelInject constructor(repository: ArticlesRepository
) : ViewModel() {
    val articles = repository.getArticles()
}
