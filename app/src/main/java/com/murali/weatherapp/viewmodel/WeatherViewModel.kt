package com.murali.weatherapp.viewmodel

import com.murali.weatherapp.model.WeatherRepository
import com.murali.weatherapp.model.weather.WeatherResponse
import com.murali.weatherapp.model.ServerResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository): BaseViewModel() {

    fun getWeatherData(lat: String, lon:String, appId: String){
        liveData.value = ServerResponse(ServerResponse.Status.LOADING, null, null)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val requestCall = weatherRepository.getWeatherData(lat, lon, appId)
            withContext(Dispatchers.Main) {
                requestCall?.enqueue(object : Callback<WeatherResponse?> {
                    override fun onResponse(call: Call<WeatherResponse?>, response: Response<WeatherResponse?>) {
                        if (response.isSuccessful) {
                            liveData.value =
                                ServerResponse(ServerResponse.Status.SUCCESS, null, response.body())
                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse?>, t: Throwable) {
                        liveData.value =
                            ServerResponse(ServerResponse.Status.ERROR, "Error occured ${t.localizedMessage}", null)
                    }
                })
            }
        }
    }
}