package com.eharoldreyes.github.data.model

data class Repository(
    val id: Int,
    val name: String,
    val forks: String,
    val openIssues: String,
    val watchers: String,
    val htmlUrl: String,
    val owner: Owner
)