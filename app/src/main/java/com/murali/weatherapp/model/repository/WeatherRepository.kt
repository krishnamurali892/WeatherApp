package com.murali.weatherapp.model.repository

import android.content.Context
import android.util.Log
import com.murali.weatherapp.model.local.WeatherDao
import com.murali.weatherapp.model.remote.WeatherApi
import com.murali.weatherapp.model.remote.WeatherResponse
import com.murali.weatherapp.utils.NetworkUtils
import com.murali.weatherapp.utils.Resource

class WeatherRepository(
    private val context: Context,
    private val remoteDataSource: WeatherApi,
    private val localDataSource: WeatherDao) {

    private val TAG = WeatherRepository::class.java.simpleName

    suspend fun getWeatherData(lat:String, lon:String, appId:String): Resource<WeatherResponse> {
        if(NetworkUtils.isInternetAvailable(context)){
            return getRemoteData(lat, lon, appId)
        }
        return getLocalData()
    }

    private suspend fun getRemoteData(lat:String, lon:String, appId:String): Resource<WeatherResponse> {
        try {
            val response = remoteDataSource.getWeatherData(lat, lon, appId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null){
                    localDataSource.deleteWeathers()
                    localDataSource.insertWeather(body.weather)
                    return Resource.success(body)
                }
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private suspend fun getLocalData(): Resource<WeatherResponse>{
        try {
            val weathers = localDataSource.getWeathers()
            if (weathers.isNotEmpty()) {
                val weatherResponse = WeatherResponse(weathers[0])
                return Resource.success(weatherResponse)
            }
            return error("Error no offline data")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.i(TAG, message)
        return Resource.error("Network call has failed for a following reason: $message")
    }
}