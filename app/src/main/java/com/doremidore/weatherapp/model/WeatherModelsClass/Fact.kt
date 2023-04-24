package com.doremidore.weatherapp.model.WeatherModelsClass

data class Fact(
    var obs_time: Int,
    var uptime: Int,
    var temp: Int,
    var feels_like: Int,
    var icon: String,
    var condition: String,
    var cloudness: Double,
    var prec_type: Int,
    var prec_prob: Int,
    var prec_strength: Double,
    var is_thunder: Boolean,
    var wind_speed: Double,
    var wind_dir: String,
    var pressure_mm: Int,
    var pressure_pa: Int,
    var humidity: Int,
    var daytime: String,
    var polar: Boolean,
    var season: String,
    var source: String,
    var accum_prec: AccumPrec,
    var soil_moisture: Double,
    var soil_temp: Int,
    var uv_index: Int,
    var wind_gust: Double
)