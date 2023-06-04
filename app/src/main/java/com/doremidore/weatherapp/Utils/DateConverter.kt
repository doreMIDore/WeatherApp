package com.doremidore.weatherapp.Utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

fun convertDateToCustomFormat(dateString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM, E", Locale("ru"))

    val date = LocalDate.parse(dateString, inputFormatter)
    val today = LocalDate.now()
    val tomorrow = today.plusDays(1)

    return when (date) {
        today -> "Сегодня, ${date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("ru"))}"
        tomorrow -> "Завтра, ${date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("ru"))}"
        else -> date.format(outputFormatter)
    }
}