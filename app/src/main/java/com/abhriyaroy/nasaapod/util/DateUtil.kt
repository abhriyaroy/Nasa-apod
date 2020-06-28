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

    fun getTodayYear() = getTodayDate().split("-")[0]
    fun getTodayMonth() = getTodayDate().split("-")[1]
    fun getTodayDay() = getTodayDate().split("-")[2]

    fun getTodayDateInMillis() = System.currentTimeMillis()

    fun getMinDatePickerDatInMillis() : Long {
        val myDate = "2000/01/01 00:00:00"
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val date = sdf.parse(myDate)
        return date.time
    }

}

enum class DATE_OUTPUT_FORMAT {
    EXPANDED_TO_YEAR_MONTH_DAY,
    MILLISECONDS
}
