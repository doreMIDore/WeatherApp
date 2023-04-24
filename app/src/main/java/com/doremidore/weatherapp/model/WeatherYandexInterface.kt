package com.doremidore.weatherapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherYandexInterface {
    @Headers("X-Yandex-API-Key: fbde432c-082c-4675-aa57-d0d7933751c8")
    @GET("v2/forecast")  //latlon = 55.751244&lon=37.618423
    suspend fun getWeather(@Query("lat") lat : String,
                   @Query("lon") lon : String ): WeatherModel?
}