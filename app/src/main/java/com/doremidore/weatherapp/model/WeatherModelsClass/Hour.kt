package com.doremidore.weatherapp.model.WeatherModelsClass

data class Hour(
    var hour: String,
    var hour_ts: Int,
    var temp: Int,
    var feels_like: Int,
    var icon: String,
    var condition: String,
    var cloudness: Double,
    var prec_type: Int,
    var prec_strength: Double,
    var is_thunder: Boolean,
    var wind_dir: String,
    var wind_speed: Double,
    var wind_gust: Double,
    var pressure_mm: Int,
    var pressure_pa: Int,
    var humidity: Int,
    var uv_index: Int,
    var soil_temp: Int,
    var soil_moisture: Double,
    var prec_mm: Double,
    var prec_period: Double,
    var prec_prob: Double,

)