package com.example.weatherapp.logic.model

data class WeatherForecast(val info :String , val forecasts : List<Forecast>)

data class Weather(val nowWeather: List<Lives>, val forecasts: List<Forecast>)

data class Forecast(val city:String ,
                    val province:String , val reporttime:String , val casts : List<Cast>)

data class Cast(val date:String ,
                val week:String ,
                val dayweather:String ,
                val nightweather:String,
                val daytemp:String ,
                val nighttemp:String ,
                val daywind:String ,
                val nightwind:String ,
                val daypower:String ,
                val nightpower:String
    )