package com.example.weatherapp.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.example.weatherapp.logic.model.Lives
import com.example.weatherapp.logic.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers

object Repository {
    fun searchPlaces(city: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = WeatherNetwork.searchPlaces(city)
            if (placeResponse.info == "OK") {  // 回显的info是大OK  就是这个原因，所以值无法更新
                val lives = placeResponse.lives
                Result.success(lives)
            } else {
                Result.failure(RuntimeException("response status is${placeResponse.info}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Lives>>(e)
        }
        emit(result)
    }
}