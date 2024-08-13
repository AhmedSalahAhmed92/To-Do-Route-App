package com.example.todo.adapter

import android.view.View
import android.widget.TextView
import com.example.todo.R
import com.kizitonwose.calendar.view.ViewContainer

class CalendarDayContainer(view: View) : ViewContainer(view) {
    val dayWeekTv:TextView = view.findViewById(R.id.calendar_day_week_tv)
    val dayMonthTv: TextView = view.findViewById(R.id.calendar_day_month_tv)
}
