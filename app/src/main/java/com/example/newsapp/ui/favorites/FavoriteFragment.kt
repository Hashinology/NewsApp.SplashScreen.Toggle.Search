package com.example.newsapp.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmretrofitcoroutines.ui.favorites.DeleteItemClick
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentFavoriteBinding
import com.example.newsapp.model.Article
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.ui.MainViewModel
import com.example.newsapp.ui.news.ClickedInterFace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment() , DeleteItemClick, ClickedInterFace{

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var favoriteAdapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        viewModel.getNewsFromRoom.observe(viewLifecycleOwner, Observer {
            favoriteAdapter.differ.submitList(it)
        })


    }

    private fun setUpRecyclerView() {
        favoriteAdapter = FavoritesAdapter(this)
        binding.rvFavorites.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun onClickDelete(position: Int) {
        lifecycleScope.launch(Dispatchers.IO){
            viewModel.deleteArticle(favoriteAdapter.differ.currentList[position])
        }
    }



    override fun clickedItem(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article",article)
        }
        findNavController().navigate(R.id.action_favoriteFragment_to_detailsFragment,bundle)    }
}