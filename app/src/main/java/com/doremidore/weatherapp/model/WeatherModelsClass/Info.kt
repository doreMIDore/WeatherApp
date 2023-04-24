package com.doremidore.weatherapp.model.WeatherModelsClass

data class Info(
    var n: Boolean,
    var geoid: Int,
    var url: String,
    var lat: Double,
    var lon: Double,
    var tzinfo: Tzinfo,
    var def_pressure_mm: Int,
    var def_pressure_pa: Int,
    var slug: String,
    var zoom: Int,
    var nr: Boolean,
    var ns: Boolean,
    var nsr: Boolean,
    var p: Boolean,
    var f: Boolean,
    var _h: Boolean
)