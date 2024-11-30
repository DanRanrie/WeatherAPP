package com.example.weatherapp.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.weatherapp.logic.Repository
import com.example.weatherapp.logic.model.Lives

class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val LivesList = ArrayList<Lives>()

    val placeLiveData = searchLiveData.switchMap { city ->
        Repository.searchPlaces(city)
    }

    fun searchPlaces(city: String) {
        searchLiveData.value = city
    }

}