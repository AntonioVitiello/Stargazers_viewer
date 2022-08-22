package com.vitiello.android.stargazers.network

import android.util.Log
import com.vitiello.android.stargazers.BuildConfig
import com.vitiello.android.stargazers.network.dto.GithubRepoResponse
import com.vitiello.android.stargazers.network.dto.GithubTokenResponse
import com.vitiello.android.stargazers.network.dto.StargazerResponse
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created by Antonio Vitiello
 */
class NetworkProvider {
    private val mNoAuthService by lazy(LazyThreadSafetyMode.NONE) { createService<ApiService>() }
    private val mAuthService by lazy(LazyThreadSafetyMode.NONE) { createService<ApiAuthService>(mAuthToken) }
    private val mBaseAuthService by lazy(LazyThreadSafetyMode.NONE) {
        createService<ApiAuthService>(mUsername, mPassword)
    }
    private var mAuthToken: String? = null
    private var mUsername: String? = null
    private var mPassword: String? = null

    companion object {
        private const val TAG = "NetworkProvider"
        const val NO_AUTH_ENDPOINT = "https://github.com"
        const val AUTH_ENDPOINT = "https://api.github.com"

        fun buildHttpClient(authToken: String? = null): OkHttpClient {
            return OkHttpClient.Builder().apply {
                writeTimeout(60, TimeUnit.SECONDS)
                readTimeout(60, TimeUnit.SECONDS)
                connectTimeout(60, TimeUnit.SECONDS)
                if (authToken != null) {
                    addInterceptor(AuthInterceptor(authToken))
                }
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }.build()
        }

        inline fun <reified T> createService(authToken: String? = null): T {
            val endpoint = if (authToken != null) {
                AUTH_ENDPOINT
            } else {
                NO_AUTH_ENDPOINT
            }
            return Retrofit.Builder()
                .baseUrl(endpoint)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .callbackExecutor(Executors.newCachedThreadPool())
                .client(buildHttpClient(authToken))
                .build()
                .create(T::class.java)
        }

        fun buildHttpClient(username: String, password: String): OkHttpClient {
            return OkHttpClient.Builder().apply {
                writeTimeout(60, TimeUnit.SECONDS)
                readTimeout(60, TimeUnit.SECONDS)
                connectTimeout(60, TimeUnit.SECONDS)
                addInterceptor(BasicAuthInterceptor(username, password))
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }.build()
        }

        inline fun <reified T> createService(username: String?, password: String?): T {
            return Retrofit.Builder()
                .baseUrl(AUTH_ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .callbackExecutor(Executors.newCachedThreadPool())
                .client(buildHttpClient(username ?: "", password ?: ""))
                .build()
                .create(T::class.java)
        }
    }

    init {
        RxJavaPlugins.setErrorHandler { thr -> Log.e(TAG, "RxJava plugin error", thr) }
    }

    fun setAuthToken(authToken: String) {
        mAuthToken = authToken
    }

    fun setCredential(username: String, password: String) {
        mUsername = username
        mPassword = password
    }

    fun loadTokenSingle(idClient: String, clientSecret: String, code: String): Single<GithubTokenResponse> {
        return mNoAuthService.loadToken(idClient, clientSecret, code)
    }

    fun loadGithubReposSingle(itemsPerPage: Int): Single<List<GithubRepoResponse>> {
        return mAuthService.loadGithubRepos(itemsPerPage)
    }

    fun loadStargazerSingle(owner: String, githubRepo: String): Single<StargazerResponse> {
        return mAuthService.loadStargazer(owner, githubRepo)
    }

}