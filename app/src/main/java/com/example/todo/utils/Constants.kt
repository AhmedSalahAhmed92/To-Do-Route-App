package com.example.todo.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Constants {
    companion object {
        //Task Status
//        const val INCOMPLETE = false
//        const val COMPLETE = true
        //Date Format
        private const val DATE_FORMAT_PATTERN = "d-M-yyyy"
        const val TIME_FORMAT = "hh:mm a"

        fun getTodayDate(): String {
            val formatter = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())
            val formattedDate = Calendar.getInstance(Locale.getDefault()).time
            return formatter.format(formattedDate)
        }

        fun getDateTime() {
            val timeFormatter = SimpleDateFormat.getDateTimeInstance()
        }

    }
}
