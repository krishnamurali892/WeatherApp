package com.murali.weatherapp.model.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather?")
    suspend fun getWeatherData(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("appid") app_id: String?,
        @Query("units") units: String = "metric"
    ): Response<WeatherResponse>
}