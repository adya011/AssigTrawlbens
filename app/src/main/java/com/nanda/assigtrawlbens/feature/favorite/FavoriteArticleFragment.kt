package com.nanda.assigtrawlbens.feature.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nanda.assigtrawlbens.R
import com.nanda.assigtrawlbens.databinding.FragmentFavoriteArticleBinding
import com.nanda.assigtrawlbens.databinding.LayoutToolbarFavoriteBinding


class FavoriteArticleFragment : Fragment() {

    private var _binding: FragmentFavoriteArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteArticleBinding.inflate(inflater, container, false)
        _binding?.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbarBinding = LayoutToolbarFavoriteBinding.bind(binding.root)

        with(toolbarBinding) {
            toolbar.apply {
                setNavigationIcon(R.drawable.ic_back)
                setNavigationOnClickListener {
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}