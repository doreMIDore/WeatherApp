package com.doremidore.weatherapp.Utils

import android.content.res.Resources
import com.doremidore.weatherapp.R

//Translate condition from the pull of values
fun translateCondition(string: String, resources: Resources) : String {
    //val context = WeatherActivity()
    //context.getContext()
    val translations = mapOf(
        "clear" to resources.getString(R.string.clear),
        "partly-cloudy" to resources.getString(R.string.partly_cloudy),
        "cloudy" to resources.getString(R.string.cloudy),
        "overcast" to resources.getString(R.string.overcast),
        "drizzle" to resources.getString(R.string.drizzle),
        "light-rain" to resources.getString(R.string.light_rain),
        "rain" to resources.getString(R.string.rain),
        "moderate-rain" to resources.getString(R.string.moderate_rain),
        "heavy-rain" to resources.getString(R.string.heavy_rain),
        "continuous-heavy-rain" to resources.getString(R.string.continuous_heavy_rain),
        "showers" to resources.getString(R.string.showers),
        "wet-snow" to resources.getString(R.string.wet_snow),
        "light-snow" to resources.getString(R.string.light_snow),
        "snow" to resources.getString(R.string.snow),
        "snow-showers" to resources.getString(R.string.snow_showers),
        "hail" to resources.getString(R.string.hail),
        "thunderstorm" to resources.getString(R.string.thunderstorm),
        "thunderstorm-with-rain" to resources.getString(R.string.thunderstorm_with_rain),
        "thunderstorm-with-hail" to resources.getString(R.string.thunderstorm_with_hail)
    )
    return translations[string] ?: (resources.getString(R.string.unknown_condition) +
            "${translations[string]}")
}