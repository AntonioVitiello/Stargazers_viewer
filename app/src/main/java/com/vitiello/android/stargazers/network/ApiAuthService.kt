package com.vitiello.android.stargazers.network

import com.vitiello.android.stargazers.network.dto.GithubRepoResponse
import com.vitiello.android.stargazers.network.dto.StargazerResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Antonio Vitiello
 */
interface ApiAuthService {

    @GET("/repos/{owner}/{repo}/stargazers")
    fun loadStargazer(@Path("owner") owner: String, @Path("repo") githubRepo: String): Single<StargazerResponse>

    @GET("user/repos")
    fun loadGithubRepos(@Query("per_page") itemsPerPage: Int): Single<List<GithubRepoResponse>>

}
