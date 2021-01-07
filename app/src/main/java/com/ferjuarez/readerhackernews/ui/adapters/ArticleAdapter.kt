package com.ferjuarez.readerhackernews.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ferjuarez.readerhackernews.data.models.Article
import com.ferjuarez.readerhackernews.databinding.ArticleRowBinding

class ArticleAdapter(private val listener: ArticleItemListener) : RecyclerView.Adapter<ArticleViewHolder>() {

    interface ArticleItemListener {
        fun onArticleClicked(url: String?)
    }

    private val items = ArrayList<Article>()

    fun setItems(items: ArrayList<Article>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding: ArticleRowBinding = ArticleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) = holder.bind(items[position])
}


