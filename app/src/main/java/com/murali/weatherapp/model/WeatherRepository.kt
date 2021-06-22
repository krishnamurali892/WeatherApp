package com.murali.weatherapp.model

class WeatherRepository(private val weatherApi: WeatherApi) {
    fun getWeatherData(lat:String, lon:String, appId:String) = weatherApi.getWeatherData(lat, lon, appId)
}