package com.example.weatherapp.logic.network

import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.logic.model.WeatherForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("v3/weather/weatherInfo?key=${WeatherApplication.GaoDeWeatherAPI}&extensions=all")
    fun searchWeather(@Query("city")city: String): Call<WeatherForecast>
}