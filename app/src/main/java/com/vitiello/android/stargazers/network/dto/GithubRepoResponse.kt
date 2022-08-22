package com.vitiello.android.stargazers.network.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Antonio Vitiello
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubRepoResponse(@JsonProperty("id") val id: Int?) {
    @JsonProperty("node_id")
    val nodeId: String? = null

    @JsonProperty("name")
    val name: String? = null

    @JsonProperty("full_name")
    val fullName: String? = null

    @JsonProperty("private")
    val private: Boolean? = null

    @JsonProperty("owner")
    val owner: Owner? = null

    @JsonProperty("html_url")
    val htmlUrl: String? = null

    @JsonProperty("fork")
    val fork: Boolean? = null

    @JsonProperty("url")
    val url: String? = null

    @JsonProperty("forks_url")
    val forksUrl: String? = null

    @JsonProperty("keys_url")
    val keysUrl: String? = null

    @JsonProperty("collaborators_url")
    val collaboratorsUrl: String? = null

    @JsonProperty("teams_url")
    val teamsUrl: String? = null

    @JsonProperty("hooks_url")
    val hooksUrl: String? = null

    @JsonProperty("issue_events_url")
    val issueEventsUrl: String? = null

    @JsonProperty("events_url")
    val eventsUrl: String? = null

    @JsonProperty("assignees_url")
    val assigneesUrl: String? = null

    @JsonProperty("branches_url")
    val branchesUrl: String? = null

    @JsonProperty("tags_url")
    val tagsUrl: String? = null

    @JsonProperty("blobs_url")
    val blobsUrl: String? = null

    @JsonProperty("git_tags_url")
    val gitTagsUrl: String? = null

    @JsonProperty("git_refs_url")
    val gitRefsUrl: String? = null

    @JsonProperty("trees_url")
    val treesUrl: String? = null

    @JsonProperty("statuses_url")
    val statusesUrl: String? = null

    @JsonProperty("languages_url")
    val languagesUrl: String? = null

    @JsonProperty("stargazers_url")
    val stargazersUrl: String? = null

    @JsonProperty("contributors_url")
    val contributorsUrl: String? = null

    @JsonProperty("subscribers_url")
    val subscribersUrl: String? = null

    @JsonProperty("subscription_url")
    val subscriptionUrl: String? = null

    @JsonProperty("commits_url")
    val commitsUrl: String? = null

    @JsonProperty("git_commits_url")
    val gitCommitsUrl: String? = null

    @JsonProperty("comments_url")
    val commentsUrl: String? = null

    @JsonProperty("issue_comment_url")
    val issueCommentUrl: String? = null

    @JsonProperty("contents_url")
    val contentsUrl: String? = null

    @JsonProperty("compare_url")
    val compareUrl: String? = null

    @JsonProperty("merges_url")
    val mergesUrl: String? = null

    @JsonProperty("archive_url")
    val archiveUrl: String? = null

    @JsonProperty("downloads_url")
    val downloadsUrl: String? = null

    @JsonProperty("issues_url")
    val issuesUrl: String? = null

    @JsonProperty("pulls_url")
    val pullsUrl: String? = null

    @JsonProperty("milestones_url")
    val milestonesUrl: String? = null

    @JsonProperty("notifications_url")
    val notificationsUrl: String? = null

    @JsonProperty("labels_url")
    val labelsUrl: String? = null

    @JsonProperty("releases_url")
    val releasesUrl: String? = null

    @JsonProperty("deployments_url")
    val deploymentsUrl: String? = null

    @JsonProperty("created_at")
    val createdAt: String? = null

    @JsonProperty("updated_at")
    val updatedAt: String? = null

    @JsonProperty("pushed_at")
    val pushedAt: String? = null

    @JsonProperty("git_url")
    val gitUrl: String? = null

    @JsonProperty("ssh_url")
    val sshUrl: String? = null

    @JsonProperty("clone_url")
    val cloneUrl: String? = null

    @JsonProperty("svn_url")
    val svnUrl: String? = null

    @JsonProperty("size")
    val size: Int? = null

    @JsonProperty("stargazers_count")
    val stargazersCount: Int? = null

    @JsonProperty("watchers_count")
    val watchersCount: Int? = null

    @JsonProperty("language")
    val language: String? = null

    @JsonProperty("has_issues")
    val hasIssues: Boolean? = null

    @JsonProperty("has_projects")
    val hasProjects: Boolean? = null

    @JsonProperty("has_downloads")
    val hasDownloads: Boolean? = null

    @JsonProperty("has_wiki")
    val hasWiki: Boolean? = null

    @JsonProperty("has_pages")
    val hasPages: Boolean? = null

    @JsonProperty("forks_count")
    val forksCount: Int? = null

    @JsonProperty("archived")
    val archived: Boolean? = null

    @JsonProperty("disabled")
    val disabled: Boolean? = null

    @JsonProperty("open_issues_count")
    val openIssuesCount: Int? = null

    @JsonProperty("forks")
    val forks: Int? = null

    @JsonProperty("open_issues")
    val openIssues: Int? = null

    @JsonProperty("watchers")
    val watchers: Int? = null

    @JsonProperty("default_branch")
    val defaultBranch: String? = null

    @JsonProperty("permissions")
    val permissions: Permissions? = null

    @JsonProperty("description")
    val description: String? = null

    @JsonProperty("homepage")
    val homepage: String? = null

    @JsonProperty("mirror_url")
    val mirrorUrl: String? = null
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Owner(
    @JsonProperty("login")
    val login: String?,
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("node_id")
    val nodeId: String?,
    @JsonProperty("avatar_url")
    val avatarUrl: String?,
    @JsonProperty("gravatar_id")
    val gravatarId: String?,
    @JsonProperty("url")
    val url: String?,
    @JsonProperty("html_url")
    val htmlUrl: String?,
    @JsonProperty("followers_url")
    val followersUrl: String?,
    @JsonProperty("following_url")
    val followingUrl: String?,
    @JsonProperty("gists_url")
    val gistsUrl: String?,
    @JsonProperty("starred_url")
    val starredUrl: String?,
    @JsonProperty("subscriptions_url")
    val subscriptionsUrl: String?,
    @JsonProperty("organizations_url")
    val organizationsUrl: String?,
    @JsonProperty("repos_url")
    val reposUrl: String?,
    @JsonProperty("events_url")
    val eventsUrl: String?,
    @JsonProperty("received_events_url")
    val receivedEventsUrl: String?,
    @JsonProperty("type")
    val type: String?,
    @JsonProperty("site_admin")
    val siteAdmin: Boolean?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Permissions(
    @JsonProperty("admin")
    val admin: Boolean?,
    @JsonProperty("push")
    val push: Boolean?,
    @JsonProperty("pull")
    val pull: Boolean?
)