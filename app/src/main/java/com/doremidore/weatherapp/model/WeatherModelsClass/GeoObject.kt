package com.doremidore.weatherapp.model.WeatherModelsClass

data class GeoObject(
    var district: District,
    var locality: Locality,
    var province: Province,
    var country: Country
)