package com.doremidore.weatherapp.Utils

import java.text.SimpleDateFormat
import java.util.*

fun convertDateToCustomFormat(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM, E", Locale("ru"))

    val date = inputFormat.parse(dateString)
    val today = Calendar.getInstance().apply { time = Date() }.get(Calendar.DAY_OF_YEAR)

    return when (date?.let { Calendar.getInstance().apply { time = it }.get(Calendar.DAY_OF_YEAR) }) {
        today -> "Сегодня, ${outputFormat.format(date)}"
        today + 1 -> "Завтра, ${outputFormat.format(date)}"
        else -> date?.let { outputFormat.format(it) } ?: ""
    }
}