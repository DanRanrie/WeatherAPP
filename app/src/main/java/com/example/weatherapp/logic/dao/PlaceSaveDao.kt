package com.example.weatherapp.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.logic.model.Lives
import com.google.gson.Gson

object PlaceDao {

    // 将 city 对象存储到SharedPreferences文件中
    fun saveCity(city: Lives) {
        sharedPreferences().edit {
            // 通过GSON将 city 对象转成一个JSON字符串，然后就可以用字符串存储的方式来保存数据
            putString("city", Gson().toJson(city))
        }
    }

    // 读取city对象  与存取方法相反
    fun getSavedCity(): Lives {
        val placeJson = sharedPreferences().getString("city", "")
        return Gson().fromJson(placeJson, Lives::class.java)
    }

    // 判断是否有数据已被存储
    fun isCitySaved() = sharedPreferences().contains("city")

    private fun sharedPreferences() = WeatherApplication.context.
    getSharedPreferences("weather", Context.MODE_PRIVATE)

}