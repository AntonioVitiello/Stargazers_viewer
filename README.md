# Stargazers viewer

## Objective

Create a simple mobile app that, through the GitHub API, allows you to view the list of stargazers in a repository i.e. the list of users
who have added it to their favorites.

## API docs

[https://docs.github.com/en/rest/activity/starring#list-stargazers](https://docs.github.com/en/rest/activity/starring#list-stargazers)<br/>
[https://docs.github.com/en/developers/apps](https://docs.github.com/en/developers/apps)

## Technologies

* Used the MVVM pattern with Kotlin and Jetpack.
* Used a WebView for Github login, in order to get the ticket and then the OAuth token which allows to use the REST api.
* Added a Toolbar to introduce some pre-established users and repositories in order to be able to carry out manual tests without having to
  type anything, moreover clicking on a stargazer allows navigation to its repositories.

## Important

Before building the project it is necessary to write the clien_id and client_secret code in the local_properties file

`client_id=xxxxxxxx`<br/>
`client_secret=xxxxxxxx`

## Release build

For the biuld type release it is possible to add the following properties in the Global 'gradle.properties' file:

`RELEASE_STORE_FILE=<path_for_release_keystore>/release.keystore`<br/>
`RELEASE_STORE_PASSWORD=<store_password>`<br/>
`RELEASE_KEY_ALIAS=<key_alias>`<br/>
`RELEASE_KEY_PASSWORD=<password>`<br/>
