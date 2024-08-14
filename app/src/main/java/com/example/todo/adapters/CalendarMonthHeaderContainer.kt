package com.example.todo.adapters

import android.view.View
import com.example.todo.databinding.CalendarMonthHeaderBinding
import com.kizitonwose.calendar.view.ViewContainer

class CalendarMonthHeaderContainer(view: View) : ViewContainer(view) {
    val monthHeaderTitleTv = CalendarMonthHeaderBinding.bind(view).calendarMonthTitleTv
}
