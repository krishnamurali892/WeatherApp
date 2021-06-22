package com.murali.weatherapp.model

import com.murali.weatherapp.model.weather.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather?")
    fun getWeatherData(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("appid") app_id: String?,
        @Query("units") units: String = "metric"
    ): Call<WeatherResponse?>?
}