package com.abhriyaroy.nasaapod.util

import java.text.SimpleDateFormat
import java.util.*

open class DateUtil {

    private val yearMonthDayDateFormat = "yyyy-MM-dd"

    fun getTodayDate(): String {
        val sdf = SimpleDateFormat(yearMonthDayDateFormat)
        val currentDate = sdf.format(Date())
        return currentDate.toString()
    }

}

enum class DATE_OUTPUT_FORMAT {
    EXPANDED_TO_YEAR_MONTH_DAY,
    MILLISECONDS
}
