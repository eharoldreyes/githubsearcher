package com.eharoldreyes.github.data.repository

import com.eharoldreyes.github.data.model.Repository

interface GithubRepository {
    suspend fun searchRepositories(queryString: String): List<Repository>
}