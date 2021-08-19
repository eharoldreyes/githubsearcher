package com.eharoldreyes.github.view.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eharoldreyes.github.data.model.Repository
import com.eharoldreyes.github.databinding.ItemRepositoryBinding

class GithubRepoItemAdapter(private val listener: OnItemClickListener) : ListAdapter<Repository, GithubRepoItemAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.run { holder.bind(this) }
    }

    inner class ViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION)
                        listener.onItemClicked(getItem(position))
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(repositoryItem: Repository) =
            with(binding) {
                with(repositoryItem) {
                    repoName.text = name
                    userName.text = owner.userName
                    forkText.text = "$forks Forks"
                    openIssuesText.text = "$openIssues Open Issues"
                    watchersText.text = "$watchers Watchers"
                    Glide.with(binding.root.context)
                        .load(owner.avatarUrl)
                        .override(120, 120)
                        .into(binding.userAvatar)
                }
            }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(
                oldItem: Repository,
                newItem: Repository
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: Repository,
                newItem: Repository
            ) = oldItem.id == newItem.id
        }
    }
}