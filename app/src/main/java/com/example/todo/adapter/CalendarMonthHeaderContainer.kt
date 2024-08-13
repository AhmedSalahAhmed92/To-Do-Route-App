package com.example.todo.adapter

import android.view.View
import android.widget.TextView
import com.example.todo.R
import com.example.todo.databinding.CalendarMonthHeaderBinding
import com.kizitonwose.calendar.view.ViewContainer

class CalendarMonthHeaderContainer(view: View) : ViewContainer(view) {
    val monthHeaderTitleTv = CalendarMonthHeaderBinding.bind(view).calendarMonthTitleTv
}
