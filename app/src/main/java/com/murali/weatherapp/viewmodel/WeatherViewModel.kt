package com.murali.weatherapp.viewmodel

import com.murali.weatherapp.model.repository.WeatherRepository
import com.murali.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository): BaseViewModel() {

    fun getWeatherData(lat: String, lon:String, appId: String){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch{
            liveData.postValue(Resource(Resource.Status.LOADING, null, null))
            liveData.postValue(weatherRepository.getWeatherData(lat, lon, appId))
        }
    }
}