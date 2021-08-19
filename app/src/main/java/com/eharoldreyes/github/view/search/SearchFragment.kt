package com.eharoldreyes.github.view.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.eharoldreyes.github.R
import com.eharoldreyes.github.databinding.FragmentSearchBinding
import com.eharoldreyes.github.util.viewBindingLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel by viewModels<SearchViewModel>()
    private var binding by viewBindingLifecycle<FragmentSearchBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        val adapter = GithubRepoItemAdapter()

        with(binding) {
            githubRepositoryListView.adapter = adapter
            githubRepositorySwipeRefreshLayout.setOnRefreshListener {
                viewModel.searchRepositories("Moover") //TODO get search edit text value
            }
        }

        with(viewModel) {
            githubRepositories.observe(viewLifecycleOwner) {
                binding.githubRepositorySwipeRefreshLayout.isRefreshing = false
                adapter.submitList(it)
            }

            showErrorLiveData.observe(viewLifecycleOwner) {
                binding.githubRepositorySwipeRefreshLayout.isRefreshing = false
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }

            searchRepositories("Moover") //TODO get search edit text value
        }
    }
}