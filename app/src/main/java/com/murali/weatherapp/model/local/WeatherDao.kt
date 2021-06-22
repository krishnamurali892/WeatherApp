package com.murali.weatherapp.model.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.murali.weatherapp.model.entities.Weather

@Dao
interface WeatherDao {

    @Query("SELECT * FROM table_weather")
    suspend fun getWeathers(): List<Weather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: Weather)

    @Query("DELETE FROM table_weather")
    suspend fun deleteWeathers()
}