package com.doremidore.weatherapp.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherDataInterface {
    @Headers("X-Yandex-API-Key: c5984c31-ee03-459b-91c7-20a40a9244a7")
    @GET("v2/forecast")  //latlon = 55.751244&lon=37.618423
    suspend fun requestWeatherData(@Query("lat") lat : String,
                                   @Query("lon") lon : String,
                                   @Query("lang") lang : String = "ru_RU"): WeatherModel

    companion object {
         operator fun invoke() : WeatherDataInterface{
             val BASE_URL = "https://api.weather.yandex.ru/"
             return Retrofit.Builder()
                 .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build()
                 .create(WeatherDataInterface::class.java)
        }

    }
}