package com.murali.weatherapp.model.remote

import com.google.gson.annotations.SerializedName
import com.murali.weatherapp.model.entities.Weather

data class WeatherResponse(
    @SerializedName("main")
    val weather: Weather
){
    fun getWeatherData(): String{
        return """Temp: ${weather.tempval}
            |Min Temp: ${weather.temp_min}
            |Max Temp: ${weather.temp_max}
            |Humidity: ${weather.humidity}
            |""".trimMargin()
    }
}