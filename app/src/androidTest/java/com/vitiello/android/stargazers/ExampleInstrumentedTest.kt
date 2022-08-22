package com.vitiello.android.stargazers

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.vitiello.android.stargazers", appContext.packageName)
    }

    @Test
    fun checkApplication() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        Assert.assertTrue("CHECK APPLICATION", appContext is StargazersApplication)
    }

    @Test
    fun useApplication() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        assertEquals("USING APPLICATION", "Stargazers on Github", appContext.getString(R.string.app_name))
    }

}
