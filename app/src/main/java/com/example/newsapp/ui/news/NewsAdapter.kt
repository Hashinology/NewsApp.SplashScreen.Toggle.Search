package com.example.newsapp.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.Article

class NewsAdapter (): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.ivNews)
        val title = itemView.findViewById<TextView>(R.id.tvTitle)
        val desc = itemView.findViewById<TextView>(R.id.tvDescription)
        val source = itemView.findViewById<TextView>(R.id.tvSource)
        val publishedAt = itemView.findViewById<TextView>(R.id.tvPublishedAt)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
        return ViewHolder(view)
    }


    private var onItemClickListener: ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val news = differ.currentList[position]

        holder.title.text = news.title
        holder.desc.text = news.description
        holder.source.text = news.source?.name
        holder.publishedAt.text = news.publishedAt

        Glide.with(holder.image)
            .load(news.urlToImage)
            .into(holder.image)


        holder.itemView.setOnClickListener {
           onItemClickListener?.let {
               it(news)
           }
        }
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}