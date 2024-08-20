package com.example.todo.callbacks

import com.example.todo.database.entity.Task

interface OnTaskDeleteListener {
    fun onTaskDelete(task: Task, taskPosition: Int)
}