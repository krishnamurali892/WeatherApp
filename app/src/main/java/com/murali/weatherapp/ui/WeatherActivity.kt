package com.murali.weatherapp.ui

import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.location.*
import com.murali.weatherapp.R
import com.murali.weatherapp.databinding.ActivityWeatherInfoBinding
import com.murali.weatherapp.model.ServerResponse
import com.murali.weatherapp.model.weather.WeatherResponse
import com.murali.weatherapp.utils.*
import com.murali.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var binding: ActivityWeatherInfoBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var tvWeatherInfo: TextView
    private val LOCATION_PERMISSION_REQUEST_CODE = 919
    private val TAG = "WeatherActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        setUpObserver()
    }

    override fun onStart() {
        super.onStart()
        if(PermissionUtils.isAccessFineLocationPermissionGranted(this)){
            when {
                PermissionUtils.isLocationEnabled(this) -> startLocationRequests()
                else -> PermissionUtils.showGPSNotEnabledDialog(this)
            }
        }else{
            PermissionUtils.requestAccessFineLocationPermission(
                this,
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun initialize() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_info)
        progressBar = binding.progressBar
        tvWeatherInfo = binding.tvWeatherInfo

        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = Constants.LOCATION_UPDATE_INTERVAL
        locationCallback = object : LocationCallback() {
            override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                super.onLocationAvailability(locationAvailability)
                if (locationAvailability.isLocationAvailable) {
                    Log.i(TAG, "Location is available")
                } else {
                    Log.i(TAG, "Location is unavailable")
                }
            }
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                Log.i(TAG, "Location result is available")
                fetWeatherData(locationResult.lastLocation)
            }
        }
    }

    private fun setUpObserver() {
        weatherViewModel.liveData.observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    ServerResponse.Status.SUCCESS -> {
                        tvWeatherInfo.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let {
                            if (it is WeatherResponse) {
                                tvWeatherInfo.setText(it.getWeatherData())
                            }
                        }
                    }
                    ServerResponse.Status.ERROR -> {
                        tvWeatherInfo.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        showToast(it.message)
                    }
                    ServerResponse.Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        tvWeatherInfo.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun fetWeatherData(location: Location) {
        if (NetworkUtils.isInternetAvailable(this)) {
            weatherViewModel.getWeatherData(
                location.latitude.toString(),
                location.longitude.toString(),
                Constants.APP_ID
            )
        } else {
            showToast(getString(R.string.check_internet_connection))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when {
                        PermissionUtils.isLocationEnabled(this) -> startLocationRequests()
                        else -> PermissionUtils.showGPSNotEnabledDialog(this)
                    }
                } else {
                    showToast(getString(R.string.location_permission_not_granted))
                }
            }
        }
    }

    private fun stopLocationRequests() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationRequests() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            this@WeatherActivity.getMainLooper()
        )
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            it?.let {
                fetWeatherData(it)
            }
        }
        fusedLocationProviderClient.getLastLocation().addOnFailureListener { e ->
            Log.i(TAG, "Exception while getting the location: " + e.message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationRequests()
    }
}