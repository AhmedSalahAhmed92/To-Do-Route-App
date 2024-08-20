package com.example.todo.database.typeConverters

import androidx.room.TypeConverter
import java.util.Date


class DateConverter {
    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate(timeStamp: Long): Date = Date(timeStamp)
}