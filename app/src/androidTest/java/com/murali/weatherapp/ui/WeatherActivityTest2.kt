package com.murali.weatherapp.ui

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.murali.weatherapp.R
import com.murali.weatherapp.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class WeatherActivityTest2 {

    private lateinit var scenario:ActivityScenario<WeatherActivity>

    @Before
    fun beforeTest(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        scenario = ActivityScenario.launch(WeatherActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @After
    fun afterTest(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    /*Please enable GPS and Network connection
    before running this test*/
    @Test
    fun test_isWeatherInfoDisplayed(){
        onView(withId(R.id.tvWeatherInfo)).check(matches(isDisplayed()))
    }
}