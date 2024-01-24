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
import com.example.news_app.databinding.ItemArticlePreviewBinding
import com.example.news_app.models.Article
import com.example.news_app.ui.NewsViewModel


// Here we use difutils
class NewsAdapter(
    val viewModel: NewsViewModel
):RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    inner class ArticleViewHolder(private val binding: ItemArticlePreviewBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.tvTitle
        val source = binding.tvSource
        val description = binding.tvDescription
        val publishedAt = binding.tvPublishedAt
        val imageView = binding.ivArticleImage
        val layout = binding.articleItemLayout
        fun bind(article:Article){
            Glide.with(imageView)
                .load(article.urlToImage)
                .into(imageView)
            title.text = article.title
            source.text = article.source.name
            description.text = article.description
            publishedAt.text = article.publishedAt
            layout.setOnClickListener {
                viewModel.selectArticle(article)
            }
        }
    }


    private val differCallback = object  : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    // This async list differ runs in the background thread and compares current listview with updated one and changes the updated items only
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = ItemArticlePreviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)

//        holder.itemView.apply {
//            Glide.with(this).load(article.urlToImage).into(findViewById(R.id.ivArticleImage))
//            findViewById<TextView>(R.id.tvSource).text = article.source.name
//            findViewById<TextView>(R.id.tvTitle).text = article.title
//            findViewById<TextView>(R.id.tvDescription).text = article.description
//            findViewById<TextView>(R.id.tvPublishedAt).text = article.publishedAt
//            setOnItemClickListener {
//                onItemClickListener?.let{
//                    it(article)
//                }
//            }
//        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

//    private var onItemClickListener: ((Article) -> Unit)? = null
//
//    fun setOnItemClickListener(listener: (Article) -> Unit){
//        onItemClickListener = listener
//    }

}