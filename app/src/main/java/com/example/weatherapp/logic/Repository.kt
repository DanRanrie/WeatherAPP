package com.example.weatherapp.logic

import androidx.lifecycle.liveData
import com.example.weatherapp.logic.dao.PlaceDao
import com.example.weatherapp.logic.model.Lives
import com.example.weatherapp.logic.model.PlaceResponse
import com.example.weatherapp.logic.model.Weather
import com.example.weatherapp.logic.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {

    // 数据本地持久化
    fun saveCity(city: Lives) = PlaceDao.saveCity(city)
    fun getSavedCity() = PlaceDao.getSavedCity()
    fun isCitySaved() = PlaceDao.isCitySaved()
    fun searchPlaces(city: String) = fire(Dispatchers.IO) {
            val placeResponse:PlaceResponse
            if(city =="老登的家"){
                placeResponse = WeatherNetwork.searchPlaces("合肥")
            }
            else if(city == "傻逼的家"){
                placeResponse = WeatherNetwork.searchPlaces("合肥")
            }
            else if(city == "雪菜的家"){
                placeResponse = WeatherNetwork.searchPlaces("天长")
            }
            else{
                placeResponse = WeatherNetwork.searchPlaces(city)
            }
            if (placeResponse.info == "OK") {  // 回显的info是大OK  就是这个原因，所以值无法更新
                val lives = placeResponse.lives
                Result.success(lives)
            } else {
                Result.failure(RuntimeException("response status is${placeResponse.info}"))
            }
    }
    // 定义刷新天气信息
    fun refreshWeather(city : String) = fire(Dispatchers.IO){
            coroutineScope {
                val deferredNowWeather = async {
                    WeatherNetwork.searchPlaces(city)
                }
                val deferredForeCast = async {
                    WeatherNetwork.searchWeather(city)
                }
                val nowWeatherResponse = deferredNowWeather.await()
                val ForeCastResponse = deferredForeCast.await()
                if (nowWeatherResponse.info == "OK" && ForeCastResponse.info == "OK") {
                    val weather = Weather(nowWeatherResponse.lives,
                        ForeCastResponse.forecasts)
                    Result.success(weather)
                } else {
                    Result.failure(
                        RuntimeException(
                            "NowWeather response status is ${nowWeatherResponse.info}" +
                                    "ForeCastWeather response status is ${ForeCastResponse.info}"
                        )
                    )
                }
            }
        }
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
    }


