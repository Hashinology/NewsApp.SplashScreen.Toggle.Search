package com.example.newsapp.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmretrofitcoroutines.ui.favorites.DeleteItemClick
import com.example.newsapp.R
import com.example.newsapp.model.Article

class FavoritesAdapter (private val deleteItemClick: DeleteItemClick): RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }


    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.favorites_list_item,parent,false)
        return FavoritesViewHolder(view)
    }
    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val news = differ.currentList[position]

        holder.tvTitleFv.text = news.title
        holder.tvSourceFv.text = news.source?.name!!
        Glide.with(holder.itemView.context).load(news.urlToImage).into(holder.ivNewsFv)

        holder.ibDelete.setOnClickListener{
            deleteItemClick.onClickDelete(holder.adapterPosition)
        }


        }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class FavoritesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivNewsFv = itemView.findViewById<ImageView>(R.id.ivNewsFv)
        val tvTitleFv = itemView.findViewById<TextView>(R.id.tvTitleFv)
        val tvSourceFv = itemView.findViewById<TextView>(R.id.tvSourceFv)
        val ibDelete = itemView.findViewById<ImageButton>(R.id.ibDelete)

    }
}