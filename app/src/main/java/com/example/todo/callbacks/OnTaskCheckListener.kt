package com.example.todo.callbacks

import com.example.todo.database.entity.Task

interface OnTaskCheckListener {
    fun onTaskCheck(task: Task, taskPosition: Int)
}