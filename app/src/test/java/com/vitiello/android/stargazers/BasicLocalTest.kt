package com.vitiello.android.stargazers

import android.app.Application
import android.content.res.Resources
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class BasicLocalTest {
    private lateinit var resources: Resources

    @Before
    fun setupViewModel() {
        resources = ApplicationProvider.getApplicationContext<Application>().resources
    }

    @Test
    fun check_string_resourses() {
        assertEquals("USING APPLICATION", "Stargazers on Github", resources.getString(R.string.app_name))
    }

    @Test
    fun check_owners_and_repos() {
        val owners = resources.getStringArray(R.array.owners)
        val repos = resources.getStringArray(R.array.repos)

        assertThat("At last one item in menu, eg: Clear", owners.isNotEmpty() && repos.isNotEmpty())
        assertEquals("Check owners, repos same size", owners.size, repos.size)
    }
}
