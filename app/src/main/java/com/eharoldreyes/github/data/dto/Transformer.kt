package com.eharoldreyes.github.data.dto

import com.eharoldreyes.github.data.model.Owner
import com.eharoldreyes.github.data.model.Repository

fun RepositoryDto.transform() = Repository(
    id = id,
    name = name,
    forks = forks,
    openIssues = open_issues,
    watchers = watchers,
    owner = Owner(
        userName = this.owner.login,
        avatarUrl = this.owner.avatar_url
    )
)