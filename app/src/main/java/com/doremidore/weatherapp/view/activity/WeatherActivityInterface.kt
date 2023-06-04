package com.doremidore.weatherapp.view.activity

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.doremidore.weatherapp.model.WeatherModel
import com.doremidore.weatherapp.model.WeatherModelsClass.Forecast

interface WeatherActivityInterface {
    fun fillDataWeatherCard(weatherData: WeatherModel?)
    fun onWeatherDataLoaded(weatherData: WeatherModel?)
    fun fillImageWeatherCard(drawable: PictureDrawable)
    fun initRcView(weatherList: List<Forecast>?)
    fun getContext(): Context
}