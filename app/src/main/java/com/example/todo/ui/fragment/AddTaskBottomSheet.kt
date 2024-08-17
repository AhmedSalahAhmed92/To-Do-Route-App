package com.example.todo.ui.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.todo.R
import com.example.todo.callbacks.OnTaskAddedListener
import com.example.todo.database.TaskDatabase
import com.example.todo.database.entity.Task
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.utils.Constants.Companion.getTodayDate
import com.example.todo.utils.clearTime
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTaskBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var calendar: Calendar
    private lateinit var taskTitle: String
    private lateinit var taskDetails: String
    private var isDateSelected = false
    private var isTimeSelected = false
    var onAddedTaskListener: OnTaskAddedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalendar()
        //displayCurrentDate()
        onSelectDateClick()
        onSelectTimeClick()
        onAddTaskBtnClick()
    }

    private fun initCalendar() {
        calendar = Calendar.getInstance()
    }

    private fun displayCurrentDate() {
        binding.tvSelectedDateValue.text = getTodayDate()
    }

    private fun onSelectDateClick() {
        binding.tvSelectDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun onSelectTimeClick() {
        binding.tvSelectTime.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun onAddTaskBtnClick() {
        binding.btnAddTask.setOnClickListener {
            if (!validateTextFields()) return@setOnClickListener
            calendar.clearTime()
            createToDoTask()
            onAddedTaskListener?.onTaskAdded()
            dismiss()
        }
    }

    private fun validateTextFields(): Boolean {
        taskTitle = binding.edtTaskTitle.text?.trim().toString()
        taskDetails = binding.edtTaskDetails.text?.trim().toString()
        binding.tilTaskTitle.error = validateTitle(taskTitle)
        binding.tilTaskDetails.error = validateDetails(taskDetails)
        if (!validateDate()) return false
        if (!validateTime()) return false
        return validateTitle(taskTitle) == null && validateDetails(taskDetails) == null
    }

    private fun validateTitle(title: String): String? {
        if (title.isEmpty() || title.isBlank()) return getString(R.string.title_is_required)
        return null
    }

    private fun validateDetails(details: String): String? {
        if (details.isEmpty() || details.isBlank()) return getString(R.string.details_is_required)
        return null
    }

    private fun validateDate(): Boolean {
        if (!isDateSelected) {
            Toast.makeText(
                requireContext().applicationContext,
                getString(R.string.date_is_required),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    private fun validateTime(): Boolean {
        if (!isTimeSelected) {
            Toast.makeText(
                requireContext().applicationContext,
                getString(R.string.time_is_required),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    private fun createToDoTask() {
        val task = Task(
            title = binding.edtTaskTitle.text?.trim().toString(),
            description = binding.edtTaskDetails.text?.trim().toString(),
            date = calendar.time,
            time = binding.tvSelectedTimeValue.text.toString(),
            isDone = false
        )
        TaskDatabase.getINSTANCE(requireContext().applicationContext).getTaskDAO().insertTask(task)
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(requireContext().applicationContext)
        datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            isDateSelected = true
            calendar.apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            displaySelectedDate(dayOfMonth, month, year)
        }
        datePickerDialog.show()
    }

    private fun displaySelectedDate(dayOfMonth: Int, month: Int, year: Int) {
        val showDateFormat = "$dayOfMonth/${month + 1}/$year"
        binding.tvSelectedDateValue.text = showDateFormat
    }

    private fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(
            requireContext().applicationContext,
            { view, hourOfDay, minute ->
                isTimeSelected = true
                calendar.apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                }
                displaySelectedTime(hourOfDay, minute)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()
    }

    private fun displaySelectedTime(hourOfDay: Int, minute: Int) {
        var hour = hourOfDay % 12
        if (hour == 0) hour = 12
        val amOrPm = if (hourOfDay < 12) "AM" else "PM"
        val showTimeFormat = "$hour:$minute $amOrPm"
        binding.tvSelectedTimeValue.text = showTimeFormat
    }


}