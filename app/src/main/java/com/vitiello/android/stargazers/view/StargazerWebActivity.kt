package com.vitiello.android.stargazers.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.vitiello.android.stargazers.R
import com.vitiello.android.stargazers.model.StargazerModel
import kotlinx.android.synthetic.main.activity_login.*


/**
 * Created by Antonio Vitiello
 */
class StargazerWebActivity : AppCompatActivity() {

    companion object {
        const val STARGAZER_MODEL_KEY = "stargazer_model_key"
        private const val TAG = "StargazerWebActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stargazer_web)

        intent.getParcelableExtra<StargazerModel>(STARGAZER_MODEL_KEY)?.let { stargazerModel ->
            val pageUrl = stargazerModel.htmlUrl
            if (!pageUrl.isNullOrEmpty()) {
                initComponents(pageUrl)
            } else {
                finish()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initComponents(pageUrl: String) {
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            mixedContentMode = MIXED_CONTENT_ALWAYS_ALLOW
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
                mixedContentMode = MIXED_CONTENT_ALWAYS_ALLOW
            }
        }

        webView.webChromeClient = StagazerWebChromeClient()
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            javaScriptCanOpenWindowsAutomatically = true
            loadsImagesAutomatically = true
            builtInZoomControls = true
            setSupportMultipleWindows(true)
            setSupportZoom(false)
            allowContentAccess = true
            allowFileAccess = true
        }
        webView.loadUrl(pageUrl)

    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }


    inner class StagazerWebChromeClient : WebChromeClient() {

        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            Log.d(
                TAG, """StagazerWeb: console message:${consoleMessage?.message()}
                | line:${consoleMessage?.sourceId()}
                | line ${consoleMessage?.lineNumber()}""".trimMargin()
            )
            return super.onConsoleMessage(consoleMessage)
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            if (newProgress > 99) {
                progressView.isVisible = false
            }
            Log.d(TAG, "StagazerWeb: progress $newProgress")
        }
    }

}