package com.example.weatherapp.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.weatherapp.logic.Repository
import com.example.weatherapp.logic.model.Forecast
import com.example.weatherapp.logic.model.Lives

class WeatherViewModel : ViewModel() {

    private val cityLiveData = MutableLiveData<String>()

    var city = ""
    val nowList = ArrayList<Lives>()
    val forecastList = ArrayList<Forecast>()

    val WeatherLiveData = cityLiveData.switchMap { city ->
        Repository.refreshWeather(city)
    }

    fun refreshWeather(city: String) {
        cityLiveData.value = city
    }
}