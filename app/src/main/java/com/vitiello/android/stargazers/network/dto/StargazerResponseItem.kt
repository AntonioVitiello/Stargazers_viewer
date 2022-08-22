package com.vitiello.android.stargazers.network.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Created by Antonio Vitiello
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class StargazerResponseItem(@JsonProperty("id") val id: Int?) {

    @JsonProperty("avatar_url")
    var avatarUrl: String? = null

    @JsonProperty("events_url")
    var eventsUrl: String? = null

    @JsonProperty("followers_url")
    var followersUrl: String? = null

    @JsonProperty("following_url")
    var followingUrl: String? = null

    @JsonProperty("gists_url")
    var gistsUrl: String? = null

    @JsonProperty("gravatar_id")
    var gravatarId: String? = null

    @JsonProperty("html_url")
    var htmlUrl: String? = null

    @JsonProperty("login")
    var login: String? = null

    @JsonProperty("node_id")
    var nodeId: String? = null

    @JsonProperty("organizations_url")
    var organizationsUrl: String? = null

    @JsonProperty("received_events_url")
    var receivedEventsUrl: String? = null

    @JsonProperty("repos_url")
    var reposUrl: String? = null

    @JsonProperty("site_admin")
    var siteAdmin: Boolean? = null

    @JsonProperty("starred_url")
    var starredUrl: String? = null

    @JsonProperty("subscriptions_url")
    var subscriptionsUrl: String? = null

    @JsonProperty("type")
    var type: String? = null

    @JsonProperty("url")
    var url: String? = null

    override fun toString(): String {
        return "StargazerResponseItem(id=$id, avatarUrl=$avatarUrl, eventsUrl=$eventsUrl, followersUrl=$followersUrl, followingUrl=$followingUrl, gistsUrl=$gistsUrl, gravatarId=$gravatarId, htmlUrl=$htmlUrl, login=$login, nodeId=$nodeId, organizationsUrl=$organizationsUrl, receivedEventsUrl=$receivedEventsUrl, reposUrl=$reposUrl, siteAdmin=$siteAdmin, starredUrl=$starredUrl, subscriptionsUrl=$subscriptionsUrl, type=$type, url=$url)"
    }

}