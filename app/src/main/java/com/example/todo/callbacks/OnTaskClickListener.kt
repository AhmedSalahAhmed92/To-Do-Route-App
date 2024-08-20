package com.example.todo.callbacks

import com.example.todo.database.entity.Task

interface OnTaskClickListener {
    fun onTaskClick(task: Task, taskPosition: Int)
}