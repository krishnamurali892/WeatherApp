package com.murali.weatherapp.utils

class Constants {

    companion object{
        const val APP_ID = "59b9c49620ceb0f2da6159019d7af0d3"
        const val BASE_URL = "https://api.openweathermap.org/"
        const val LOCATION_UPDATE_INTERVAL:Long = 5*1000//5 seconds And for 2 hr 1000*(2*60*60)
    }
}
