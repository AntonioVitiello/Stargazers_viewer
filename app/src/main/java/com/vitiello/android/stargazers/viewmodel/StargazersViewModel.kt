package com.vitiello.android.stargazers.viewmodel

import android.app.Application
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.vitiello.android.stargazers.BuildConfig
import com.vitiello.android.stargazers.R
import com.vitiello.android.stargazers.model.GithubRepoModel
import com.vitiello.android.stargazers.model.StargazerModel
import com.vitiello.android.stargazers.network.GithubRepository
import com.vitiello.android.stargazers.network.map.mapRepositories
import com.vitiello.android.stargazers.network.map.mapStargazerResponse
import com.vitiello.android.stargazers.network.map.mapTokenReponse
import com.vitiello.android.stargazers.tools.SingleEvent
import com.vitiello.android.stargazers.tools.hostAvailable
import com.vitiello.android.stargazers.tools.manageProgress
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Antonio Vitiello
 */
class StargazersViewModel(
    private val mRepository: GithubRepository,
    application: Application
) : AndroidViewModel(application) {

    private val mDisposable = CompositeDisposable()

    private val _stargazersLiveData = MutableLiveData<SingleEvent<List<StargazerModel>>>()
    val stargazersLiveData: LiveData<SingleEvent<List<StargazerModel>>> = _stargazersLiveData

    private val _repositoriesLiveData = MutableLiveData<List<GithubRepoModel>>()
    val repositoriesLiveData: LiveData<List<GithubRepoModel>> = _repositoriesLiveData

    private val _checkInternetLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val checkInternetLiveData: LiveData<SingleEvent<Boolean>> = _checkInternetLiveData

    private val _errorLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val errorLiveData: LiveData<SingleEvent<Boolean>> = _errorLiveData

    private val _progressLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val progressLiveData: LiveData<SingleEvent<Boolean>> = _progressLiveData

    private val _clearStargazersLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val clearStargazersLiveData: LiveData<SingleEvent<Boolean>> = _clearStargazersLiveData

    private val _customToastLiveData = MutableLiveData<SingleEvent<String>>()
    val customToastLiveData: LiveData<SingleEvent<String>> = _customToastLiveData

    private val _loadStargazersFromMenuLiveData = MutableLiveData<SingleEvent<Pair<String, String>>>()
    val loadStargazersFromMenuLiveData: LiveData<SingleEvent<Pair<String, String>>> = _loadStargazersFromMenuLiveData

    private val _hideLoadButtonLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val hideLoadButtonLiveData: LiveData<SingleEvent<Boolean>> = _hideLoadButtonLiveData

    private val mOwners by lazy(LazyThreadSafetyMode.NONE) {
        getApplication<Application>().resources.obtainTypedArray(R.array.owners)
    }
    private val mRepos by lazy(LazyThreadSafetyMode.NONE) {
        getApplication<Application>().resources.obtainTypedArray(R.array.repos)
    }

    companion object {
        const val TAG = "StargazersViewModel"
        const val HOST_TO_CHECK = "github.com"
    }


    fun checkInternet() {
        mDisposable.add(
            Single.fromCallable {
                hostAvailable(HOST_TO_CHECK)
            }
                .subscribeOn(Schedulers.io())
                .manageProgress(_progressLiveData)
                .subscribe({ isAvailable ->
                    _checkInternetLiveData.postValue(SingleEvent(isAvailable))
                }, { exc ->
                    _errorLiveData.postValue(SingleEvent(true))
                    Log.e(TAG, "Error on checkInternet.", exc)
                })
        )
    }

    fun login(stargazersCode: String) {
        val idClient = getClientId()
        val clientSecret = getClientSec()
        mDisposable.add(
            mRepository.loadTokenSingle(idClient, clientSecret, stargazersCode)
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .manageProgress(_progressLiveData)
                .map(::mapTokenReponse)
                .subscribe({ token ->
                    mRepository.setAuthToken(token)
                    val message = getApplication<Application>().getString(R.string.login_success_msg)
                    _customToastLiveData.postValue(SingleEvent(message))
                }, { exc ->
                    _errorLiveData.postValue(SingleEvent(true))
                    Log.e(TAG, "Error while login.", exc)
                })
        )
    }

    fun setCredential(username: String, password: String) {
        mRepository.setCredential(username, password)
    }

    fun loadRepositories(itemsPerPage: Int) {
        mDisposable.add(
            mRepository.loadRepositoriesSingle(itemsPerPage)
                .subscribeOn(Schedulers.io())
                .manageProgress(_progressLiveData)
                .map(::mapRepositories)
                .subscribe({ repos ->
                    _repositoriesLiveData.postValue(repos)
                }, { exc ->
                    _errorLiveData.postValue(SingleEvent(true))
                    Log.e(TAG, "Error while loading Github repos.", exc)
                })
        )
    }

    @MainThread
    fun loadStargazerFromMenu(index: Int) {
        val owner = mOwners.getString(index) ?: "-"
        val repo = mRepos.getString(index) ?: "-"
        _loadStargazersFromMenuLiveData.value = SingleEvent(owner to repo)
    }

    fun loadStargazers(owner: String, repo: String) {
        mDisposable.add(
            mRepository.loadStargazerSingle(owner, repo)
                .subscribeOn(Schedulers.io())
                .manageProgress(_progressLiveData)
                .map(::mapStargazerResponse)
                .subscribe({ stargazers ->
                    _stargazersLiveData.postValue(SingleEvent(stargazers))
                }, { exc ->
                    _errorLiveData.postValue(SingleEvent(true))
                    Log.e(TAG, "Error while loading stargazers.", exc)
                })
        )
    }

    fun getClientId(): String {
        return BuildConfig.CLIENT_ID.ifEmpty {
            // should be removed and instead put the property 'client_id' in local.properties file
            getApplication<Application>().getString(R.string.tikid)
        }
    }

    fun getClientSec(): String {
        return BuildConfig.CLIENT_SECRET.ifEmpty {
            // should be removed and instead put the property 'client_secret' in local.properties file
            getApplication<Application>().getString(R.string.tikcod)
        }
    }

    @MainThread
    fun clearStargazersRequest() {
        _clearStargazersLiveData.value = (SingleEvent(true))
    }

    @MainThread
    fun customToastRequest(message: String) {
        _customToastLiveData.value = (SingleEvent(message))
    }

    override fun onCleared() {
        if (!mDisposable.isDisposed) {
            mDisposable.dispose()
        }
    }

    @MainThread
    fun hideLoadButton(hide: Boolean) {
        _hideLoadButtonLiveData.value = SingleEvent(hide)
    }


    class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(StargazersViewModel::class.java)) {
                StargazersViewModel(GithubRepository(), application) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

}