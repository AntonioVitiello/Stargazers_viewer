package com.vitiello.android.stargazers

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.vitiello.android.stargazers.data.source.simulatedException
import com.vitiello.android.stargazers.data.source.stargazerItem
import com.vitiello.android.stargazers.network.GithubRepository
import com.vitiello.android.stargazers.network.dto.GithubRepoResponse
import com.vitiello.android.stargazers.network.map.mapStargazerResponse
import com.vitiello.android.stargazers.tools.SingleEvent
import com.vitiello.android.stargazers.viewmodel.StargazersViewModel
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


/**
 * Created by Antonio Vitiello
 */
@RunWith(AndroidJUnit4::class)
class StargazersViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var viewModel: StargazersViewModel

    @Mock
    private lateinit var repositoryMock: GithubRepository

    private lateinit var gitHubOwners: Array<String>
    private lateinit var gitHubRepos: Array<String>


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        val appContext = ApplicationProvider.getApplicationContext<Application>()
        viewModel = StargazersViewModel(repositoryMock, appContext)
        gitHubOwners = appContext.resources.getStringArray(R.array.owners)
        gitHubRepos = appContext.resources.getStringArray(R.array.repos)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `check Internet connection is successful`() {
        viewModel.checkInternet()
        val event = viewModel.checkInternetLiveData.getOrAwaitValue()
        assertTrue(event.getContentIfNotHandled() == true)
    }

    @Test
    fun `load stargazers is successful`() {
        val owner = gitHubOwners[2]
        val repo = gitHubRepos[2]
        val stargazersMockResponse = stargazerItem()
        val singleResponse = Single.just(stargazersMockResponse)
        val eventModel = SingleEvent(mapStargazerResponse(stargazersMockResponse))

        // optional just for logging
        val logger = singleResponse.subscribe({ response ->
            println("LOG Response:$response")
        }, { thr ->
            println("LOG Error: $thr")
        })

        whenever(repositoryMock.loadStargazerSingle(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(singleResponse)

        val progressObserver = viewModel.progressLiveData.testObserver()
        val errorObserver = viewModel.errorLiveData.testObserver()
        viewModel.loadStargazers(owner, repo)
        val event = viewModel.stargazersLiveData.getOrAwaitValue()


        verify(repositoryMock, times(1)).loadStargazerSingle(Mockito.anyString(), Mockito.anyString())
        Truth.assertThat(eventModel.peekContent()[0].id)
            .isEqualTo(event.getContentIfNotHandled()?.get(0)?.id)
        assertEquals(2, progressObserver.observedValues.size)
        assertEquals(0, errorObserver.observedValues.size)
    }

    @Test
    fun `load stargazers is failure`() {
        whenever(repositoryMock.loadStargazerSingle(Mockito.anyString(), Mockito.anyString()))
            .thenAnswer { Single.error<retrofit2.HttpException>(simulatedException()) }

        val progressObserver = viewModel.progressLiveData.testObserver()
        val testObserver = viewModel.stargazersLiveData.testObserver()
        val errorObserver = viewModel.errorLiveData.testObserver()
        viewModel.loadStargazers("owner", "repo")
        val event = viewModel.errorLiveData.getOrAwaitValue()

        assertEquals(0, testObserver.observedValues.size)
        assertEquals(1, errorObserver.observedValues.size)
        assertEquals(2, progressObserver.observedValues.size)
        assertEquals(true, event.getContentIfNotHandled())
    }

    @Test
    fun `load Github repositories is successful`() {
        val idExpected = 12345
        val idResponse = 12345
        val responseMock: List<GithubRepoResponse> = mutableListOf(GithubRepoResponse(idResponse))
        val singleResponse = Single.just(responseMock)

        whenever(repositoryMock.loadRepositoriesSingle(Mockito.anyInt()))
            .thenReturn(singleResponse)

        viewModel.loadRepositories(25)

        val testObserver = viewModel.repositoriesLiveData.testObserver()
        Truth.assertThat(idExpected)
            .isEqualTo(testObserver.observedValues[0]?.get(0)?.id)
    }

}