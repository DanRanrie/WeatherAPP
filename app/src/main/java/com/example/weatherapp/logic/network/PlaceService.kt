package com.example.weatherapp.logic.network

import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.WeatherApplication.Companion.GaoDeWeatherAPI
import com.example.weatherapp.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("v3/weather/weatherInfo?key=$GaoDeWeatherAPI")
    fun searchPlaces(@Query("city")city:String): Call<PlaceResponse>

}