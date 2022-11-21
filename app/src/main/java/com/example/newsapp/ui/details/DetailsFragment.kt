package com.example.newsapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentDetailsBinding
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.ui.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: MainViewModel
    private val args : DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
       binding = DataBindingUtil.inflate(inflater,R.layout.fragment_details, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let {
                loadUrl(article.url)
            }
        }

        binding.fab.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                viewModel.insertNews(article)
            }
        }

    }
}