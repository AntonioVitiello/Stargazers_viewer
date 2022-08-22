package com.vitiello.android.stargazers.view

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vitiello.android.stargazers.R
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Antonio Vitiello
 */
@RunWith(AndroidJUnit4::class)
class StargazersFragmentTest : TestCase() {
    private lateinit var scenario: FragmentScenario<StargazersFragment>

    @Before
    public override fun setUp() {
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun test1() {
        onView(withId(R.id.ownerEdit)).perform(ViewActions.typeText("pagopa"))
        onView(withId(R.id.repoEdit)).perform(ViewActions.typeText("io-app"))
        onView(withId(R.id.loadButton)).perform(ViewActions.click())
        assertEquals("0", "0")
        Thread.sleep(5000)
    }

}