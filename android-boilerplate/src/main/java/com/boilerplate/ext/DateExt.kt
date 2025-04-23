package com.boilerplate.ext

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val YYYY_MM_DD = "yyyy-MM-dd"
const val YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
const val DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy HH:mm:ss"
const val DD_MM_YY = "dd-MM-yyyy"
const val HH_MM_SS = "HH:mm:ss"

fun dateFormat(
    dateString: String?,
    original: String = YYYY_MM_DD_HH_MM_SS,
    target: String = DD_MM_YYYY_HH_MM_SS,
    defaultValue: String = "-"
): String {
    if (dateString.isNullOrEmpty())
        return defaultValue

    return try {
        val originalFormat = SimpleDateFormat(original, Locale.getDefault())
        val dateChange: Date? = originalFormat.parse(dateString)
        val targetFormat = SimpleDateFormat(target, Locale.getDefault())
        dateChange?.let { targetFormat.format(it) } ?: defaultValue
    } catch (e: Exception) {
        e.printStackTrace()
        dateString
    }
}

fun getCurrentDate(format: String = DD_MM_YY): String {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(Date())
}

fun getDate(format: String = DD_MM_YY): Date? {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.parse(formatter.format(Date()))
}

fun String?.convertStringToDate(): Date? {
    if (this.isNullOrBlank()) return null
    val dateFormat = SimpleDateFormat(DD_MM_YY, Locale.getDefault())
    return dateFormat.parse(this)
}