package com.vitiello.android.stargazers

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes

/**
 * Created by Antonio Vitiello
 */
class StargazersApplication : Application() {

    companion object {
        private lateinit var appContext: Context

        fun getString(@StringRes resId: Int): String {
            return appContext.getString(resId)
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

}
