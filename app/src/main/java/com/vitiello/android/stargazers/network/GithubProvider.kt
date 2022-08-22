package com.vitiello.android.stargazers.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created by Antonio Vitiello
 */
class GithubProvider private constructor() {

    lateinit var apiService: ApiAuthService

    companion object {

        @Volatile
        private var INSTANCE: GithubProvider? = null
        const val ENDPOINT = "https://api.github.com"

        fun getInstance(): GithubProvider? = INSTANCE
        fun getInstance(username: String, password: String): GithubProvider =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildProvider(username, password).also { INSTANCE = it }
            }

        private fun buildProvider(username: String, password: String): GithubProvider =
            GithubProvider().apply {
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(BasicAuthInterceptor(username, password))
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build()

                apiService = retrofit.create(ApiAuthService::class.java)
            }

    }

}

