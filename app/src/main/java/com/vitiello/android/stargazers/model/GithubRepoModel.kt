package com.vitiello.android.stargazers.model

/**
 * Created by Antonio Vitiello
 */
class GithubRepoModel(val id: Int) {
    var name: String? = null
    var owner: String? = null
    var url: String? = null

    override fun toString(): String {
        return "GithubRepoModel(id=$id, name=$name, owner=$owner, url=$url)"
    }
}
