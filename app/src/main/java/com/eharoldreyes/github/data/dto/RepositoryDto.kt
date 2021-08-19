package com.eharoldreyes.github.data.dto

class RepositoryDto (
    val id: Int,
    val name: String,
    val forks: String,
    val open_issues: String,
    val watchers: String,
    val html_url: String,
    val owner: OwnerDto
)