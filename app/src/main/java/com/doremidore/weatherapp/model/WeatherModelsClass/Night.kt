package com.doremidore.weatherapp.model.WeatherModelsClass

data class Night(
    var _source: String,
    var temp_min: Int,
    var temp_avg: Int,
    var temp_max: Int,
    var wind_speed: Double,
    var wind_gust: Double,
    var wind_dir: String,
    var pressure_mm: Int,
    var pressure_pa: Int,
    var humidity: Int,
    var soil_temp: Int,
    var soil_moisture: Double,
    var prec_mm: Double,
    var prec_prob: Int,
    var prec_period: Int,
    var cloudness: Double,
    var prec_type: Int,
    var prec_strength: Double,
    var icon: String,
    var condition: String,
    var uv_index: Int,
    var feels_like: Int,
    var daytime: String,
    var polar: Boolean,
    var fresh_snow_mm: Int,
)