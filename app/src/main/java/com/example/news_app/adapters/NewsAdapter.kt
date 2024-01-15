package com.example.news_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news_app.R
import com.example.news_app.models.Article


// Here we use difutils
class NewsAdapter:RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    private val differCallback = object  : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    // This async listdiffer runs in the background thread and compares current listview with updated one and changes the updated items only
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]


        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(findViewById(R.id.ivArticleImage))
            findViewById<TextView>(R.id.tvSource).text = article.source.name
            findViewById<TextView>(R.id.tvTitle).text = article.title
            findViewById<TextView>(R.id.tvDescription).text = article.description
            findViewById<TextView>(R.id.tvPublishedAt).text = article.publishedAt
            setOnItemClickListner {
                onItemClickListner?.let{
                    it(article)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListner: ((Article) -> Unit)? = null

    fun setOnItemClickListner(listener: (Article) -> Unit){
        onItemClickListner = listener
    }

}