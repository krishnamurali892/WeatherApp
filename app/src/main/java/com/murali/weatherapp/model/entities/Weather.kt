package com.murali.weatherapp.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "table_weather")
data class Weather(
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
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}