package com.ferjuarez.readerhackernews.ui.adapters

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ferjuarez.readerhackernews.data.models.Article
import com.ferjuarez.readerhackernews.databinding.ArticleRowBinding
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class ArticleViewHolder(private val itemBinding: ArticleRowBinding, private val listener: ArticleAdapter.ArticleItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var article: Article

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(article: Article) {
        this.article = article
        itemBinding.textViewTitle.text = article.title ?: article.story_title
        val time = PrettyTime().format(Date(article.created_at_i*1000))
        itemBinding.textViewCreation.text = """by ${article.author.capitalize()} - $time"""
    }

    override fun onClick(v: View?) {
        listener.onArticleClicked(article.url)
    }
}