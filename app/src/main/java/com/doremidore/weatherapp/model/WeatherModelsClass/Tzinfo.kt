package com.doremidore.weatherapp.model.WeatherModelsClass

data class Tzinfo(
    var name: String,
    var abbr: String,
    var dst: Boolean,
    var offset: Int
)