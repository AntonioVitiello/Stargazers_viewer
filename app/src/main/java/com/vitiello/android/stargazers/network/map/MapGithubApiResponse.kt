package com.vitiello.android.stargazers.network.map

import com.vitiello.android.stargazers.model.GithubRepoModel
import com.vitiello.android.stargazers.model.StargazerModel
import com.vitiello.android.stargazers.network.dto.GithubRepoResponse
import com.vitiello.android.stargazers.network.dto.GithubTokenResponse
import com.vitiello.android.stargazers.network.dto.StargazerResponse

/**
 * Created by Antonio Vitiello
 */

fun mapStargazerResponse(response: StargazerResponse): List<StargazerModel> {
    return mutableListOf<StargazerModel>().apply {
        response.forEach { responseItem ->
            responseItem.id?.let { idItem ->
                add(
                    StargazerModel(
                        id = idItem,
                        username = responseItem.login ?: "-",
                        avatarUrl = responseItem.avatarUrl,
                        htmlUrl = responseItem.htmlUrl
                    )
                )
            }
        }
    }
}

fun mapTokenReponse(response: GithubTokenResponse): String {
    return response.accessToken!!
}

fun mapRepositories(list: List<GithubRepoResponse>): List<GithubRepoModel> {
    return ArrayList<GithubRepoModel>().apply {
        for (item in list) {
            item.id?.let { id ->
                add(GithubRepoModel(id).apply {
                    name = item.name
                    owner = item.owner?.login
                    url = item.url
                })
            }
        }
    }
}
