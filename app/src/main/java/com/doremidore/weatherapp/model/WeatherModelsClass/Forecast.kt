package com.doremidore.weatherapp.model.WeatherModelsClass

data class Forecast(
    var date: String,
    var date_ts: Int,
    var week: Int,
    var sunrise: String,
    var sunset: String,
    var rise_begin: String,
    var set_end: String,
    var moon_code: Int,
    var moon_text: String,
    var parts: Parts,
    var hours: List<Hour>,
    var biomet: Biomet
)