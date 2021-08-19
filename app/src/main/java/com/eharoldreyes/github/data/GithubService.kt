package com.eharoldreyes.github.data

import com.eharoldreyes.github.data.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("/search/repositories")
    suspend fun searchRepositories(@Query("q") queryString: String): SearchResponseDto

}