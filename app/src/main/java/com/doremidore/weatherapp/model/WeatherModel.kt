package com.doremidore.weatherapp.model

import com.doremidore.weatherapp.model.WeatherModelsClass.*

data class WeatherModel(
    var now: Int,
    var now_dt: String,
    var info: Info,
    var geo_object: GeoObject,
    var yesterday: Yesterday,
    var fact: Fact,
    var forecasts: List<Forecast>
    )