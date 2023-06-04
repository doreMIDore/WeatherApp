package com.doremidore.weatherapp.presenter


interface PresenterInterface {
    fun loadDataWeather(lat: String, lon: String)
    fun loadImg(path: String)
    fun showRecyclerViewData()
}
