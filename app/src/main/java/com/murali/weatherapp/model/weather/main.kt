package com.murali.weatherapp.model.weather

import com.google.gson.annotations.SerializedName

data class main(
    @SerializedName("temp")
    val tempval: Float,
    @SerializedName("humidity")
    val humidity: Float,
    @SerializedName("pressure")
    val pressure: Float,
    @SerializedName("temp_min")
    val temp_min: Float,
    @SerializedName("temp_max")
    val temp_max: Float
)