package com.murali.weatherapp.model

data class ServerResponse(val status: Status,
                          val message: String?, val data: Any?){
    enum class Status() {
        SUCCESS,
        ERROR,
        LOADING
    }
}