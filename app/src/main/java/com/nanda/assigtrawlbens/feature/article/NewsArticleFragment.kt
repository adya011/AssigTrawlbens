package com.nanda.assigtrawlbens.feature.article

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nanda.assigtrawlbens.R
import com.nanda.assigtrawlbens.base.BaseFragment
import com.nanda.assigtrawlbens.databinding.FragmentNewsArticleBinding
import com.nanda.assigtrawlbens.databinding.LayoutToolbarArticleBinding
import com.nanda.assigtrawlbens.domain.model.ArticleItemUiState
import com.nanda.assigtrawlbens.remote.util.Constants.PAGE_SIZE
import com.nanda.assigtrawlbens.util.Constants.ARG_URL
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsArticleFragment : BaseFragment() {

    private val viewModel by viewModel<NewsArticleViewModel>()

    private var _binding: FragmentNewsArticleBinding? = null
    private val binding get() = _binding!!

    private var articleAdapter: NewsArticleAdapter? = null
    private var infiniteScrollLoading: Boolean = false
    private var disableInfiniteScroll: Boolean = false

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
        setupScrollListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupToolbar() {
        val toolbarBinding = LayoutToolbarArticleBinding.bind(binding.root)
        with(toolbarBinding) {
            ivFavorite.setOnClickListener {

            }
            ivClear.setOnClickListener {
                val q = etToolbarSearch.text.toString()
                if (q != "") {
                    viewModel.clearQuery()
                    resetInfiniteScroll()
                    viewModel.fetchNewsArticle()
                }
                etToolbarSearch.setText("")
            }
            etToolbarSearch.setOnKeyListener { view, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    resetInfiniteScroll()
                    val q = (view as EditText).text.toString()
                    viewModel.setQuery(q)
                    viewModel.fetchNewsArticle()
                    etToolbarSearch.setText(q)
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    private fun resetInfiniteScroll() {
        viewModel.resetCurrentPage()
        infiniteScrollLoading = false
        disableInfiniteScroll = false
    }

    private fun setupAdapter() = with(binding) {
        articleAdapter = NewsArticleAdapter {
            navigateToWebView(it)
        }
        rvArticle.adapter = articleAdapter
    }

    private fun setupScrollListener() = with(binding) {
        rvArticle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastPos =
                    (rvArticle.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (infiniteScrollLoading.not() &&
                    lastPos == articleAdapter!!.itemCount - 1 &&
                    disableInfiniteScroll.not() &&
                    (viewModel.newsArticleLiveData.value?.total ?: 0) >= PAGE_SIZE
                ) {
                    disableInfiniteScroll = true
                    viewModel.addCurrentPage()
                    setInfiniteScrollLoadingState(articleAdapter!!.currentList)
                    viewModel.fetchNewsArticle(page = viewModel.getCurrentPage())
                }
            }
        })
    }

    private fun setInfiniteScrollLoadingState(list: List<ArticleItemUiState>) {
        infiniteScrollLoading = true
        val itemLoading = ArticleItemUiState(isLoading = true)

        if (list.contains(itemLoading).not()) {
            articleAdapter?.submitList((list + itemLoading))
        }
    }

    private fun navigateToWebView(url: String) {
        findNavController().navigate(
            R.id.open_webview,
            bundleOf(
                ARG_URL to url
            )
        )
    }

    private fun setupObserver() {
        viewModel.displayState.observe(viewLifecycleOwner) { state ->
            binding.vfContent.displayedChild = state.displayChild
            binding.tvWarningTitle.text = state.title
            binding.tvWarningMessage.text = state.description
        }
        viewModel.newsArticleLiveData.observe(viewLifecycleOwner) { article ->
            articleAdapter?.submitList(article.articles)
        }
        viewModel.newsArticleLoadMoreLiveData.observe(viewLifecycleOwner) { articles ->
            binding.rvArticle.postDelayed(
                {
                    val currItems = viewModel.newsArticleLiveData.value?.articles.orEmpty().toMutableList()
                    currItems.addAll(articles)
                    articleAdapter?.submitList(currItems)
                    disableInfiniteScroll = false
                }, 700
            )
            infiniteScrollLoading = false
        }
    }
}