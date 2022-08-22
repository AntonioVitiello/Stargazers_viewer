package com.vitiello.android.stargazers.network.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Antonio Vitiello
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String?,
    @JsonProperty("scope")
    val scope: String?,
    @JsonProperty("token_type")
    val tokenType: String?
)