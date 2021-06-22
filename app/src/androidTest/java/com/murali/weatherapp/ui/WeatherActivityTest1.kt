package com.murali.weatherapp.ui

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.murali.weatherapp.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class WeatherActivityTest1 {

    private lateinit var scenario:ActivityScenario<WeatherActivity>

    @Before
    fun beforeTest(){
        scenario = ActivityScenario.launch(WeatherActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun test_isActivityDisplayed(){
        onView(withId(R.id.weatherScreen)).check(matches(isDisplayed()))
    }
}