package com.vitiello.android.stargazers.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.vitiello.android.stargazers.R
import com.vitiello.android.stargazers.model.StargazerModel
import com.vitiello.android.stargazers.tools.SingleEvent
import com.vitiello.android.stargazers.tools.closeKeyboard
import com.vitiello.android.stargazers.tools.takeIfNotEmpty
import com.vitiello.android.stargazers.view.StargazerWebActivity.Companion.STARGAZER_MODEL_KEY
import com.vitiello.android.stargazers.view.adapter.StargazersAdapter
import com.vitiello.android.stargazers.viewmodel.StargazersViewModel
import kotlinx.android.synthetic.main.fragment_stargazers.*


/**
 * Created by Antonio Vitiello
 */
class StargazersFragment : Fragment(R.layout.fragment_stargazers) {
    private val mViewModel by activityViewModels<StargazersViewModel> { StargazersViewModel.ViewModelFactory(requireActivity().application) }
    private lateinit var mAdapter: StargazersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.stargazersLiveData.observe(this, Observer(::fillData))
        mViewModel.clearStargazersLiveData.observe(this, Observer(::clearRecycler))
        mViewModel.loadStargazersFromMenuLiveData.observe(this, Observer(::loadStargazersFromMenu))
        mViewModel.hideLoadButtonLiveData.observe(this, Observer(::hideLoadButton))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        mAdapter = StargazersAdapter { stargazerModel ->
            val intent = Intent(context, StargazerWebActivity::class.java)
            intent.putExtra(STARGAZER_MODEL_KEY, stargazerModel)
            startActivity(intent)
        }
        stargazerRecycler.adapter = mAdapter
        loadButton.setOnClickListener { loadStargazers() }
    }

    private fun fillData(event: SingleEvent<List<StargazerModel>>) {
        event.getContentIfNotHandled()?.let { models ->
            mAdapter.switchData(models)
            stargazerRecycler.smoothScrollToPosition(0)
        }
    }

    private fun loadStargazersFromMenu(event: SingleEvent<Pair<String, String>>) {
        event.getContentIfNotHandled()?.let { ownerRepoPair ->
            val owner = ownerRepoPair.first
            val repo = ownerRepoPair.second
            ownerEdit.setText(owner)
            repoEdit.setText(repo)
            if (owner.isEmpty() && repo.isEmpty()) {
                mAdapter.switchData(null)
                closeKeyboard()
                ownerEdit.requestFocus()
            } else {
                loadStargazers()
            }
        }
    }

    private fun loadStargazers() {
        takeIfNotEmpty(ownerEdit, repoEdit) { owner, repo ->
            mViewModel.loadStargazers(owner, repo)
        } ?: run {
            mAdapter.switchData(null)
            mViewModel.customToastRequest(getString(R.string.empty_fields_msg))
        }
    }

    private fun hideLoadButton(event: SingleEvent<Boolean>) {
        event.getContentIfNotHandled()?.let { invisible ->
            loadButton.isInvisible = invisible
        }
    }

    private fun clearRecycler(event: SingleEvent<Boolean>) {
        event.getContentIfNotHandled()?.let { clear ->
            if (clear) {
                mAdapter.switchData(null)
            }
        }
    }

}
