package com.murali.weatherapp.ui

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.murali.weatherapp.model.entities.Weather
import com.murali.weatherapp.model.local.WeatherDao
import com.murali.weatherapp.model.local.WeatherDatabase
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class WeatherDaoTest : TestCase(){

    private lateinit var weatherDatabase: WeatherDatabase
    private lateinit var weatherDao: WeatherDao

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        weatherDatabase = Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java)
            .allowMainThreadQueries().build()
        weatherDao = weatherDatabase.getWeatherDao()
    }

    @After
    fun teardown(){
        weatherDatabase.close()
    }

    @Test
    fun insertWeather() = runBlocking {
        val weather = Weather(40.0f, 40.0f,
            40.0f, 40.0f, 40.0f)
        weatherDao.insertWeather(weather)
        val weathers = weatherDao.getWeathers()
        assert(weathers.contains(weather))
    }

    @Test
    fun deleteWeather() = runBlocking {
        val weather = Weather(40.0f, 40.0f,
            40.0f, 40.0f, 40.0f)
        weatherDao.insertWeather(weather)
        weatherDao.deleteWeathers()
        val weathers = weatherDao.getWeathers()
        assertFalse(weathers.contains(weather))
    }
}