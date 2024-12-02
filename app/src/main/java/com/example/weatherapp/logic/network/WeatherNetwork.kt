package com.example.weatherapp.logic.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.math.log

object WeatherNetwork {

    // 类实例方法 和 泛型方法 这两种好像都可以实现效果
    private val placeService = ServiceCreator.create<PlaceService>()
    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    // await 等待结果 阻塞
    suspend fun searchPlaces(city: String) = placeService.searchPlaces(city).await()

    suspend fun searchWeather(city: String) = weatherService.searchWeather(city).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null)
                        continuation.resume(body)
                    else
                        continuation.resumeWithException(
                        RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}