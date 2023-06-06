package com.doremidore.weatherapp.model.Repository

import android.graphics.drawable.PictureDrawable
import com.doremidore.weatherapp.model.WeatherModel

interface WeatherRepositoryInterface {
    suspend fun getWeatherData(lat: String = "55.751244", lon: String = "37.618423"): WeatherModel
    suspend fun getImage(iconPath: String): PictureDrawable
    fun getData() : WeatherModel?
}