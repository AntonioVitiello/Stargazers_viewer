package com.vitiello.android.stargazers.data.source

import com.vitiello.android.stargazers.network.dto.StargazerResponse
import com.vitiello.android.stargazers.network.dto.StargazerResponseItem
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

/**
 * Created by Antonio Vitiello on 22/08/2022.
 */
fun stargazerItem(): StargazerResponse {
    return StargazerResponse().apply {
        add(
            StargazerResponseItem(3959405).apply { // optionals values just for logging
                avatarUrl = "https://avatars.githubusercontent.com/u/3959405?v=4"
                eventsUrl = ""
                followersUrl = ""
                followingUrl = ""
                gistsUrl = ""
                gravatarId = ""
                htmlUrl = "https://github.com/CrisTofani"
                login = "daf-dataportal"
                nodeId = ""
                organizationsUrl = ""
                receivedEventsUrl = ""
                reposUrl = "https://api.github.com/users/CrisTofani/repos"
                siteAdmin = false
                starredUrl = ""
                subscriptionsUrl = ""
                type = ""
                url = "https://api.github.com/users/CrisTofani"
            }
        )
    }
}

fun simulatedException(): Exception {
    return retrofit2.HttpException(
        Response.error<String>(
            404,
            "Response.error()".toResponseBody("text/plain; charset=utf-8".toMediaType())
        )
    )

}