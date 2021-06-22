package com.murali.weatherapp.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.murali.weatherapp.model.entities.Weather

@Database(entities = [Weather::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
}