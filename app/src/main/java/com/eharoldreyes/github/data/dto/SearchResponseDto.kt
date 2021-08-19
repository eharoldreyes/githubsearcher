package com.eharoldreyes.github.data.dto

data class SearchResponseDto(
    val total_count: Int,
    val items: List<RepositoryDto>
)