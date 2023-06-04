package com.doremidore.weatherapp.model

import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageInterface{
    @GET("{image}.svg")
    suspend fun requestSvgImage(@Path("image") image: String): ResponseBody

    companion object{
        operator fun invoke(): ImageInterface{
            return Retrofit.Builder()
                .baseUrl("https://yastatic.net/weather/i/icons/funky/dark/")
                .build()
                .create(ImageInterface::class.java)
        }
    }
}