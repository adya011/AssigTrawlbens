package com.nanda.assigtrawlbens.feature.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nanda.assigtrawlbens.base.BaseFragment
import com.nanda.assigtrawlbens.databinding.FragmentNewsArticleBinding
import com.nanda.assigtrawlbens.databinding.LayoutToolbarArticleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsArticleFragment : BaseFragment() {

    private val viewModel by viewModel<NewsArticleViewModel>()

    private var _binding: FragmentNewsArticleBinding? = null
    private val binding get() = _binding!!

    private var articleAdapter: NewsArticleAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsArticleBinding.inflate(inflater, container, false)
        _binding?.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchNewsArticle()
        setupToolbar()
        setupObserver()
        setupAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupToolbar() {
        val toolbarBinding = LayoutToolbarArticleBinding.bind(binding.root)
        with(toolbarBinding) {
            ivLup.setOnClickListener {

            }
            ivClear.setOnClickListener {

            }
            ivFavorite.setOnClickListener {

            }
        }
    }

    private fun setupAdapter() = with(binding) {
        articleAdapter = NewsArticleAdapter {
            //TODO: to webview
        }
        rvArticle.adapter = articleAdapter
    }

    private fun setupObserver() {
        viewModel.newsArticleLiveData.observe(viewLifecycleOwner) { article ->
            articleAdapter?.submitList(article.articles)
        }
    }
}