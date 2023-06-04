package com.doremidore.weatherapp.Utils

import android.content.Context
import com.doremidore.weatherapp.R

//Translate condition from the pull of values
fun translateCondition(string: String, context: Context?) : String {
    val translations = mapOf(
        "clear" to context?.resources?.getString(R.string.clear),
        "partly-cloudy" to context?.resources?.getString(R.string.partly_cloudy),
        "cloudy" to context?.resources?.getString(R.string.cloudy),
        "overcast" to context?.resources?.getString(R.string.overcast),
        "drizzle" to context?.resources?.getString(R.string.drizzle),
        "light-rain" to context?.resources?.getString(R.string.light_rain),
        "rain" to context?.resources?.getString(R.string.rain),
        "moderate-rain" to context?.resources?.getString(R.string.moderate_rain),
        "heavy-rain" to context?.resources?.getString(R.string.heavy_rain),
        "continuous-heavy-rain" to context?.resources?.getString(R.string.continuous_heavy_rain),
        "showers" to context?.resources?.getString(R.string.showers),
        "wet-snow" to context?.resources?.getString(R.string.wet_snow),
        "light-snow" to context?.resources?.getString(R.string.light_snow),
        "snow" to context?.resources?.getString(R.string.snow),
        "snow-showers" to context?.resources?.getString(R.string.snow_showers),
        "hail" to context?.resources?.getString(R.string.hail),
        "thunderstorm" to context?.resources?.getString(R.string.thunderstorm),
        "thunderstorm-with-rain" to context?.resources?.getString(R.string.thunderstorm_with_rain),
        "thunderstorm-with-hail" to context?.resources?.getString(R.string.thunderstorm_with_hail)
    )
    return translations[string] ?: (context?.resources?.getString(R.string.unknown_condition) +
            "${translations[string]}")
}