package com.example.todo.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.Constants.Companion.getTodayDate
import com.example.todo.R
import com.example.todo.callback.OnTaskAddedListener
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.db.Task
import com.example.todo.db.TaskDatabase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTaskBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var calendar: Calendar
    private lateinit var taskTitle: String
    private lateinit var taskDetails: String
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
        displayCurrentDate()
        onSelectDateClick()
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

    private fun onAddTaskBtnClick() {
        binding.btnAddTask.setOnClickListener {
            if (!validateTextFields()) return@setOnClickListener
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

    private fun createToDoTask() {
        val task = Task(
            title = binding.edtTaskTitle.text?.trim().toString(),
            description = binding.edtTaskDetails.text?.trim().toString(),
            date = binding.tvSelectedDateValue.text.toString(),
            isDone = false
        )
        TaskDatabase.getINSTANCE(requireContext()).getTaskDAO().insertTask(task)
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(requireContext())
        datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
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


}