package com.example.todo.utils

import java.util.Calendar

fun Calendar.clearTime() {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}

fun Calendar.setDate(dayOfMonth: Int, month: Int, year: Int) {
    set(Calendar.DAY_OF_MONTH, dayOfMonth)
    set(Calendar.MONTH, month)
    set(Calendar.YEAR, year)
}