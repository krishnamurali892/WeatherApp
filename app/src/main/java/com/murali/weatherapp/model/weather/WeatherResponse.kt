package com.murali.weatherapp.model.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("main")
    val main: main
){
    fun getWeatherData(): String{
        return """Temp: ${main.tempval}
            |Min Temp: ${main.temp_min}
            |Max Temp: ${main.temp_max}
            |Humidity: ${main.humidity}
            |""".trimMargin()
    }
}