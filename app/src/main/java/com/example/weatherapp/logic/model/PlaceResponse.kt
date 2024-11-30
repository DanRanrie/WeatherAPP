package com.example.weatherapp.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val info: String, val lives: List<Lives>)

data class Lives(val province: String,
                 val city: String,
                 val adcode: String,
                 val weather: String,
                 val temperature: String,
                 val winddirection: String,
                 val windpower: String,
                 val humidity: String,
                 val reporttime: String,
                 )

//data class Location(val lng: String, val lat: String)