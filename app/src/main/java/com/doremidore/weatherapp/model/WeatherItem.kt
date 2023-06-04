package com.doremidore.weatherapp.model

import android.widget.ImageView

data class WeatherItem(
    var dayOfWeek : String,
    var precipitationImageUrl : String,
    var dayTemp : String,
    var nightTemp : String,
)