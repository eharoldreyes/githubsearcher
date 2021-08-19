package com.eharoldreyes.github.data.dto

import com.eharoldreyes.github.data.model.Repository

fun RepositoryDto.transform() = Repository(
    id = id,
    name = name,
    language = language,
    forks = forks,
    open_issues = open_issues,
    watchers = watchers
)