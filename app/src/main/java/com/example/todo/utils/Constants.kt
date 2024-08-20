package com.example.todo.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Constants {
    companion object {
        const val EXTRA_TASK_CONTENT = "Task Content"
        //Task Status
//        const val INCOMPLETE = false
//        const val COMPLETE = true
        //Date Format
        private const val DATE_FORMAT_PATTERN = "d-M-yyyy"

        fun getTodayDate(): String {
            val formatter = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())
            val formattedDate = Calendar.getInstance(Locale.getDefault()).time
            return formatter.format(formattedDate)
        }
    }
}
