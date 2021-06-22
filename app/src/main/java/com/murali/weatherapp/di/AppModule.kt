package com.murali.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.murali.weatherapp.model.remote.WeatherApi
import com.murali.weatherapp.model.repository.WeatherRepository
import com.murali.weatherapp.model.local.WeatherDao
import com.murali.weatherapp.model.local.WeatherDatabase
import com.murali.weatherapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext appContext: Context): WeatherDatabase =
        Room.databaseBuilder(appContext, WeatherDatabase::class.java, "weatherDB").build()

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao =
        weatherDatabase.getWeatherDao()

    @Singleton
    @Provides
    fun provideWeatherRepository(@ApplicationContext appContext: Context, remoteDataSource: WeatherApi, localDataSource: WeatherDao) =
        WeatherRepository(appContext, remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun getLocationClient(@ApplicationContext context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

}