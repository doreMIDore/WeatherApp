package com.doremidore.weatherapp.presenter


interface PresenterInterface {
    fun processWeatherData(lat: String, lon: String)
    fun loadImg(path: String)
}
