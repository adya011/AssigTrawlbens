package com.nanda.assigtrawlbens.feature.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nanda.assigtrawlbens.databinding.FragmentNewsArticleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsArticleFragment : Fragment() {

    private val viewModel by viewModel<NewsArticleViewModel>()

    private var _binding: FragmentNewsArticleBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}