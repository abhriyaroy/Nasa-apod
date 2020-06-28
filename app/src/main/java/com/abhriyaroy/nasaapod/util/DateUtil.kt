package com.abhriyaroy.nasaapod.util

import java.text.SimpleDateFormat
import java.util.*

open class DateUtil {

    private val yearMonthDayDateFormat = "yyyy-MM-dd"

    open fun getTodayDate(): String {
        val sdf = SimpleDateFormat(yearMonthDayDateFormat)
        val currentDate = sdf.format(Date())
        return currentDate.toString()
    }

    open fun getTodayYear() = getTodayDate().split("-")[0]
    open fun getTodayMonth() = getTodayDate().split("-")[1]
    open fun getTodayDay() = getTodayDate().split("-")[2]

    open fun getTodayDateInMillis() = System.currentTimeMillis()

    open fun getMinDatePickerDatInMillis() : Long {
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
