package com.murali.weatherapp.workmanager

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.murali.weatherapp.model.local.WeatherDao
import com.murali.weatherapp.model.remote.WeatherApi
import com.murali.weatherapp.utils.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class WeatherDataSyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao): CoroutineWorker(appContext, workerParams){

    private val TAG = "WeatherDataSyncWorker"

    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {
        Log.d(TAG, "called")
        getLastKnownLocation()?.let {
            getRemoteData(it.latitude.toString(), it.longitude.toString(), Constants.APP_ID)
        }
        return Result.success()
    }

    private suspend fun getRemoteData(lat:String, lon:String, appId:String): Result {
        try {
            val response = weatherApi.getWeatherData(lat, lon, appId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null){
                    weatherDao.deleteWeathers()
                    weatherDao.insertWeather(body.weather)
                    return Result.success()
                }
            }
            return Result.failure()
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    private fun getLastKnownLocation():Location? {
        val lastKnownGPSLocation: Location?
        val lastKnownNetworkLocation: Location?
        var lastKnownLocation:Location?=null
        val gpsLocationProvider = LocationManager.GPS_PROVIDER
        val networkLocationProvider = LocationManager.NETWORK_PROVIDER
        try {
            val locationManager =
                appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            lastKnownNetworkLocation = locationManager.getLastKnownLocation(networkLocationProvider)!!
            lastKnownGPSLocation = locationManager.getLastKnownLocation(gpsLocationProvider)!!
            if (lastKnownGPSLocation != null) {
                Log.i(TAG, "lastKnownGPSLocation is used.")
                lastKnownLocation = lastKnownGPSLocation
            } else if (lastKnownNetworkLocation != null) {
                Log.i(TAG, "lastKnownNetworkLocation is used.")
                lastKnownLocation = lastKnownNetworkLocation
            } else {
                Log.e(TAG, "lastLocation is not known.")
            }
        } catch (sex: SecurityException) {
            Log.e(TAG, "Location permission is not granted!")
        }catch (e: Exception){
            Log.e(TAG, "Unknown exception occured!")
        }
        return lastKnownLocation
    }

}