package com.vitiello.android.stargazers.network

import com.vitiello.android.stargazers.network.dto.GithubRepoResponse
import com.vitiello.android.stargazers.network.dto.GithubTokenResponse
import com.vitiello.android.stargazers.network.dto.StargazerResponse
import io.reactivex.Single

/**
 * Created by Antonio Vitiello
 */
class GithubRepository {
    private val mNetworkProvider = NetworkProvider()

    fun setAuthToken(authToken: String) {
        mNetworkProvider.setAuthToken(authToken)
    }

    fun setCredential(username: String, password: String) {
        mNetworkProvider.setCredential(username, password)
    }

    fun loadTokenSingle(idClient: String, clientSecret: String, code: String): Single<GithubTokenResponse> {
        return mNetworkProvider.loadTokenSingle(idClient, clientSecret, code)
    }

    fun loadRepositoriesSingle(itemsPerPage: Int): Single<List<GithubRepoResponse>> {
        return mNetworkProvider.loadGithubReposSingle(itemsPerPage)
    }

    fun loadStargazerSingle(owner: String, githubRepo: String): Single<StargazerResponse> {
        return mNetworkProvider.loadStargazerSingle(owner, githubRepo)
    }

}