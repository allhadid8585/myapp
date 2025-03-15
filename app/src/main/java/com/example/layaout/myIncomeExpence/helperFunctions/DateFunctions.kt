package com.example.layaout.myIncomeExpence.helperFunctions

import java.util.Calendar
import java.util.Date

fun Date.toStartOfDay (): Date{
    val calendar =  Calendar.getInstance().apply {
        time = this@toStartOfDay
        set(Calendar.HOUR, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        set(Calendar.AM_PM, 0)

    }
    return calendar.time
}

fun Date.toEndOfDay (): Date{
    val calendar =  Calendar.getInstance().apply {
        time = this@toEndOfDay
        set(Calendar.HOUR, 11)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
        set(Calendar.AM_PM, 1)

    }
    return calendar.time
}

fun Date.toStartOfMonth(): Date{
    val calendar =  Calendar.getInstance().apply {
        time = this@toStartOfMonth
        set(Calendar.HOUR, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        set(Calendar.AM_PM, 0)
        set(Calendar.DAY_OF_MONTH, 1)

    }
    return calendar.time
}

fun Date.toEndOfMonth (): Date{
    val calendar =  Calendar.getInstance().apply {
        time = this@toEndOfMonth
        set(Calendar.HOUR, 11)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
        set(Calendar.AM_PM, 1)
        set(Calendar.DAY_OF_MONTH, this.getActualMaximum(Calendar.DAY_OF_MONTH))

    }
    return calendar.time
}

fun Date.toStartOfYear ():Date {
    val calendar =  Calendar.getInstance().apply {
        time = this@toStartOfYear
        set(Calendar.HOUR, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        set(Calendar.AM_PM, 0)
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.MONTH, 0)

    }
    return calendar.time
}

fun Date.toEndOfYear():Date{
    val calendar =  Calendar.getInstance().apply {
        time = this@toEndOfYear
        set(Calendar.HOUR, 11)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
        set(Calendar.AM_PM, 1)
        set(Calendar.MONTH, 11)
        set(Calendar.DAY_OF_MONTH, this.getActualMaximum(Calendar.DAY_OF_MONTH))


    }
    return calendar.time
}
