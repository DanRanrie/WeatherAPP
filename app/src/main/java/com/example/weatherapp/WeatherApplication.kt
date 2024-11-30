package com.example.weatherapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class WeatherApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        var city = ""
        const val GaoDeWeatherAPI = "bc0dc8c1b3355b3d7f25a05612a1d9a4"
        var WeatherUrl = "https://restapi.amap.com/v3/weather/weatherInfo?key=$GaoDeWeatherAPI&city=$city"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}