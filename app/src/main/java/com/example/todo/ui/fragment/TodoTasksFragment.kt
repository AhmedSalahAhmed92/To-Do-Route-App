package com.example.todo.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.adapters.TaskAdapter
import com.example.todo.adapters.calendar.CalendarDayContainer
import com.example.todo.adapters.calendar.CalendarMonthHeaderContainer
import com.example.todo.database.TaskDatabase
import com.example.todo.database.entity.Task
import com.example.todo.databinding.FragmentTodoTasksBinding
import com.example.todo.utils.clearTime
import com.example.todo.utils.setDate
import com.kizitonwose.calendar.core.Week
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.WeekDayBinder
import com.kizitonwose.calendar.view.WeekHeaderFooterBinder
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale


class TodoTasksFragment : Fragment() {
    private lateinit var binding: FragmentTodoTasksBinding
    lateinit var calendar: Calendar
    var selectedDate: LocalDate? = null
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()
        initWeekCalendarAdapter()
        initRecyclerView()
    }

    private fun initWeekCalendarAdapter() {
        setupWeekCalendarView()
        bindMonthYearCalendarView()
        bindWeekCalendarView()
    }

    private fun setupWeekCalendarView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentDate = LocalDate.now()
            val currentMonth = YearMonth.now()
            val startDate = currentMonth.minusMonths(100).atStartOfMonth() // Adjust as needed
            val endDate = currentMonth.plusMonths(100).atEndOfMonth() // Adjust as needed
            val firstDayOfWeek =
                firstDayOfWeekFromLocale(Locale.forLanguageTag("ar")) // Available from the library
            binding.calendarView.setup(startDate, endDate, firstDayOfWeek)
            binding.calendarView.scrollToWeek(currentDate)
        }
    }

    private fun bindMonthYearCalendarView() {
        binding.calendarView.weekHeaderBinder =
            object : WeekHeaderFooterBinder<CalendarMonthHeaderContainer> {
                override fun bind(container: CalendarMonthHeaderContainer, data: Week) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val monthName = data.days.first().date.month.name
                        val year = data.days.first().date.year.toString()
                        val calendarHeaderTitle = "$monthName - $year"
                        container.monthHeaderTitleTv.text = calendarHeaderTitle
                    }
                }

                override fun create(view: View): CalendarMonthHeaderContainer {
                    return CalendarMonthHeaderContainer(view)
                }

            }
    }

    private fun bindWeekCalendarView() {
        val selectedColor = ResourcesCompat.getColor(resources, R.color.blue, null)
        val unSelectedColor = ResourcesCompat.getColor(resources, R.color.calendar_txt, null)

        binding.calendarView.dayBinder = object : WeekDayBinder<CalendarDayContainer> {
            override fun bind(container: CalendarDayContainer, data: WeekDay) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    container.apply {
                        dayWeekTv.text =
                            data.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                        dayMonthTv.text =
                            data.date.dayOfMonth.toString()
                    }

                    if (selectedDate == data.date) {
                        container.apply {
                            dayWeekTv.setTextColor(selectedColor)
                            dayMonthTv.setTextColor(selectedColor)
                        }
                    } else {
                        container.apply {
                            dayWeekTv.setTextColor(unSelectedColor)
                            dayMonthTv.setTextColor(unSelectedColor)
                        }
                    }
                    container.view.setOnClickListener {
                        selectedDate = data.date
                        binding.calendarView.notifyWeekChanged(data)

                        val dayOfMonth = data.date.dayOfMonth
                        val month = data.date.month.value - 1
                        val year = data.date.year
                        calendar.apply {
                            setDate(dayOfMonth, month, year)
                            clearTime()
                        }
                        getUpdatedTaskList(calendar.time)
                    }
                }
            }

            override fun create(view: View): CalendarDayContainer {
                return CalendarDayContainer(view)
            }
        }
    }

    fun getUpdatedTaskList(date: Date) {
        val filteredTaskList = filterTaskByDate(date)
        adapter.updateData(filteredTaskList)
    }

    private fun filterTaskByDate(date: Date): List<Task> {
        return TaskDatabase.getINSTANCE(requireContext())
            .getTaskDAO().getTaskByDate(date)
    }

    private fun initRecyclerView() {
        val tasksList = getAllTasksFromDataBase()
        adapter = TaskAdapter(tasksList)
        binding.rvTasks.adapter = adapter
    }

    fun getAllTasksFromDataBase(): List<Task> {
        return TaskDatabase.getINSTANCE(requireContext()).getTaskDAO().getAllTasks()
    }

}