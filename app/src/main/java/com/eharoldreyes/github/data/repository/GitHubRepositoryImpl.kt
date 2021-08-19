package com.eharoldreyes.github.data.repository

import com.eharoldreyes.github.data.GithubService
import com.eharoldreyes.github.data.dto.transform
import com.eharoldreyes.github.data.model.Repository
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val service: GithubService
) : GithubRepository {
    override suspend fun searchRepositories(queryString: String): List<Repository> {
        return service.searchRepositories(queryString).items.map {
            it.transform()
        }
    }
}