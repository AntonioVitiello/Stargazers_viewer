package com.vitiello.android.stargazers.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.muddzdev.styleabletoast.StyleableToast
import com.vitiello.android.stargazers.BuildConfig
import com.vitiello.android.stargazers.R
import com.vitiello.android.stargazers.tools.SingleEvent
import com.vitiello.android.stargazers.view.LoginActivity.Companion.STARGAZERS_CODE
import com.vitiello.android.stargazers.view.LoginActivity.Companion.STARGAZERS_SCHEME
import com.vitiello.android.stargazers.viewmodel.StargazersViewModel
import kotlinx.android.synthetic.main.activity_stargazers.*


/**
 * Created by Antonio Vitiello
 */
class StargazersActivity : AppCompatActivity(R.layout.activity_stargazers), CredentialsDialog.ICredentialsDialogListener {
    private val mViewModel by viewModels<StargazersViewModel> { StargazersViewModel.ViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        handleIntent(intent)

        mViewModel.customToastLiveData.observe(this, Observer(::showCustomToast))
        mViewModel.progressLiveData.observe(this, Observer(::showProgress))
        mViewModel.errorLiveData.observe(this, Observer(::onError))

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.data?.let { uri ->
            if (STARGAZERS_SCHEME == uri.scheme) {
                login(uri.getQueryParameter(STARGAZERS_CODE))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_00 -> {
                mViewModel.loadStargazerFromMenu(0)
                return true
            }
            R.id.menu_item_01 -> {
                mViewModel.loadStargazerFromMenu(1)
                return true
            }
            R.id.menu_item_02 -> {
                mViewModel.loadStargazerFromMenu(2)
                return true
            }
            R.id.menu_item_03 -> {
                mViewModel.loadStargazerFromMenu(3)
                return true
            }
            R.id.menu_item_04 -> {
                mViewModel.loadStargazerFromMenu(4)
                return true
            }
            R.id.menu_item_05 -> {
                mViewModel.loadStargazerFromMenu(5)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showProgress(event: SingleEvent<Boolean>) {
        event.getContentIfNotHandled()?.let { flag ->
            progressView.isVisible = flag
            mViewModel.hideLoadButton(flag)
        }
    }

    private fun onError(event: SingleEvent<Boolean>) {
        if (event.getContentIfNotHandled() == true) {
            mViewModel.clearStargazersRequest()
            customToast.showFaded(getString(R.string.generic_network_error))
        }
    }

    private fun showCredentialsDialog() {
        val username = BuildConfig.USERNAME
        val password = BuildConfig.PASSWORD
        CredentialsDialog.newInstance(username, password)
            .show(supportFragmentManager, CredentialsDialog.TAG)
    }

    override fun onDialogPositiveClick(username: String, password: String) {
        mViewModel.setCredential(username, password)
        customToast.showFaded(getString(R.string.github_oauth_msg))
    }

    fun login(stargazersCode: String?) {
        if (stargazersCode != null) {
            mViewModel.login(stargazersCode)
        } else {
            StyleableToast.makeText(
                this, getString(R.string.login_code_error_msg), LENGTH_SHORT, R.style.styleableToast
            ).show()
            finish()
        }
    }

    private fun showCustomToast(event: SingleEvent<String>) {
        event.getContentIfNotHandled()?.let { message ->
            customToast.showFaded(message)
        }
    }

}
