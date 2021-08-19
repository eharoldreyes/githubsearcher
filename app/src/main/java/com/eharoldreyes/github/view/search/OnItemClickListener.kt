package com.eharoldreyes.github.view.search

import com.eharoldreyes.github.data.model.Repository

interface OnItemClickListener {
    fun onItemClicked(item: Repository)
}