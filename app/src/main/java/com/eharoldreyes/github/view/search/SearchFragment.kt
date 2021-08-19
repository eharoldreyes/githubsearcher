package com.eharoldreyes.github.view.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.eharoldreyes.github.R
import com.eharoldreyes.github.data.model.Repository
import com.eharoldreyes.github.databinding.FragmentSearchBinding
import com.eharoldreyes.github.util.viewBindingLifecycle
import dagger.hilt.android.AndroidEntryPoint
import android.net.Uri

import android.content.Intent




@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), OnItemClickListener {

    private val viewModel by viewModels<SearchViewModel>()
    private var binding by viewBindingLifecycle<FragmentSearchBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding = FragmentSearchBinding.bind(view)

        val adapter = GithubRepoItemAdapter(this)

        with(binding) {
            githubRepositoryListView.adapter = adapter
            githubRepositorySwipeRefreshLayout.setOnRefreshListener {
                viewModel.refreshList()
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

            searchRepositories(INIT_SEARCH)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))

        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchRepositories(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        }

        searchView.setOnQueryTextListener(queryTextListener)

        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        const val INIT_SEARCH = "Github"
    }

    override fun onItemClicked(item: Repository) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(item.htmlUrl)
            )
        )
    }

}