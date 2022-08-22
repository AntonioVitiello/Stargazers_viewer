package com.vitiello.android.stargazers.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.muddzdev.styleabletoast.StyleableToast
import com.vitiello.android.stargazers.R
import com.vitiello.android.stargazers.tools.SingleEvent
import com.vitiello.android.stargazers.viewmodel.StargazersViewModel
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Antonio Vitiello
 */
class LoginActivity : AppCompatActivity() {
    private val mViewModel by viewModels<StargazersViewModel> { StargazersViewModel.ViewModelFactory(application) }

    companion object {
        const val STARGAZERS_SCHEME = "stargazers"
        const val STARGAZERS_CODE = "code"
        private const val TAG = "LoginActivity"
        private const val WEB_PAGE_URL = "https://github.com/login/oauth/authorize?client_id=%s"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mViewModel.checkInternet()
        mViewModel.checkInternetLiveData.observe(this, Observer(::onCheckInternet))

        initComponents()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initComponents() {
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        webView.webViewClient = LoginWebViewClient()
        val webPageUrl = String.format(WEB_PAGE_URL, mViewModel.getClientId())
        webView.loadUrl(webPageUrl)
    }

    private fun onCheckInternet(event: SingleEvent<Boolean>) {
        event.getContentIfNotHandled()?.let { checked ->
            if (!checked) {
                StyleableToast.makeText(
                    this, getString(R.string.internet_not_reachable_msg), Toast.LENGTH_LONG, R.style.styleableToast
                ).show()
                finish()
            }
        }
    }

    open inner class LoginWebViewClient : WebViewClient() {
        /**
         * On success Url intercepted is:
         * stargazers://stargazers.com/?code=021e1178c4fa4d623955
         */
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
            var shouldOverride = false
            val url = request.url.toString()
            if (url.isNotEmpty()) {
                if (progressView.visibility != View.VISIBLE) {
                    progressView.isVisible = true
                }
                Log.d(TAG, "WebView Login: Url intercepted=$url")
                if (request.url.scheme == STARGAZERS_SCHEME) {
                    shouldOverride = true
                    finish()
                    startActivity(Intent(Intent.ACTION_VIEW, request.url))
                }
            }
            return shouldOverride
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            progressView.isVisible = false
            Log.d(TAG, "WebView Login: page finished:$url")
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            Log.d(TAG, "WebView Login: page started:$url")
        }

        override fun onLoadResource(view: WebView?, url: String?) {
            Log.d(TAG, "WebView Login: loading:$url")
        }

        override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
            super.onReceivedHttpError(view, request, errorResponse)
            Log.e(TAG, "WebView Login: HttpError:$errorResponse")
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            Log.d(TAG, "WebView Login: Error:$error")
        }

    }

}