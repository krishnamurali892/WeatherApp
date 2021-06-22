package com.murali.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murali.weatherapp.utils.Resource
import com.murali.weatherapp.model.remote.WeatherResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job

open class BaseViewModel: ViewModel(){
    var liveData = MutableLiveData<Resource<WeatherResponse>>()
    var job: Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    private fun onError(message: String) {
        liveData.postValue(Resource(Resource.Status.ERROR, null, "Error: $message"))
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}