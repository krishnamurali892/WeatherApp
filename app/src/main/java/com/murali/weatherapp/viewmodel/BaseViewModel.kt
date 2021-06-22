package com.murali.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murali.weatherapp.model.ServerResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job

open class BaseViewModel: ViewModel() {
    var liveData = MutableLiveData<ServerResponse>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    private fun onError(message: String) {
        liveData.value = ServerResponse(ServerResponse.Status.ERROR, "Error: $message", null)
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}