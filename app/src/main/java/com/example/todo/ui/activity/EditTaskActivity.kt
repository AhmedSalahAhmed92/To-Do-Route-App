package com.example.todo.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.R
import com.example.todo.database.TaskDatabase
import com.example.todo.database.entity.Task
import com.example.todo.databinding.ActivityEditTaskBinding
import com.example.todo.ui.fragment.AddTaskBottomSheet.Companion.AM
import com.example.todo.ui.fragment.AddTaskBottomSheet.Companion.PM
import com.example.todo.utils.Constants.Companion.EXTRA_TASK_CONTENT
import com.example.todo.utils.clearTime
import com.example.todo.utils.setDate
import com.example.todo.utils.setTime
import java.util.Calendar

class EditTaskActivity : AppCompatActivity() {
    private var _binding: ActivityEditTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var calendar: Calendar
    private lateinit var taskTitle: String
    private lateinit var taskDetails: String
    private lateinit var taskTime: String
    private var isDateSelected = false
    private var isTimeSelected = false

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initViews() {
        onBackStackPressed()
        initCalendar()
        getTask()
        onSelectDateClick()
        onSelectTimeClick()
        saveChangesListener()
    }

    private fun initCalendar() {
        calendar = Calendar.getInstance()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getTask() {
        val task = intent.getSerializableExtra(EXTRA_TASK_CONTENT, Task::class.java)
        if (task != null) {
            binding.content.titleEdt.setText(task.title)
            binding.content.detailsEdt.setText(task.description)
            binding.content.tvSelectedTimeValue.setText(task.time).toString()
            binding.content.tvSelectedDateValue.setText(task.date.toString()).toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun saveChangesListener() {
        binding.content.btnSave.setOnClickListener {
            if (!validateTextFields()) return@setOnClickListener
            calendar.clearTime()
            updateToDoTask()
            navigateToHomeActivity()
        }
    }

    private fun validateTextFields(): Boolean {
        taskTitle = binding.content.titleEdt.text?.trim().toString()
        taskDetails = binding.content.detailsEdt.text?.trim().toString()
        binding.content.titleTil.error = validateTitle(taskTitle)
        binding.content.detailsTil.error = validateDetails(taskDetails)
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
            Toast.makeText(this, getString(R.string.date_is_required), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateTime(): Boolean {
        if (!isTimeSelected) {
            Toast.makeText(this, getString(R.string.time_is_required), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun updateToDoTask() {
        val task = Task(
            id = intent.getSerializableExtra(EXTRA_TASK_CONTENT, Task::class.java)!!.id,
            title = binding.content.titleEdt.text?.trim().toString(),
            description = binding.content.detailsEdt.text?.trim().toString(),
            date = calendar.time,
            time = binding.content.tvSelectedTimeValue.text.toString()
        )
        TaskDatabase.getINSTANCE(this).getTaskDAO().updateTask(task)
    }

    private fun onSelectDateClick() {
        binding.content.tvSelectDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun onSelectTimeClick() {
        binding.content.tvSelectTime.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(this)
        datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            isDateSelected = true
            calendar.setDate(dayOfMonth, month, year)
            displaySelectedDate(dayOfMonth, month, year)
        }
        datePickerDialog.show()
    }

    private fun displaySelectedDate(dayOfMonth: Int, month: Int, year: Int) {
        val showDateFormat = "$dayOfMonth/${month + 1}/$year"
        binding.content.tvSelectedDateValue.text = showDateFormat
    }

    private fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(this, { view, hourOfDay, minute ->
            isTimeSelected = true
            calendar.setTime(hourOfDay, minute)
            displaySelectedTime(hourOfDay, minute)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)
        timePickerDialog.show()
    }

    private fun displaySelectedTime(hourOfDay: Int, minute: Int) {
        var hour = hourOfDay % 12
        if (hour == 0) hour = 12
        val amOrPm = if (hourOfDay < 12) AM else PM
        val showTimeFormat = "$hour:$minute $amOrPm"
        taskTime = binding.content.tvSelectedTimeValue.setText(showTimeFormat).toString()
    }

    private fun onBackStackPressed() {
        binding.toolbarBackButton.setOnClickListener {
            navigateToHomeActivity()
        }
    }

    private fun navigateToHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
